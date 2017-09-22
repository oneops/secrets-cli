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
package com.oneops.secrets.proxy.model;

import java.util.Map;

/**
 * Application secret create request.
 *
 * @author Suresh
 */
public class SecretReq {

    private final String content;
    private final String description;
    private final Map<String, String> metadata;
    private final long expiry;
    private final String type;

    public SecretReq(String content, String description, Map<String, String> metadata, long expiry, String type) {
        this.content = content;
        this.description = description;
        this.metadata = metadata;
        this.expiry = expiry;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public long getExpiry() {
        return expiry;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SecretReq{" +
                "content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", metadata=" + metadata +
                ", expiry=" + expiry +
                ", type='" + type + '\'' +
                '}';
    }
}
