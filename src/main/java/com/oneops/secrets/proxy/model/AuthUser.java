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

import java.util.List;

/**
 * Authenticated user details.
 *
 * @author Suresh
 */
public class AuthUser {

    private final String cn;
    private final String domain;
    private final List<String> roles;
    private final String userName;

    public AuthUser(String cn, String domain, List<String> roles, String userName) {
        this.cn = cn;
        this.domain = domain;
        this.roles = roles;
        this.userName = userName;
    }

    public String getCn() {
        return cn;
    }

    public String getDomain() {
        return domain;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "cn='" + cn + '\'' +
                ", domain='" + domain + '\'' +
                ", roles=" + roles +
                ", userName='" + userName + '\'' +
                '}';
    }
}
