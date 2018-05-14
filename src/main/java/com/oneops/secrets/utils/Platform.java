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
package com.oneops.secrets.utils;

/**
 * A platform is a unique combination of CPU architecture and operating system. This class attempts
 * to determine the platform it is executing on by examining and normalizing the <code>os.arch
 * </code> and <code>os.name</code> system properties.
 *
 * @author Suresh
 */
public class Platform {

  private static final OS os;
  private static final Arch arch;
  private static final String user;
  private static final String userHome;
  private static final boolean isUnix;

  static {
    user = System.getProperty("user.name");
    userHome = System.getProperty("user.home");
    String osArch = System.getProperty("os.arch").toLowerCase();
    String osName = System.getProperty("os.name").toLowerCase();

    if (osName.matches(OS.mac.getType())) {
      os = OS.mac;
    } else if (osName.startsWith(OS.linux.getType())) {
      os = OS.linux;
    } else if (osName.startsWith(OS.windows.getType())) {
      os = OS.windows;
    } else if (osName.startsWith(OS.freebsd.getType())) {
      os = OS.freebsd;
    } else {
      throw new IllegalStateException("Unsupported OS " + osName);
    }

    if (osArch.matches(Arch.x86.getType())) {
      arch = Arch.x86;
    } else if (osArch.matches(Arch.x86_64.getType())) {
      arch = Arch.x86_64;
    } else {
      throw new IllegalStateException("Unsupported OS Arch " + osArch);
    }

    isUnix = (os != OS.windows);
  }

  private Platform() {}

  public static OS getOs() {
    return os;
  }

  public static Arch getArch() {
    return arch;
  }

  public static String getUser() {
    return user;
  }

  public static String getUserHome() {
    return userHome;
  }

  public static boolean isUnix() {
    return isUnix;
  }
}

enum OS {
  linux("linux"),
  freebsd("freebsd"),
  windows("win"),
  mac("mac os x|darwin");

  private final String type;

  OS(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}

enum Arch {
  x86("x86|i386"),
  x86_64("x86_64|amd64");

  private final String type;

  Arch(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
