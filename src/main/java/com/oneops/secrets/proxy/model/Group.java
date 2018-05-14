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
package com.oneops.secrets.proxy.model;

import java.util.*;

/**
 * Secrets application group.
 *
 * @author Suresh G
 */
public class Group {

  /** Application metadata. */
  public static final String USERID_METADATA = "_userId";

  public static final String DOMAIN_METADATA = "_domain";

  private final String name;
  private final String description;
  private final long createdAtSeconds;
  private final long updatedAtSeconds;
  private final String createdBy;
  private final String updatedBy;
  private final List<String> secrets;
  private final List<String> clients;
  private final Map<String, String> metadata;

  public Group(
      String name,
      String description,
      long createdAtSeconds,
      long updatedAtSeconds,
      String createdBy,
      String updatedBy,
      List<String> secrets,
      List<String> clients,
      Map<String, String> metadata) {
    this.name = name;
    this.description = description;
    this.createdAtSeconds = createdAtSeconds;
    this.updatedAtSeconds = updatedAtSeconds;
    this.createdBy = createdBy;
    this.updatedBy = updatedBy;
    this.secrets = secrets;
    this.clients = clients;
    this.metadata = metadata;
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

  public List<String> getSecrets() {
    return secrets;
  }

  public List<String> getClients() {
    return clients;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  @Override
  public String toString() {
    return "Group{"
        + "name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", createdAtSeconds="
        + createdAtSeconds
        + ", updatedAtSeconds="
        + updatedAtSeconds
        + ", createdBy='"
        + createdBy
        + '\''
        + ", updatedBy='"
        + updatedBy
        + '\''
        + ", secrets="
        + secrets
        + ", clients="
        + clients
        + ", metadata="
        + metadata
        + '}';
  }
}
