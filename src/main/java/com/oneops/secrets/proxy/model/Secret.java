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
 * Application secret
 *
 * @author Suresh G
 */
public class Secret {

    private final String name;
    private final String description;
    private final String checksum;
    private final long createdAtSeconds;
    private final long updatedAtSeconds;
    private final String createdBy;
    private final String updatedBy;
    private final Map<String, String> metadata;
    private final String type;
    private final long expiry;
    private final long version;

    public Secret(String name, String description, String checksum, long createdAtSeconds, long updatedAtSeconds, String createdBy, String updatedBy, Map<String, String> metadata, String type, long expiry, long version) {
        this.name = name;
        this.description = description;
        this.checksum = checksum;
        this.createdAtSeconds = createdAtSeconds;
        this.updatedAtSeconds = updatedAtSeconds;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.metadata = metadata;
        this.type = type;
        this.expiry = expiry;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getChecksum() {
        return checksum;
    }

    public long getCreatedAtSeconds() {
        return createdAtSeconds;
    }

    public long getUpdatedAtSeconds() {
        return updatedAtSeconds;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getType() {
        return type;
    }

    public long getExpiry() {
        return expiry;
    }

    public long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Secret{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", checksum='" + checksum + '\'' +
                ", createdAtSeconds=" + createdAtSeconds +
                ", updatedAtSeconds=" + updatedAtSeconds +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", metadata=" + metadata +
                ", type='" + type + '\'' +
                ", expiry=" + expiry +
                ", version=" + version +
                '}';
    }
}
