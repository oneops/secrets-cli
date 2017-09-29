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

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.oneops.secrets.utils.Common.println;

/**
 * Secret/Client details command.
 *
 * @author Suresh
 */
@Command(name = "details", description = "Get a client/secret details of an application.")
public class Details extends SecretsCommand {

    @Option(name = "-client", title = "Client name", description = "Secrets client name.")
    public String client;

    @Option(name = "-secret", title = "Secret name", description = "Secret name.")
    public String secret;

    @Override
    public void exec() {
        boolean secretPresent = !isNullOrEmpty(secret);
        boolean clientPresent = !isNullOrEmpty(client);

        // XOR check. Either client/secret should present, not both.
        if (secretPresent ^ clientPresent) {
            try {
                if (secretPresent) {
                    Result<Secret> secRes = secretsClient.getSecret(app.getName(), secret);
                    if (secRes.isSuccessful()) {
                        Secret secret = secRes.getBody();
                        println(Secret.getTable(secret));
                    } else {
                        throw new SecretsProxyException(this, secRes.getErr());
                    }
                } else {
                    Result<Client> clRes = secretsClient.getClientDetails(app.getName(), client);
                    if (clRes.isSuccessful()) {
                        Client client = clRes.getBody();
                        println(Client.getTable(client));
                    } else {
                        throw new SecretsProxyException(this, clRes.getErr());
                    }
                }
            } catch (IOException ex) {
                throw new SecretsProxyException(ex);
            }
        } else {
            throw new IllegalArgumentException("Either client or secret name (not both) should present.");
        }
    }
}
