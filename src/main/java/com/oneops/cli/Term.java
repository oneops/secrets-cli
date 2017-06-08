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
package com.oneops.cli;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

/**
 * System terminal utilities
 *
 * @author Suresh
 */
public class Term {

    /**
     * Returns the system terminal that handle an OS terminal window.
     * There is only a single system terminal for a given JVM, the
     * one that has been used to launch the JVM.
     */
    public static final Terminal sysTerm;

    /**
     * Terminal reader, allows reading lines from a terminal,
     * with full input/line editing.
     */
    public static final LineReader lineReader;

    /**
     * Default terminal signal handler.
     */
    public static final TermSignalHandler termHandler;

    private Term() {
    }

    static {
        try {
            sysTerm = TerminalBuilder.builder()
                    .system(true)
                    .name("Keywhiz-CLI")
                    .nativeSignals(true)
                    .signalHandler(Terminal.SignalHandler.SIG_IGN)
                    .build();

            lineReader = LineReaderBuilder.builder()
                    .terminal(sysTerm)
                    .appName(sysTerm.getName())
                    .build();
            lineReader.setOpt(LineReader.Option.AUTO_FRESH_LINE);
            termHandler = new TermSignalHandler();

        } catch (IOException e) {
            throw new IllegalStateException("Can't initialize the system terminal", e);
        }
    }

    /**
     * Writes the input [string] to console simultaneously doing the input (typing).
     * This will make sure that the output doesn't interleave with the input text.
     */
    public static void writeAtBottom(String string) {
        // Clean the used display.
        lineReader.callWidget(LineReader.CLEAR);
        sysTerm.writer().println(string);
        // Force a redraw of the line
        lineReader.callWidget(LineReader.REDRAW_LINE);
        lineReader.callWidget(LineReader.REDISPLAY);
        sysTerm.writer().flush();
    }

    /**
     * Clear the screen.
     */
    public static void clearScreen() {
        sysTerm.puts(InfoCmp.Capability.clear_screen);
        sysTerm.flush();
    }

    /**
     * Prints the terminal info useful for logging.
     */
    public static String info() {
        return sysTerm.getType() + "(Name: " + sysTerm.getName() + ", " + sysTerm.getSize() + ")";
    }

    /**
     * Prints the string to terminal and flush.
     */
    public static void tPrintln(String string) {
        sysTerm.writer().println(string);
        sysTerm.flush();
    }
}
