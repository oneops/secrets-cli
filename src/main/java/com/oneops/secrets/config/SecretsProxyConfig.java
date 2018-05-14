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

import com.typesafe.config.Config;

/**
 * Secrets Proxy EndPoint config
 *
 * @author Suresh
 */
public class SecretsProxyConfig {

  private final String baseUrl;

  private final int timeout;

  private final KeyStoreConfig trustStore;

  public SecretsProxyConfig(String baseUrl, int timeout, KeyStoreConfig trustStore) {
    this.baseUrl = baseUrl;
    this.timeout = timeout;
    this.trustStore = trustStore;
  }

  public SecretsProxyConfig(Config config) {
    this.baseUrl = config.getString("secretsProxy.baseUrl");
    this.timeout = config.getInt("secretsProxy.timeout");
    this.trustStore = new KeyStoreConfig(config.getConfig("secretsProxy.truststore"));
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public int getTimeout() {
    return timeout;
  }

  public KeyStoreConfig getTrustStore() {
    return trustStore;
  }

  @Override
  public String toString() {
    return "SecretsProxyConfig{"
        + "baseUrl='"
        + baseUrl
        + '\''
        + ", timeout="
        + timeout
        + ", trustStore="
        + trustStore
        + '}';
  }
}
