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
package com.oneops.ldap;

import com.oneops.config.CliConfig;
import com.oneops.config.LDAP;
import com.oneops.security.KeywhizKeyStore;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.Credential;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.auth.*;
import org.ldaptive.ssl.SslConfig;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;

/**
 * @author <a href="mailto:sgopal1@walmartlabs.com">Suresh G</a>
 * @Since 6/12/17
 */
public class LDAPClient {



    public static void main(String[] args) throws  Exception{

        LDAP ldap = CliConfig.ldap;

        InputStream ins = KeywhizKeyStore.class.getResourceAsStream(ldap.getTrustStore().getName());
        System.out.println("Inputstream " + ins);
        KeyStore trustStore = KeyStore.getInstance(ldap.getTrustStore().getType());
        trustStore.load(ins, ldap.getTrustStore().getPassword());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        SslConfig ssl = new SslConfig();
        ssl.setTrustManagers(trustManagers);

        ConnectionConfig connConfig = new ConnectionConfig();
        connConfig.setUseStartTLS(true);
        connConfig.setConnectTimeout(Duration.ofSeconds(5));
        connConfig.setLdapUrl(ldap.getServer());
        connConfig.setResponseTimeout(Duration.ofSeconds(10));
        connConfig.setSslConfig(ssl);

        SearchDnResolver dnResolver = new SearchDnResolver(new DefaultConnectionFactory(connConfig));
        dnResolver.setBaseDn(ldap.getUserBaseDN());
        dnResolver.setUserFilter(ldap.getUserAttribute());


        BindAuthenticationHandler authHandler = new BindAuthenticationHandler(new DefaultConnectionFactory(connConfig));
        Authenticator auth = new Authenticator(dnResolver, authHandler);

        AuthenticationResponse response = auth.authenticate(new AuthenticationRequest("sgopal1", new Credential("xxxxx")));
        if (response.getResult()) {
            System.out.println("Authentication succeeded!");
        } else {
            System.out.println("Authentication failed!");
        }


    }

}
