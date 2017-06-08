package com.oneops.cli;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.oneops.utils.Color.*;

/**
 * A singleton object holds the current CLI context(prompt) information.
 *
 * @author Suresh
 */
public class Context {

    private static Context instance = new Context();

    public static Context cliCtx() {
        return instance;
    }

    private static boolean mask = false;

    private Context() {
    }

    /**
     * Returns the current context prompt formatted.
     */
    public @Nonnull
    String prompt() {
        return bold("vault") + red("> ");
    }

    /**
     * Right prompt string formatted, which is the local time.
     */
    public @Nonnull
    String rightPrompt() {
        return dim(gray(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
    }

    /**
     * Returns the character mask, may be <code>null</code>.
     *
     * @return character mask or null.
     */
    public static @Nullable
    Character maskChar() {
        return mask ? '*' : null;
    }

    /**
     * Check if the character mask is enabled.
     */
    public static boolean isMask() {
        return mask;
    }

    /**
     * Enable/disable the character mask for instance.
     *
     * @param mask <code>true</code> to enable the character mask.
     */
    public static void setMask(boolean mask) {
        Context.mask = mask;
    }
}
