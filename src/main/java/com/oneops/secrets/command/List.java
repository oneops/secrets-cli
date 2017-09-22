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
import io.airlift.airline.Command;

import java.io.IOException;

import static com.oneops.secrets.utils.Color.*;
import static com.oneops.secrets.utils.Common.println;
import static java.lang.String.format;

/**
 * List all secret details for an application.
 *
 * @author Suresh
 */
@Command(name = "list", description = "List all secrets for the application.")
public class List extends SecretsCommand {

    @Override
    public void exec() {
        try {
            Result<java.util.List<Secret>> result = secretsClient.getAllSecrets(app);
            if (result.isSuccessful()) {
                java.util.List<Secret> secrets = result.getBody();
                println(sux(format("%d secrets are uploaded for the application %s.", secrets.size(), app)));

                for (int i = 0; i < secrets.size(); i++) {
                    Secret secret = secrets.get(i);
                    println("[✓] " + secret.getName() + ", Desc: " + secret.getDescription() + ", Version: " + secret.getVersion());
                    if (i > 50) {
                        println(bold("..."));
                        break;
                    }
                }
            } else {
                throw new SecretsProxyException(result.getErr().getMessage());
            }
        } catch (IOException ex) {
            throw new SecretsProxyException(ex);
        }
    }
}
