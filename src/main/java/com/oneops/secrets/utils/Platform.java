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

  private static final String user;
  private static final String userHome;

  static {
    user = System.getProperty("user.name");
    userHome = System.getProperty("user.home");
  }

  private Platform() {}

  public static String getUser() {
    return user;
  }

  public static String getUserHome() {
    return userHome;
  }
}
