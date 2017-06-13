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
package com.oneops;

import com.oneops.cli.Context;
import com.oneops.cli.Term;
import com.oneops.log.BriefLogFormatter;
import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.oneops.cli.Context.cliCtx;
import static com.oneops.cli.Term.*;
import static com.oneops.config.CliConfig.banner;
import static com.oneops.utils.Color.bold;
import static com.oneops.utils.Color.err;
import static com.oneops.utils.Common.*;

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
     * Holds the program's running state.
     */
    private static AtomicBoolean running = new AtomicBoolean(true);

    /**
     * Initalizes the logger. Make sure to call the
     * init function first in your main method.
     *
     * @throws IOException
     */
    private static void init() throws IOException {
        BriefLogFormatter.init();
        log = Logger.getLogger("Keywhiz");
    }

    public static void main(String[] args) {
        try {
            init();
            log.info("Initializing the system terminal, " + Term.info());
            sysTerm.handle(Terminal.Signal.INT, termHandler);
            sysTerm.handle(Terminal.Signal.TSTP, termHandler);
            clearScreen();
            println(banner());

            while (running.get()) {
                String text = lineReader.readLine(cliCtx().prompt(), cliCtx().rightPrompt(), Context.maskChar(), null);
                text = (text != null) ? text.toLowerCase() : "";

                if (in(text, "clear", "cls")) {
                    clearScreen();
                } else if (in(text, "help", "?")) {
                    ToDo(text);
                } else if (in(text, "exit", "quit")) {
                    exit(0);
                } else {
                    println(text);
                }
            }
        } catch (UserInterruptException | EndOfFileException ignore) {
        } catch (Throwable err) {
            log.log(Level.SEVERE, "Got unrecoverable error.", err);
            println(err("Oops..something went wrong!"));
            println("Check the log (" + bold(BriefLogFormatter.logDir + "/OneOps-KeywhizCli-*.log") + ") for more details.");
        } finally {// Restore the handlers.
        }
        exit(0);
    }
}
