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
package com.oneops.secrets.config;

import com.typesafe.config.Config;

/**
 * Keystore store config
 *
 * @author Suresh
 */
public class KeyStoreConfig {

    private static final String FS_PREFIX = "file:";

    private final String name;
    private final String type;
    private final char[] password;
    private final boolean fileResource;

    public KeyStoreConfig(Config config) {
        String path = config.getString("path");
        this.type = config.getString("type");
        this.password = config.getString("password").toCharArray();
        this.name = path.replaceFirst(FS_PREFIX, "");
        this.fileResource = path.toLowerCase().startsWith(FS_PREFIX);
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

    /**
     * Checks if the keystore config path is a file resource.
     * Paths starts with <b>file://</b> identified as fs resource.
     *
     * @return <code>true</code> if the path is a file system resource.
     */
    public boolean isFileResource() {
        return fileResource;
    }

    @Override
    public String toString() {
        return "KeyStoreConfig{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", fileResource=" + fileResource +
                '}';
    }
}
