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

import java.io.*;
import java.nio.file.*;
import java.util.Base64;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.oneops.secrets.utils.Color.sux;
import static com.oneops.secrets.utils.Common.println;
import static java.lang.String.format;

/**
 * Add secrets for an application.
 *
 * @author Suresh
 */
@Command(name = "add", description = "Add secret for an application.")
public class Add extends SecretsCommand {

    @Arguments(title = "Secrets file", description = "Secrets file", required = true)
    public String path;

    @Option(name = "-d", title = "Description", description = "Secret description", required = true)
    public String description;

    @Override
    public void exec() {
        File secretsFile = Paths.get(path).toFile();

        if (!secretsFile.exists() || secretsFile.isDirectory()) {
            throw new IllegalArgumentException(format("Secret '%s' not exists or is a directory.", path));
        }

        if (secretsFile.length() > 100_000) {
            throw new IllegalArgumentException(format("Secret '%s' is too large. It should be under 100K.", path));
        }

        if (isNullOrEmpty(description) || (description.length() > 64)) {
            throw new IllegalArgumentException(format("Secret '%s' description can't be empty or too long.", path));
        }

        try {
            String secret = Base64.getEncoder().encodeToString(Files.readAllBytes(secretsFile.toPath()));
            SecretReq secReq = new SecretReq(secret, description, null, 0, "secret");
            Result<Void> result = secretsClient.createSecret(app, secretsFile.getName(), true, secReq);
            if (result.isSuccessful()) {
                println(sux(format("Secret '%s' added successfully for application %s.", path, app)));
            } else {
                throw new SecretsProxyException(result.getErr().getMessage());
            }
        } catch (IOException e) {
            throw new SecretsProxyException(e);
        }
    }
}
