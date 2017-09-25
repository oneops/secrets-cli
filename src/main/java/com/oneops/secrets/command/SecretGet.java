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

import static com.oneops.secrets.utils.Color.*;
import static com.oneops.secrets.utils.Common.println;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * Secret get content command.
 *
 * @author Suresh
 */
@Command(name = "get", description = "Retrieve secret from vault.")
public class SecretGet extends SecretsCommand {

    @Arguments(title = "Secret name", description = "Secrets name", required = true)
    public String secretName;

    @Option(name = "-s", title = "Save", description = "Save to file. Defaults to false.")
    public boolean save = false;

    @Override
    public void exec() {
        try {
            Result<SecretContent> result = secretsClient.getSecretContent(app.getName(), secretName);
            if (result.isSuccessful()) {
                byte[] content = Base64.getDecoder().decode(result.getBody().getContent());

                if (save) {
                    Path path = save(secretName, content);
                    println(sux(String.format("Secret '%s' retrieved to %s", secretName, path.toAbsolutePath())));
                } else {
                    println(String.format("%s%s", System.lineSeparator(), cyan(new String(content))));
                }

            } else {
                throw new SecretsProxyException(this, result.getErr());
            }
        } catch (IOException e) {
            throw new SecretsProxyException(e);
        }
    }

    /**
     * Helper method to save secret content to a file.
     */
    private Path save(String name, byte[] content) throws IOException {
        Path path = Paths.get(name);
        if (path.toFile().exists()) {
            String in = System.console().readLine(warn("File " + path + " exists. Do you want to overwrite (y/n)? "));
            if (in == null || !in.equalsIgnoreCase("y")) {
                throw new IllegalStateException("Exiting");
            }
        }
        return Files.write(path, content, CREATE);
    }
}
