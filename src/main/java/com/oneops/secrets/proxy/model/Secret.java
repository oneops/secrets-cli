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

import com.oneops.secrets.asciitable.*;

import java.time.*;
import java.util.*;

import static com.oneops.secrets.proxy.SecretsUtils.dateFormatter;

/**
 * Application secret
 *
 * @author Suresh G
 */
public class Secret {

    /**
     * The attributes used in secret metadata
     */
    public static final String USERID_METADATA = "_userId";
    public static final String DESC_METADATA = "_desc";
    public static final String FILENAME_METADATA = "filename";

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

    /**
     * Returns formatted table string for list of secrets.
     *
     * @param secrets list of secrets.
     * @return formatted string.
     */
    public static String getTable(List<Secret> secrets) {
        List<Column.Data<Secret>> columns = Arrays.asList(
                new Column("Secret Name").with(Secret::getName),
                new Column("Description").with(Secret::getDescription),
                new Column("UserID").with(s -> s.getMetadata().getOrDefault(USERID_METADATA, "N/A")),
                new Column("Checksum").with(s -> s.checksum.substring(0, 6)),
                new Column("Expiry").with(s -> expiry(s.expiry)),
                new Column("Version").with(s -> String.valueOf(s.getVersion())));
        return String.format("%s%s", System.lineSeparator(), Table.getTable(secrets, columns));
    }

    /**
     * Returns formatted table string for list of secret versions.
     *
     * @param secrets list of secrets.
     * @return formatted string.
     */
    public static String getVersionTable(List<Secret> secrets) {
        List<Column.Data<Secret>> columns = Arrays.asList(
                new Column("Secret Name").with(Secret::getName),
                new Column("Version").with(s -> String.valueOf(s.getVersion())),
                new Column("Description").with(s -> s.getMetadata().getOrDefault(DESC_METADATA, "N/A")),
                new Column("UserID").with(s -> s.getMetadata().getOrDefault(USERID_METADATA, "N/A")),
                new Column("Checksum").with(s -> s.checksum.substring(0, 6)),
                new Column("Expiry").with(s -> expiry(s.expiry)));
        return String.format("%s%s", System.lineSeparator(), Table.getTable(secrets, columns));
    }

    /**
     * Returns formatted table string for the given secret details.
     * The table is inverted as there are many columns. For 'Updated By'
     * user, we are using a custom metadata attribute because of the
     * Keywhiz limitation.
     *
     * @return formatted string.
     */
    public static String getTable(Secret secretDetails) {

        String[] header = {"Secret Name", secretDetails.name};
        String[][] data = {
                {"Description", secretDetails.description},
                {"Created By", secretDetails.createdBy},
                {"Created At", time(secretDetails.createdAtSeconds)},
                {"Updated By", secretDetails.getMetadata().getOrDefault(USERID_METADATA, "N/A")},
                {"Updated At", time(secretDetails.updatedAtSeconds)},
                {"Checksum", secretDetails.checksum},
                {"Type", secretDetails.type},
                {"Expiry", expiry(secretDetails.expiry)},
                {"Version", String.valueOf(secretDetails.version)},
        };
        return String.format("%s%s", System.lineSeparator(), Table.getTable(header, data));
    }

    /**
     * Table helper method.
     */
    private static String time(long atSeconds) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(atSeconds), ZoneId.systemDefault()).format(dateFormatter);
    }

    /**
     * Table helper method.
     */
    private static String expiry(long atMilli) {
        if (atMilli != 0) {
            return ZonedDateTime.ofInstant(Instant.ofEpochMilli(atMilli), ZoneId.systemDefault()).format(dateFormatter);
        } else {
            return "Never";
        }
    }
}
