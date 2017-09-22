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
package com.oneops.secrets;

import com.oneops.secrets.command.*;
import com.oneops.secrets.log.BriefLogFormatter;
import com.oneops.secrets.proxy.SecretsProxyException;
import io.airlift.airline.*;

import java.io.IOException;
import java.util.logging.Logger;

import static com.oneops.secrets.utils.Color.*;
import static com.oneops.secrets.utils.Common.println;
import static java.util.logging.Level.SEVERE;

/**
 * Application main function. Make sure to init logger first.
 *
 * @author Suresh G
 */
public class Main {

    /**
     * Logger instance
     */
    private static Logger log;

    /**
     * Initializes the logger. Make sure to call the init function first in your main method.
     */
    private static void init() throws IOException {
        BriefLogFormatter.init();
        log = Logger.getLogger("Secrets-CLI");
    }

    public static void main(String[] args) {
        try {
            init();
            log.info("Initializing secrets cli app.");
            Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("secrets")
                    .withDescription("CLI for managing OneOps application secrets")
                    .withDefaultCommand(Help.class)
                    .withCommands(Add.class, List.class, Clients.class, Version.class, Help.class);

            Cli<Runnable> cliParser = builder.build();
            cliParser.parse(args).run();

        } catch (SecretsProxyException e) {
            log.log(SEVERE, "Error while running the command.", e);
            println(err(e.getMessage()));
        } catch (Throwable t) {
            log.log(SEVERE, "Error while running the command.", t);
            println(err(t.getMessage()));
            println("See " + bold("'secrets help'"));
        }
    }
}
