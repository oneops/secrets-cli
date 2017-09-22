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
package com.oneops.secrets.utils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static com.google.common.base.Strings.*;
import static com.oneops.secrets.utils.Color.green;

/**
 * Common static utilities.
 *
 * @author Suresh
 */
public class Common {

    /**
     * Main logger
     */
    public static final Logger log = Logger.getLogger("SecretsCLI");

    /**
     * Prints the string
     *
     * @param str string message.
     */
    public static void println(String str) {
        System.out.println(str);
    }

    /**
     * Prints the message supplied by {@link Supplier}
     *
     * @param stringSupplier string supplier
     */
    public static void println(Supplier<String> stringSupplier) {
        System.out.println(stringSupplier.get());
    }

    /**
     * Exits with error code.
     *
     * @param code error code.
     */
    public static void exit(int code) {
        println(green("Goodbye!"));
        log.info("Exiting Secrets CLI app...");
        System.exit(code);
    }

    /**
     * Center justify a string.
     *
     * @param str   input
     * @param width final size
     * @return center justified string.
     */
    public static String center(@Nonnull String str, int width) {
        if (str.length() > width) return str;
        int padSize = width - str.length();
        str = padStart(str, str.length() + padSize / 2, ' ');
        str = padEnd(str, width, ' ');
        return str;
    }

    /**
     * Check if the <code>str</code> contains in the array of <code>strings</code>
     *
     * @return <code>true</code> if the str is presents in the array of strings.
     */
    public static boolean in(String str, String... strings) {
        return Arrays.asList(strings).contains(str);
    }

    /**
     * A handy place holder for throwing {@link UnsupportedOperationException}
     */
    public static void ToDo(String msg) {
        throw new UnsupportedOperationException(msg);
    }
}
