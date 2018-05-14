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
package com.oneops.secrets.config;

import static com.oneops.secrets.utils.Color.*;
import static java.util.jar.Attributes.Name.IMPLEMENTATION_VERSION;

import com.typesafe.config.*;
import java.io.IOException;
import java.net.*;
import java.nio.file.*;
import java.util.jar.Attributes;

/**
 * Default configuration for Secrets CLI app.
 *
 * @author Suresh
 */
public class CliConfig {

  public static final SecretsProxyConfig secretsProxy;

  public static final Path logPath;

  public static final Attributes jarManifest;

  public static final String oneOpsBaseUrl;

  /** Config initialization. */
  static {
    Config appConfig = ConfigFactory.load("application");
    jarManifest = readJarManifest();
    secretsProxy = new SecretsProxyConfig(appConfig);
    logPath = Paths.get(appConfig.getString("log.dir"), appConfig.getString("log.filePattern"));
    oneOpsBaseUrl = appConfig.getString("oneops.baseUrl");
  }

  private CliConfig() {}

  /**
   * Returns the jar manifest of the class. Returns <code>empty</code> attributes if the class is
   * not bundled in a jar (Classes in an unpacked class hierarchy).
   */
  private static Attributes readJarManifest() {
    try {
      URL res = CliConfig.class.getResource(CliConfig.class.getSimpleName() + ".class");
      URLConnection conn = res.openConnection();
      if (conn instanceof JarURLConnection) {
        return ((JarURLConnection) conn).getManifest().getMainAttributes();
      } else {
        return new Attributes(0);
      }
    } catch (IOException ex) {
      throw new IllegalStateException(ex);
    }
  }

  /** Formatted banner string for CLI */
  public static String banner() {
    return String.format(
        "OneOps Secrets CLI: %s %n%s",
        cyan("v" + jarManifest.getValue(IMPLEMENTATION_VERSION)),
        gray("Built on " + jarManifest.getValue("Built-Date")));
  }

  /**
   * Returns the current CLI version.
   *
   * @return version string.
   */
  public static String getVersion() {
    return jarManifest.getValue(IMPLEMENTATION_VERSION);
  }
}
