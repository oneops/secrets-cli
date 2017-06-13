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
 * Keywhiz config
 *
 * @author Suresh
 */
public class Keywhiz {

    private String baseUrl;
    private String svcUser;
    private char[] svcPasswd;
    private TrustStore trustStore;

    public Keywhiz(String baseUrl, String svcUser, char[] svcPasswd, TrustStore trustStore) {
        this.baseUrl = baseUrl;
        this.svcUser = svcUser;
        this.svcPasswd = svcPasswd;
        this.trustStore = trustStore;
    }

    public Keywhiz(Config config) {
        this.baseUrl = config.getString("keywhiz.baseUrl");
        this.svcUser = config.getString("keywhiz.svcUser");
        this.svcPasswd = config.getString("keywhiz.svcPasswd").toCharArray();
        this.trustStore = new TrustStore(config.getConfig("keywhiz.truststore"));
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getSvcUser() {
        return svcUser;
    }

    public char[] getSvcPasswd() {
        return svcPasswd;
    }

    public TrustStore getTrustStore() {
        return trustStore;
    }

    @Override
    public String toString() {
        return "Keywhiz{" +
                "baseUrl='" + baseUrl + '\'' +
                ", svcUser=********" +
                ", svcPasswd=********" +
                ", trustStore=" + trustStore +
                '}';
    }
}