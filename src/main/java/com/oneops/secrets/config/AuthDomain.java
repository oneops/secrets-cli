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
  
/*Oneops PCI domain name*/
  OOPCIQA("stg-pci"),
  OOPCIPROD("prod-pci"),
  OOPCIMGMT("mgmt-pci");

  private final String type;

  AuthDomain(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
