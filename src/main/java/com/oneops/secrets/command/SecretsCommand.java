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
package com.oneops.secrets.command;

import com.oneops.secrets.proxy.*;
import com.oneops.secrets.proxy.model.App;
import com.oneops.secrets.utils.Platform;
import io.airlift.airline.Option;

import java.util.logging.Logger;

/**
 * Abstract secrets command. OneOps application name (also known
 * as application group) is common to all the commands.
 *
 * @author Suresh
 */
public abstract class SecretsCommand implements Runnable {

    protected Logger log = Logger.getLogger(getClass().getSimpleName());

    @Option(name = "-a", title = "Application name", description = "OneOps App name (org_assembly_env), which you have secret-admin access", required = true)
    String appName;

    @Option(name = "-u", title = "User", description = "User name")
    public String user = Platform.getUser();

    @Option(name = "-v", title = "Verbose", description = "Verbose mode")
    public boolean verbose;

    /**
     * Holds application details.
     */
    public App app;

    /**
     * Secrets proxy client.
     */
    SecretsClient secretsClient;

    /**
     * Executes the command logic.
     */
    public abstract void exec();

    @Override
    public void run() {
        app = new App(appName);
        secretsClient = SecretsUtils.getSecretsClient(this);
        exec();
    }
}
