package com.oneops.secrets.config;

/**
 * Keywhiz auth domain.
 *
 * @author Suresh
 */
public enum AuthDomain {
  PROD("prod"),
  MGMT("mgmt"),
  STG("stg"),
  DEV("dev");

  private final String type;

  AuthDomain(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
