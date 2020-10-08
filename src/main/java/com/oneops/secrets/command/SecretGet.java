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
import static java.nio.file.StandardOpenOption.CREATE;

import com.oneops.secrets.proxy.SecretsProxyException;
import com.oneops.secrets.proxy.model.*;
import io.airlift.airline.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * Secret get content command.
 *
 * @author Suresh
 */
@Command(name = "get", description = "Retrieve a secret from the vault.")
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
          println(
              sux(String.format("Secret '%s' retrieved to %s", secretName, path.toAbsolutePath())));
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

  /** Helper method to save secret content to a file. */
  private Path save(String name, byte[] content) throws IOException {
    Path path = Paths.get(name);
    Pattern p = Pattern.compile("^y(es)?$");
    if (path.toFile().exists()) {
      if (null != System.console()) {
        String in = ConsoleImpl.readConsole(path);
        if (in == null || !p.matcher(in).lookingAt()) {
          throw new IllegalStateException("Exiting");
        }
      } else {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
          input = reader.readLine();
        } catch (IOException e) {
          throw new IllegalStateException("Exiting");
        }
        if (input == null || !p.matcher(input).lookingAt()) {
          throw new IllegalStateException("Exiting");
        }
      }
    }
    return Files.write(path, content, CREATE);
  }
}
