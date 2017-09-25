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
import java.util.List;

import static com.oneops.secrets.utils.Color.sux;
import static com.oneops.secrets.utils.Common.println;
import static java.lang.String.format;

/**
 * Secret version command.
 *
 * @author Suresh
 */
@Command(name = "versions", description = "Retrieve versions of a secret, sorted from newest to oldest update time.")
public class SecretVersions extends SecretsCommand {

    @Arguments(title = "Secret name", description = "Secrets name", required = true)
    public String secretName;

    @Override
    public void exec() {
        try {
            Result<List<Secret>> result = secretsClient.getSecretVersions(app.getName(), secretName);
            if (result.isSuccessful()) {
                List<Secret> secrets = result.getBody();
                println(sux(format("Retrieved %d version(s) of secret '%s' for application env: %s", secrets.size(), secretName, app.getNsPath())));
                if (secrets.size() > 0) {
                    println(Secret.getVersionTable(secrets));
                }
            } else {
                throw new SecretsProxyException(this, result.getErr());
            }
        } catch (IOException e) {
            throw new SecretsProxyException(e);
        }
    }
}
