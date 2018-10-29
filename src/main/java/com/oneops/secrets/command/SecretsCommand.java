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

import com.oneops.secrets.config.AuthDomain;
import com.oneops.secrets.proxy.SecretsClient;
import com.oneops.secrets.proxy.SecretsUtils;
import com.oneops.secrets.proxy.model.App;
import com.oneops.secrets.utils.Platform;
import io.airlift.airline.Option;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Abstract secrets command. OneOps application name (also known as application group) is common to
 * all the commands.
 *
 * @author Suresh
 */
public abstract class SecretsCommand implements Runnable {

  protected Logger log = Logger.getLogger(getClass().getSimpleName());

  @Option(
    name = "-a",
    title = "Application name",
    description =
        "OneOps application name (org_assembly_env), to which you have secret-admin access",
    required = true
  )
  String appName;

  @Option(name = "-u", title = "User", description = "User name")
  public String user = Platform.getUser();

  @Option(name = "-m", title = "Domain", description = "Auth Domain. Default domain is 'prod'")
  public String domain = "prod";

  @Option(name = "-v", title = "Verbose", description = "Verbose mode")
  public boolean verbose;

  @Option(
    name = "-p",
    title = "Password",
    description = "Password - (Note : Use password if it is really required)"
  )
  public String password;

  /** Holds application details. */
  public App app;

  /** Secrets proxy client. */
  SecretsClient secretsClient;

  /** Executes the command logic. */
  public abstract void exec();

  @Override
  public void run() {
    validateAuthDomain();
    app = new App(appName);
    secretsClient = SecretsUtils.getSecretsClient(this);
    exec();
  }

  /**
   * Auth token file name for the given user and domain.
   *
   * @return token file name.
   */
  public String authTokenName() {
    return String.format("%s-%s.token", user, domain);
  }

  private void validateAuthDomain() {
    try {
      AuthDomain.valueOf(domain.toUpperCase());
    } catch (IllegalArgumentException iae) {
      throw new IllegalArgumentException(
          "Invalid auth domain "
              + domain
              + ". Valid domains are "
              + Arrays.stream(AuthDomain.values())
                  .map(AuthDomain::getType)
                  .collect(Collectors.joining(", ")));
    }
  }
}
