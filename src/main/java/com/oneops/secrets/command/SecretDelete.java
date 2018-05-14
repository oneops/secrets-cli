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

import static com.oneops.secrets.utils.Color.*;
import static com.oneops.secrets.utils.Common.println;
import static java.lang.String.format;

import com.oneops.secrets.proxy.SecretsProxyException;
import com.oneops.secrets.proxy.model.Result;
import io.airlift.airline.*;
import java.io.IOException;

/**
 * Secret delete command
 *
 * @author Suresh
 */
@Command(name = "delete", description = "Delete a secret.")
public class SecretDelete extends SecretsCommand {

  @Arguments(title = "Secret name", description = "Secrets name", required = true)
  public String secretName;

  @Override
  public void exec() {

    try {
      String in =
          System.console()
              .readLine(
                  warn(
                      "The delete secret operation is irrevocable. Do you want to proceed (y/n)? "));
      if (in == null || !in.equalsIgnoreCase("y")) {
        throw new IllegalStateException("Exiting");
      }

      Result<Void> result = secretsClient.deleteSecret(app.getName(), secretName);
      if (result.isSuccessful()) {
        StringBuilder buf = new StringBuilder();
        String lineSep = System.lineSeparator();
        buf.append(
                sux(
                    format(
                        "Deleted the secret '%s' for application %s.",
                        secretName, app.getNsPath())))
            .append(lineSep)
            .append(lineSep)
            .append("Note the following:")
            .append(lineSep)
            .append("  ")
            .append(
                yellow(
                    dot(
                        String.format(
                            "Secret '%s' will be removed from '%s' env computes in a few seconds.",
                            secretName, app.getNsPath().toLowerCase()))))
            .append(lineSep)
            .append("  ")
            .append(
                yellow(
                    dot(
                        "You may need to restart the application in order for this secret change to take effect.")))
            .append(lineSep)
            .append("  ")
            .append(yellow(dot("Delete secret operation is irrevocable!!")))
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
