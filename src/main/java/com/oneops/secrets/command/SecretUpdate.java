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
package com.oneops.secrets.command;

import com.oneops.secrets.proxy.SecretsProxyException;
import com.oneops.secrets.proxy.model.*;
import io.airlift.airline.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;

import static com.oneops.secrets.proxy.SecretsUtils.validateSecret;
import static com.oneops.secrets.utils.Color.*;
import static com.oneops.secrets.utils.Common.println;
import static java.lang.String.format;

/**
 * Secret update command.
 *
 * @author Suresh
 */
@Command(name = "update", description = "Update an existing secret.")
public class SecretUpdate extends SecretsCommand {

    @Arguments(title = "Secrets file", description = "Secrets file", required = true)
    public String filePath;

    @Option(name = "-d", title = "Description", description = "Secret description", required = true)
    public String description;

    @Option(name = "-n", title = "Secret name", description = "Secret name. If not set, file name will be used.")
    public String secretName;

    @Override
    public void exec() {
        validateSecret(filePath, secretName, description);
        Path path = Paths.get(filePath);

        try {
            String secret = Base64.getEncoder().encodeToString(Files.readAllBytes(path));
            SecretReq secReq = new SecretReq(secret, description, null, 0, "secret");

            String in = System.console().readLine(warn("You are going to update an existing secret. Do you want to proceed (y/n)? "));
            if (in == null || !in.equalsIgnoreCase("y")) {
                throw new IllegalStateException("Exiting");
            }

            secretName = (secretName != null) ? secretName : path.toFile().getName();
            Result<Void> result = secretsClient.updateSecret(app.getName(), secretName, secReq);

            if (result.isSuccessful()) {
                StringBuilder buf = new StringBuilder();
                String lineSep = System.lineSeparator();
                buf.append(sux(format("Updated the secret '%s' for application %s.", secretName, app.getNsPath())))
                        .append(lineSep)
                        .append(lineSep)
                        .append("Note the followings,")
                        .append(lineSep)
                        .append("  ")
                        .append(yellow(dot(String.format("Secret '%s' will be synced to '%s' env computes in few seconds.", secretName, app.getNsPath().toLowerCase()))))
                        .append(lineSep)
                        .append("  ")
                        .append(yellow(dot(String.format("Applications can access secret content by reading '/secrets/%s' file.", secretName))))
                        .append(lineSep)
                        .append("  ")
                        .append(yellow(dot(String.format("Secrets can be accessed only by the User/Group configured in 'secrets-client' component in '%s' env.", app.getNsPath().toLowerCase()))))
                        .append(lineSep)
                        .append("  ")
                        .append(yellow(dot("You may need to restart the application inorder for this secret change to take effect.")))
                        .append(lineSep)
                        .append("  ")
                        .append(yellow(dot("You can revert back to previous version of the secret. Check 'secrets help revert' for more details.")))
                        .append(lineSep);
                println(buf.toString());

            } else {
                throw new SecretsProxyException(this, result.getErr());
            }

        } catch (IOException e) {
            throw new SecretsProxyException(e);
        }
    }
}
