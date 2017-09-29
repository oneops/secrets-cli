/*******************************************************************************
 *
 *   Copyright 2017 Walmart, Inc.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *******************************************************************************/
package com.oneops.secrets.proxy;

import com.oneops.secrets.command.SecretsCommand;
import com.oneops.secrets.config.CliConfig;
import com.oneops.secrets.proxy.model.*;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.file.*;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.oneops.secrets.utils.Color.*;
import static com.oneops.secrets.utils.Platform.getUserHome;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.logging.Level.WARNING;

/**
 * Utility methods uses in commands.
 *
 * @author Suresh
 */
public class SecretsUtils {

    private static Logger log = Logger.getLogger(SecretsUtils.class.getSimpleName());

    private static Path secretsPath = Paths.get(getUserHome(), ".secrets-proxy.token");

    public static final DateTimeFormatter dateFormatter = ofPattern("yyyy-MM-dd HH:mm:ss z");

    /**
     * Returns the authenticated {@link SecretsClient}. This method will
     * prompt for user password if it's not already authenticated.
     *
     * @param cmd Command to be executed.
     * @return Authenticated secrets client.
     */
    public static SecretsClient getSecretsClient(SecretsCommand cmd) {
        try {
            SecretsClient secretsClient = new SecretsClient(CliConfig.secretsProxy);
            String currentUser = cmd.user;
            String bearerToken = readToken();

            if (bearerToken != null) {
                log.info("Using bearer token from secrets path.");
                // Validating the token user id.
                Result<AuthUser> authUser = secretsClient.getAuthUser(bearerToken);
                if (authUser.isSuccessful() && currentUser.equalsIgnoreCase(authUser.getBody().getUserName())) {
                    secretsClient.setAuthToken(bearerToken);
                    return secretsClient;
                }
            }

            log.warning("Bearer token is not valid. Generating new token.");
            String password = String.valueOf(readPassword(currentUser));

            Result<TokenRes> genToken = secretsClient.genToken(currentUser, password);
            if (genToken.isSuccessful()) {
                writeToken(genToken.getBody().getAccessToken());
                return secretsClient;
            } else {
                throw new SecretsProxyException(cmd, genToken.getErr());
            }
        } catch (IOException | GeneralSecurityException ex) {
            throw new SecretsProxyException("Error occurred talking to the OneOps Secret Proxy. See 'secrets log' for more details.", ex);
        }
    }

    /**
     * Helper method to validate secrets file and it'e metadata.
     *
     * @param path secret file path
     * @param name secret name to be used
     * @param desc secret description.
     */
    public static void validateSecret(String path, String name, String desc) {
        File secretsFile = Paths.get(path).toFile();

        if (!secretsFile.exists() || secretsFile.isDirectory()) {
            throw new IllegalArgumentException(format("Secret '%s' not exists or is a directory.", path));
        }
        if (secretsFile.length() > 100_000) {
            throw new IllegalArgumentException(format("Secret '%s' is too large. It should be under 100KB.", path));
        }
        if (isNullOrEmpty(desc)) {
            throw new IllegalArgumentException(format("Secret '%s' description can't be empty.", path));
        }
        if (desc.length() > 64) {
            throw new IllegalArgumentException(format("Secret '%s' description is too long (Max 64 chars).", path));
        }
        if (name != null && name.trim().isEmpty()) {
            throw new IllegalArgumentException("Secret name is empty.");
        }
    }

    /**
     * Reads the secrets proxy token from secrets path.
     *
     * @return secrets proxy token or <code>null</code> if it can't find.
     */
    private static @Nullable
    String readToken() {
        String token = null;
        try {
            token = new String(Files.readAllBytes(secretsPath));
        } catch (IOException ex) {
            log.log(WARNING, "Can't read the token: " + ex.getMessage());
        }
        return token;
    }

    /**
     * Write the secrets auth token to secrets path. The token is usually valid
     * for only 60 minutes.
     *
     * @param token auth token.
     */
    private static void writeToken(String token) {
        try {
            Files.write(secretsPath, token.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException ex) {
            log.log(WARNING, "Can't save the token.", ex);
        }
    }

    /**
     * Read password from console.
     * <p>
     * Note that when the System.console() is null, there is no secure way of
     * entering a password without exposing it in the clear text on the console
     * (it is echoed onto the screen).For this reason, it is suggested that the
     * user login prior to using functionality such as input redirection since
     * this could result in a null console.
     *
     * @param user user who we are prompting a password for
     * @return user-inputted password
     */
    private static char[] readPassword(String user) {
        Console console = System.console();
        if (console != null) {
            System.out.format("Password for %s : ", bold(green(user)));
            return console.readPassword();
        } else {
            throw new RuntimeException("Can't read '" + user + "' password from console.");
        }
    }
}
