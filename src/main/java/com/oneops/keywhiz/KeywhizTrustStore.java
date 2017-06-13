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
package com.oneops.keywhiz;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.logging.Logger;

import static com.oneops.config.CliConfig.keywhiz;

/**
 * Handles PKCS12 trust-store to communicate with Keywhiz server.
 * The production trust-store (.p12 file) and storepass are packaged
 * into the resources during build time using a system property.
 *
 * @author Suresh
 */
public class KeywhizTrustStore {

    /**
     * Logger instance.
     */
    private static Logger log = Logger.getLogger(KeywhizTrustStore.class.getSimpleName());

    /**
     * Default trust store.
     */
    private static KeyStore trustStore;

    static {
        String trustStoreName = keywhiz.getTrustStore().getName();
        char[] trustStorePass = keywhiz.getTrustStore().getPassword();
        try (InputStream ins = KeywhizTrustStore.class.getResourceAsStream(trustStoreName)) {
            log.info("Loading the Keywhiz trust-store " + trustStoreName);
            if (ins == null) {
                throw new IllegalStateException("Keywhiz trust-store (" + trustStoreName + ") is not available.");
            }
            trustStore = KeyStore.getInstance("PKCS12");
            trustStore.load(ins, trustStorePass);
        } catch (Exception ex) {
            throw new IllegalStateException("Keywhiz trust-store (" + trustStoreName + ") is not available.", ex);
        }
    }

    private KeywhizTrustStore() {
    }

    /**
     * Returns the keywhiz server trust store.
     *
     * @return {@link KeyStore}
     */
    public static KeyStore getTrustStore() {
        return trustStore;
    }
}
