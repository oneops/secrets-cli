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
package com.oneops.security;

import okhttp3.*;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.google.common.net.HttpHeaders.COOKIE;

/**
 * Http request interceptor to handle server-side XSRF protection.
 * If the server set a cookie with a specified name, the client will
 * send a header with each request with a specified name and value of
 * the server-supplied cookie.
 *
 * @author Suresh
 */

public class XsrfTokenInterceptor implements Interceptor {

    private Logger log = Logger.getLogger(XsrfTokenInterceptor.class.getSimpleName());

    private String xsrfCookieName = "XSRF-TOKEN";
    private String xsrfHeaderName = "X-XSRF-TOKEN";

    public XsrfTokenInterceptor() {
    }

    public XsrfTokenInterceptor(@Nonnull  String xsrfCookieName,
                                @Nonnull String xsrfHeaderName) {
        this.xsrfCookieName = xsrfCookieName;
        this.xsrfHeaderName = xsrfHeaderName;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request req = chain.request();

        //req.headers(COOKIE)
        return chain.proceed(req);

    }

    public String getXsrfCookieName() {
        return xsrfCookieName;
    }

    public String getXsrfHeaderName() {
        return xsrfHeaderName;
    }



}

