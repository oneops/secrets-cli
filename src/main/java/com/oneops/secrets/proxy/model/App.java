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

import javax.annotation.Nonnull;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Represents an application name used in Secrets Proxy cli.
 *
 * @author Suresh
 */
public class App {

    /**
     * Application group name separator.
     */
    public static final String GROUP_SEP = "_";

    private final String name;
    private final String org;
    private final String assembly;
    private final String env;

    /**
     * Constructor for {@link App}.
     *
     * @param name OneOps application name
     * @throws IllegalArgumentException if the app group name format is not valid.
     */
    public App(@Nonnull String name) {
        this.name = name;

        String[] paths = name.split(GROUP_SEP);
        if (paths.length != 3 || isNullOrEmpty(paths[0]) || isNullOrEmpty(paths[1]) || isNullOrEmpty(paths[2])) {
            throw new IllegalArgumentException("Invalid application name: " + name + ". The format is 'org_assembly_env'.");
        }

        org = paths[0].trim();
        assembly = paths[1].trim();
        env = paths[2].trim();
    }

    /**
     * Returns application name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns OneOps org for the application.
     */
    public String getOrg() {
        return org;
    }

    /**
     * Returns OneOps org  nspath for the application.
     */
    public String getOrgNsPath() {
        return String.format("/%s", org);
    }

    /**
     * Returns OneOps assembly  nspath for the application.
     */
    public String getAssemblyNsPath() {
        return String.format("/%s/%s", org, assembly);
    }

    /**
     * Returns OneOps assembly for the application.
     */
    public String getAssembly() {
        return assembly;
    }

    /**
     * Returns OneOps env for the application.
     */
    public String getEnv() {
        return env;
    }

    /**
     * Returns OneOps env nspath for the application.
     */
    public String getNsPath() {
        return String.format("/%s/%s/%s", org, assembly, env);
    }

    /**
     * Returns the team setting url for the assembly.
     *
     * @param baseUrl OneOps Base url.
     */
    public String getTeamsUrl(String baseUrl) {
        return String.format("%s/%s/assemblies/%s#teams", baseUrl, org, assembly);
    }

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", org='" + org + '\'' +
                ", assembly='" + assembly + '\'' +
                ", env='" + env + '\'' +
                '}';
    }
}
