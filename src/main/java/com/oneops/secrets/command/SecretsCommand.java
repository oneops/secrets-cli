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
import io.airlift.airline.Option;

import java.util.logging.Logger;

import static com.google.common.base.Strings.isNullOrEmpty;
import static io.airlift.airline.OptionType.GLOBAL;

/**
 * Abstract secrets command. OneOps application name (also known
 * as application group) is common to all the commands.
 *
 * @author Suresh
 */
public abstract class SecretsCommand implements Runnable {

    protected Logger log = Logger.getLogger(getClass().getSimpleName());

    @Option(type = GLOBAL, name = "-a", title = "Application name", description = "OneOps App name (org_assembly_env), which you have secret-admin access", required = true)
    public String app;

    @Option(type = GLOBAL, name = "-v", title = "Verbose", description = "Verbose mode")
    public boolean verbose;

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
        if (!getClass().isAssignableFrom(Version.class)) {
            validateApp();
            secretsClient = SecretsUtils.getSecretsClient();
        }
        exec();
    }

    /**
     * Helper method to validate the application name format on client side.
     */
    private void validateApp() {
        String[] paths = app.split("_");
        if (paths.length != 3 || isNullOrEmpty(paths[0]) || isNullOrEmpty(paths[1]) || isNullOrEmpty(paths[2])) {
            throw new SecretsProxyException("Invalid application name: " + app + ". The format is 'org_assembly_env'.");
        }
    }
}
