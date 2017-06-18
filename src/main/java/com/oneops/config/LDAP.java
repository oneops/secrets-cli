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
package com.oneops.config;

import com.typesafe.config.Config;

/**
 * Holds LDAP related config
 *
 * @author Suresh
 */
public class LDAP {

    private String server;
    private int port;
    private String userDN;
    private char[] password;
    private String userBaseDN;
    private String userAttribute;
    private KeyStoreConfig trustStore;

    public LDAP(String server, int port, String userDN, char[] password, String userBaseDN, String userAttribute, KeyStoreConfig trustStore) {
        this.server = server;
        this.port = port;
        this.userDN = userDN;
        this.password = password;
        this.userBaseDN = userBaseDN;
        this.userAttribute = userAttribute;
        this.trustStore = trustStore;
    }

    public LDAP(Config config) {
        this.server = config.getString("ldap.server");
        this.port = config.getInt("ldap.port");
        this.userDN = config.getString("ldap.userDN");
        this.password = config.getString("ldap.password").toCharArray();
        this.userBaseDN = config.getString("ldap.userBaseDN");
        this.userAttribute = config.getString("ldap.userAttribute");
        this.trustStore = new KeyStoreConfig(config.getConfig("ldap.truststore"));
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getUserDN() {
        return userDN;
    }

    public char[] getPassword() {
        return password;
    }

    public String getUserBaseDN() {
        return userBaseDN;
    }

    public String getUserAttribute() {
        return userAttribute;
    }

    public KeyStoreConfig getTrustStore() {
        return trustStore;
    }

    @Override
    public String toString() {
        return "LDAP{" +
                "server='" + server + '\'' +
                ", port=" + port +
                ", userDN='" + userDN + '\'' +
                ", password=*****" +
                ", userBaseDN='" + userBaseDN + '\'' +
                ", userAttribute='" + userAttribute + '\'' +
                ", trustStore=" + trustStore +
                '}';
    }

}
