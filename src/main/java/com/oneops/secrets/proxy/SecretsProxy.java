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

import com.oneops.secrets.proxy.model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Secrets Proxy Rest API interface.
 *
 * @author Suresh
 */
public interface SecretsProxy {

    String prefix = "/v1/apps";

    String authPrefix = "/v1/auth";

    @POST(authPrefix + "/token")
    Call<TokenRes> token(@Body TokenReq req);

    @GET(authPrefix + "/user")
    Call<AuthUser> getAuthUser(@Header("X-Authorization") String token);

    @GET(prefix + "/{group}")
    Call<Group> getGroupDetails(@Path("group") String group);

    @GET(prefix + "/{group}/clients")
    Call<List<Client>> getAllClients(@Path("group") String group);

    @GET(prefix + "/{group}/clients/{client}")
    Call<Client> getClientDetails(@Path("group") String group, @Path("client") String client);

    @DELETE(prefix + "/{group}/clients/{client}")
    Call<Void> deleteClient(@Path("group") String group, @Path("client") String client);

    @GET(prefix + "/{group}/secrets")
    Call<List<Secret>> getAllSecrets(@Path("group") String group);

    @GET(prefix + "/{group}/secrets/expiring/{time}")
    Call<List<String>> getAllSecretsExpiring(@Path("group") String group, @Path("time") long time);

    @POST(prefix + "/{group}/secrets/{name}")
    Call<Void> createSecret(@Path("group") String group, @Path("name") String name, @Query("createGroup") boolean createGroup, @Body SecretReq secretReq);

    @PUT(prefix + "/{group}/secrets/{name}")
    Call<Void> updateSecret(@Path("group") String group, @Path("name") String name, @Body SecretReq secretReq);

    @GET(prefix + "/{group}/secrets/{name}")
    Call<Secret> getSecret(@Path("group") String group, @Path("name") String name);

    @GET(prefix + "/{group}/secrets/{name}/versions")
    Call<List<Secret>> getSecretVersions(@Path("group") String group, @Path("name") String name);

    @DELETE(prefix + "/{group}/secrets/{name}")
    Call<Void> deleteSecret(@Path("group") String group, @Path("name") String name);

    @DELETE(prefix + "/{group}/secrets")
    Call<List<String>> deleteAllSecrets(@Path("group") String group);

    @POST(prefix + "/{group}/secrets/{name}/setversion")
    Call<Void> setSecretVersion(@Path("group") String group, @Path("name") String name, @Body SecretVersion secretVersion);

    @POST(prefix + "/{group}/secrets/{name}/contents")
    Call<SecretContent> getSecretContents(@Path("group") String group, @Path("name") String name);
}