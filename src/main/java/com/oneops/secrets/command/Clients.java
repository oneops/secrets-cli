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
import java.util.List;

import static com.oneops.secrets.utils.Color.*;
import static com.oneops.secrets.utils.Common.println;
import static java.lang.String.format;

/**
 * List all clients for an application.
 *
 * @author Suresh
 */
@Command(name = "clients", description = "Show all clients for the application.")
public class Clients extends SecretsCommand {

    @Override
    public void exec() {
        try {
            Result<List<Client>> result = secretsClient.getAllClients(app);
            if (result.isSuccessful()) {
                List<Client> clients = result.getBody();
                println(sux(format("%d computes (clients) are registered for the application %s.", clients.size(), app)));

                if (clients.size() == 0) {
                    println(warn(format("Complete the '%s' application env deployment by adding 'secrets-client' component.", app)));
                }
                for (int i = 0; i < clients.size(); i++) {
                    Client client = clients.get(i);
                    println("[✓] " + client.getName());
                    if (i > 10) {
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
