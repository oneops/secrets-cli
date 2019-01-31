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
  DEV("dev"),

  /*Tekton domain name*/
  TEKTONPROD("tektonprod"),
  TEKTONMGMT("tektonmgmt"),
  TEKTONSTG("tektonstg"),
  TEKTONDEV("tektondev");

  private final String type;

  AuthDomain(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
