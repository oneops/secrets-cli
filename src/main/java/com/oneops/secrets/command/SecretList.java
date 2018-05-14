/**
 * *****************************************************************************
 *
 * <p>Copyright 2017 Walmart, Inc.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * <p>*****************************************************************************
 */
package com.oneops.secrets.command;

import static com.oneops.secrets.utils.Color.bold;
import static com.oneops.secrets.utils.Color.sux;
import static com.oneops.secrets.utils.Common.println;
import static java.lang.String.format;

import com.oneops.secrets.proxy.SecretsProxyException;
import com.oneops.secrets.proxy.model.*;
import io.airlift.airline.Command;
import java.io.IOException;
import java.util.List;

/**
 * List all secret details for an application.
 *
 * @author Suresh
 */
@Command(name = "list", description = "List all secrets for the application.")
public class SecretList extends SecretsCommand {

  @Override
  public void exec() {
    try {
      Result<List<Secret>> result = secretsClient.getAllSecrets(app.getName());
      if (result.isSuccessful()) {
        List<Secret> secrets = result.getBody();
        println(
            sux(
                format(
                    "%d secrets are stored for the application environments: %s",
                    secrets.size(), app.getNsPath())));
        if (secrets.size() > 0) {
          println(Secret.getTable(secrets));
          println(
              String.format(
                  "To get the secret details, run %s",
                  bold("secrets details  -a <app name> -secret <secret name>")));
        }
      } else {
        throw new SecretsProxyException(this, result.getErr());
      }
    } catch (IOException ex) {
      throw new SecretsProxyException(ex);
    }
  }
}
