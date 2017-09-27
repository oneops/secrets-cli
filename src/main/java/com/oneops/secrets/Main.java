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
import com.oneops.secrets.proxy.model.*;
import io.airlift.airline.*;

import java.io.IOException;
import java.util.logging.Logger;

import static com.oneops.secrets.config.CliConfig.oneOpsBaseUrl;
import static com.oneops.secrets.utils.Color.*;
import static com.oneops.secrets.utils.Common.println;
import static com.oneops.secrets.utils.Platform.getUser;
import static java.lang.System.exit;
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
        log.info("Initializing secrets cli app.");
    }

    public static void main(String[] args) {
        try {
            init();
            Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("secrets")
                    .withDescription("CLI for managing OneOps application secrets")
                    .withDefaultCommand(Help.class)
                    .withCommands(SecretAdd.class,
                            SecretUpdate.class,
                            SecretDelete.class,
                            SecretGet.class,
                            SecretVersions.class,
                            SecretList.class,
                            SecretRevert.class,
                            ClientList.class,
                            Version.class,
                            TailLog.class,
                            Help.class);

            Cli<Runnable> cliParser = builder.build();
            cliParser.parse(args).run();
            exit(0);

        } catch (SecretsProxyException e) {
            printError(e);
        } catch (Throwable t) {
            log.log(SEVERE, "Error while running the command.", t);
            println(err(t.getMessage()));
            println("See " + bold("'secrets help'"));
        }
        exit(-1);
    }

    /**
     * Prints formatted string of common secrets cli client and server errors.
     */
    private static void printError(SecretsProxyException ex) {
        log.log(SEVERE, "Error while running the command.", ex);
        StringBuilder buf = new StringBuilder();
        String lineSep = System.lineSeparator();

        if (ex.getErr() != null && ex.getCmd() != null) {
            App app = ex.getCmd().app;
            ErrorRes err = ex.getErr();

            buf.append(err(err.getMessage()));
            switch (err.getStatus()) {
                case 403: {
                    buf.append(lineSep)
                            .append(lineSep)
                            .append("Verify the followings,")
                            .append(lineSep)
                            .append("  ")
                            .append(yellow(dot(String.format("OneOps assembly '%s' exists.", app.getAssemblyNsPath().toLowerCase()))))
                            .append(lineSep)
                            .append("  ")
                            .append(yellow(dot(String.format("User '%s' is part of '%s' organization.", getUser(), app.getOrg()))))
                            .append(lineSep)
                            .append("  ")
                            .append(yellow(dot(String.format("User '%s' is part of 'secrets-admin' or 'secrets-admin-%s' team.", getUser(), app.getAssembly().toLowerCase()))))
                            .append(lineSep)
                            .append("    ")
                            .append(yellow(String.format("Contact '%s' org admin to create the team(s) if it doesn't exist.", app.getOrg())))
                            .append(lineSep)
                            .append("  ")
                            .append(yellow(dot(String.format("Secrets admin team that you are part of is assigned to '%s' assembly by navigating to", app.getAssembly()))))
                            .append(lineSep)
                            .append("    ")
                            .append(yellow(app.getTeamsUrl(oneOpsBaseUrl)));
                    break;
                }
                default:
                    break;
            }
        } else {
            buf.append(err(ex.getMessage()));
        }

        println(buf.toString());
    }
}
