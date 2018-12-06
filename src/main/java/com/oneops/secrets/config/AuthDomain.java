package com.oneops.secrets.config;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
  TEKTON_PROD("tekton-prod"),
  TEKTON_MGMT("tekton-mgmt"),
  TEKTON_STG("tekton-stg"),
  TEKTON_DEV("tekton-dev"),

  /*MS domain name*/
  MS_PROD("ms-prod"),
  MS_MGMT("ms-mgmt"),
  MS_STG("ms-stg"),
  MS_DEV("ms-dev");

  private final String type;

  private static final Map<String, String> authDomainMap =
      Arrays.stream(AuthDomain.values())
          .collect(Collectors.toMap(dmn -> dmn.toString(), type -> type.getType()));

  public static AuthDomain getAuthDomain(String type) {
    return AuthDomain.valueOf(
        authDomainMap
            .entrySet()
            .stream()
            .filter(x -> x.getValue().equals(type))
            .findFirst()
            .get()
            .getKey());
  }

  AuthDomain(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
