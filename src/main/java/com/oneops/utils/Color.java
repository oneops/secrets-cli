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
package com.oneops.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ANSI color for formatting.
 *
 * @author Suresh
 */
public class Color {

    /**
     * Returns formatted string with given ANSI color code string.
     *
     * @param msg     String to be colored
     * @param escCode ANSI color code string.
     * @return formatted string.
     */
    public static String esc(String msg, String escCode) {
        return "\u001B[" + escCode + "m" + msg + "\u001b[0m";
    }

    /**
     * Returns formatted string with given ANSI color codes.
     *
     * @param msg   String to be colored
     * @param codes ANSI color codes.
     * @return formatted string.
     */
    public static String color(String msg, int... codes) {
        switch (codes.length) {
            case 0:
                return msg;
            case 1:
                return esc(msg, String.valueOf(codes[0]));
            default:
                List<String> codeList = Arrays.stream(codes).mapToObj(Integer::toString).collect(Collectors.toList());
                String escCode = String.join(";", codeList);
                return esc(msg, escCode);
        }
    }

    public static String bold(String msg) {
        return color(msg, 1);
    }

    public static String dim(String msg) {
        return color(msg, 2);
    }

    public static String italic(String msg) {
        return color(msg, 3);
    }

    public static String underline(String msg) {
        return color(msg, 4);
    }

    public static String blink(String msg) {
        return color(msg, 5);
    }

    public static String reversed(String msg) {
        return color(msg, 5);
    }

    public static String strike(String msg) {
        return color(msg, 9);
    }

    public static String black(String msg) {
        return color(msg, 30);
    }

    public static String red(String msg) {
        return color(msg, 31);
    }

    public static String green(String msg) {
        return color(msg, 32);
    }

    public static String yellow(String msg) {
        return color(msg, 33);
    }

    public static String blue(String msg) {
        return color(msg, 34);
    }

    public static String magenta(String msg) {
        return color(msg, 35);
    }

    public static String cyan(String msg) {
        return color(msg, 36);
    }

    public static String gray(String msg) {
        return color(msg, 37);
    }

    /**
     * Success string
     */
    public static String sux(String msg) {
        return cyan("\u2713 " + msg);
    }

    /**
     * Error string
     */
    public static String err(String msg) {
        return red("\u2717 " + msg);
    }

    /**
     * Warn string
     */
    public static String warn(String msg) {
        return yellow("\u27A4 " + msg);
    }

    /**
     * High voltage string
     */
    public static String highvolt(String msg) {
        return yellow("\u26A1 " + msg);
    }

    /**
     * Completed (Beer Glass) string.
     */
    public static String done(String msg) {
        return green("\uD83C\uDF7A " + msg);
    }

    /**
     * OK option string.
     */
    public static String tick(String msg) {
        return green("[✓] ") + msg;
    }
}
