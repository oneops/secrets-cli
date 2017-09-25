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

import java.util.*;

/**
 * Secrets application client
 *
 * @author Suresh G
 */
public class Client {

    private final String name;
    private final String description;
    private final long createdAtSeconds;
    private final long updatedAtSeconds;
    private final String createdBy;
    private final String updatedBy;
    private final Long lastSeenSeconds;

    public Client(String name, String description, long createdAtSeconds, long updatedAtSeconds, String createdBy, String updatedBy, Long lastSeenSeconds) {
        this.name = name;
        this.description = description;
        this.createdAtSeconds = createdAtSeconds;
        this.updatedAtSeconds = updatedAtSeconds;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.lastSeenSeconds = lastSeenSeconds;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public Long getLastSeenSeconds() {
        return lastSeenSeconds;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAtSeconds=" + createdAtSeconds +
                ", updatedAtSeconds=" + updatedAtSeconds +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", lastSeenSeconds=" + lastSeenSeconds +
                '}';
    }


    /**
     * Returns formatted table string for list of clients.
     *
     * @param clients list of clients.
     * @return formatted string.
     */
    public static String getTable(List<Client> clients) {
        List<Column.Data<Client>> columns = Arrays.asList(new Column("Client Name").with(Client::getName),
                new Column("Created By").with(Client::getCreatedBy));
        return String.format("%s%s%n", System.lineSeparator(), Table.getTable(clients, columns));
    }
}
