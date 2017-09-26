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
package com.oneops.secrets.proxy;

import com.oneops.secrets.config.*;
import com.oneops.secrets.proxy.model.*;
import com.squareup.moshi.Moshi;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.*;
import retrofit2.Response;
import retrofit2.converter.moshi.MoshiConverterFactory;

import javax.annotation.Nullable;
import javax.net.ssl.*;
import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.file.*;
import java.security.*;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Collections.singletonList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;

/**
 * Secrets proxy client.
 *
 * @author Suresh
 * @see <a href="https://oneops.github.com/secrets-proxy/apidocs">Secrets Proxy API doc</a>
 */
public class SecretsClient {

    private Logger log = Logger.getLogger(getClass().getSimpleName());

    private SecretsProxyConfig config;

    private String authToken;

    private SecretsProxy secretsProxy;

    private Converter<ResponseBody, ErrorRes> errResConverter;

    public SecretsClient(SecretsProxyConfig config) throws GeneralSecurityException {
        log.info("Initializing the Secrets client for " + config.getBaseUrl());
        this.config = config;

        Moshi moshi = new Moshi.Builder().add(new DateAdapter()).build();

        HttpLoggingInterceptor logIntcp = new HttpLoggingInterceptor(s -> log.info(s));
        logIntcp.setLevel(BASIC);

        TrustManager[] trustManagers = getTrustManagers();
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, trustManagers, new SecureRandom());
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();

        int timeout = config.getTimeout();
        OkHttpClient okhttp = new OkHttpClient().newBuilder()
                .sslSocketFactory(socketFactory, (X509TrustManager) trustManagers[0])
                .connectionSpecs(singletonList(ConnectionSpec.MODERN_TLS))
                .followSslRedirects(false)
                .retryOnConnectionFailure(true)
                .connectTimeout(timeout, SECONDS)
                .readTimeout(timeout, SECONDS)
                .writeTimeout(timeout, SECONDS)
                .addInterceptor(logIntcp)
                .addInterceptor(chain -> {
                    Request.Builder reqBuilder = chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("User-Agent", "OneOps Secrets CLI");
                    if (authToken != null) {
                        reqBuilder.addHeader("X-Authorization", "Bearer " + authToken);
                    }
                    return chain.proceed(reqBuilder.build());
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getBaseUrl())
                .client(okhttp)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        secretsProxy = retrofit.create(SecretsProxy.class);
        errResConverter = retrofit.responseBodyConverter(ErrorRes.class, new Annotation[0]);
    }

    /**
     * Generate an access token with default <b>prod</b> domain.
     *
     * @param userName oneops username
     * @param password oneops password
     * @return Token response.
     * @throws IOException throws if any IO error when communicating to Secrets Proxy.
     */
    public Result<TokenRes> genToken(String userName, String password) throws IOException {
        return genToken(userName, password, "prod");
    }

    public Result<TokenRes> genToken(String userName, String password, String domain) throws IOException {
        Result<TokenRes> res = exec(secretsProxy.token(new TokenReq(userName, password, domain)));
        if (res.isSuccessful()) {
            this.authToken = res.getBody().getAccessToken();
        }
        return res;
    }

    /**
     * Get the authentication user details from the given token.
     *
     * @param token Bearer token
     * @return {@link AuthUser}
     * @throws IOException throws if any IO error when communicating to Secrets Proxy.
     */
    public Result<AuthUser> getAuthUser(String token) throws IOException {
        return exec(secretsProxy.getAuthUser("Bearer " + token));
    }

    public Result<Group> getGroupDetails(String group) throws IOException {
        return exec(secretsProxy.getGroupDetails(group));
    }

    public Result<List<Client>> getAllClients(String group) throws IOException {
        return exec(secretsProxy.getAllClients(group));
    }

    public Result<Client> getClientDetails(String group, String clientName) throws IOException {
        return exec(secretsProxy.getClientDetails(group, clientName));
    }

    public Result<List<Secret>> getAllSecrets(String group) throws IOException {
        return exec(secretsProxy.getAllSecrets(group));
    }

    public Result<List<String>> getAllSecretsExpiring(String group, long time) throws IOException {
        return exec(secretsProxy.getAllSecretsExpiring(group, time));
    }

    public Result<Void> createSecret(String group, String name, boolean createGroup, SecretReq secretReq) throws IOException {
        return exec(secretsProxy.createSecret(group, name, createGroup, secretReq));
    }

    public Result<Void> updateSecret(String group, String name, SecretReq secretReq) throws IOException {
        return exec(secretsProxy.updateSecret(group, name, secretReq));
    }

    public Result<Secret> getSecret(String group, String name) throws IOException {
        return exec(secretsProxy.getSecret(group, name));
    }

    public Result<List<Secret>> getSecretVersions(String group, String name) throws IOException {
        return exec(secretsProxy.getSecretVersions(group, name));
    }

    public Result<Void> deleteSecret(String group, String name) throws IOException {
        return exec(secretsProxy.deleteSecret(group, name));
    }

    public Result<Void> deleteClient(String group, String name) throws IOException {
        return exec(secretsProxy.deleteClient(group, name));
    }

    public Result<List<String>> deleteAllSecrets(String group) throws IOException {
        return exec(secretsProxy.deleteAllSecrets(group));
    }

    public Result<Void> setSecretVersion(String group, String name, long version) throws IOException {
        return exec(secretsProxy.setSecretVersion(group, name, new SecretVersion(version)));
    }

    public Result<SecretContent> getSecretContent(String group, String name) throws IOException {
        return exec(secretsProxy.getSecretContents(group, name));
    }

    /**
     * Sets new authorization token to used for subsequent requests.
     *
     * @param authToken Bearer token string.
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Helper method to handle {@link Call} object and return the execution {@link Result}.
     */
    private <T> Result<T> exec(Call<T> call) throws IOException {
        Response<T> res = call.execute();
        ErrorRes err = null;
        T body = null;

        if (res.isSuccessful()) {
            body = res.body();
        } else {
            if (res.errorBody() != null) {
                err = errResConverter.convert(res.errorBody());
            }
        }
        return new Result<>(body, err, res.code(), res.isSuccessful());
    }

    /**
     * Load the keystore (PKCS12) from the given resource path.
     *
     * @param config keystore config
     * @throws IllegalStateException if the resource path doesn't exist.
     */
    private @Nullable
    KeyStore keyStoreFromResource(KeyStoreConfig config) {
        try {
            try (InputStream ins = config.isFileResource() ? Files.newInputStream(Paths.get(config.getName()))
                    : SecretsClient.class.getResourceAsStream(config.getName())) {
                log.info("Loading the trust-store: " + config.getName());
                if (ins == null) {
                    throw new IllegalStateException("Can't find the trust-store for OneOps Secrets.");
                }
                KeyStore ks = KeyStore.getInstance(config.getType());
                ks.load(ins, config.getPassword());
                return ks;
            }
        } catch (IOException | GeneralSecurityException ex) {
            throw new IllegalStateException("Can't load the trust-store (" + config.getName() + ").", ex);
        }
    }

    /**
     * Return new trust managers from the trust-store.
     */
    private TrustManager[] getTrustManagers() throws GeneralSecurityException {
        final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStoreFromResource(config.getTrustStore()));
        return trustManagerFactory.getTrustManagers();
    }
}
