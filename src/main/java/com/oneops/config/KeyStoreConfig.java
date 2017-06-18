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
 * Keystore store config
 *
 * @author Suresh
 */
public class KeyStoreConfig {

    private String name;
    private String type;
    private char[] password;

    public KeyStoreConfig(String name, String type, char[] password) {
        this.name = name;
        this.type = type;
        this.password = password;
    }

    public KeyStoreConfig(Config config) {
        this.name = config.getString("path");
        this.type = config.getString("type");
        this.password = config.getString("password").toCharArray();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public char[] getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "KeyStore {" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", password=********" +
                '}';
    }
}
