package com.remal.jceks.util;

/**
 * Templates for colored log messages.
 *
 * <p>Copyright 2021 Arnold Somogyi</p>
 *
 * @author arnold.somogyi@gmail.com
 */
public class Constants {

    /**
     * ERROR level message template.
     */
    public static final String ERROR_MESSAGE = AnsiColor.RED_BOLD_BRIGHT + "%nERROR: %s%n" + AnsiColor.DEFAULT;

    /**
     * INFO level message template.
     */
    public static final String INFO_MESSAGE = AnsiColor.YELLOW_BRIGHT + "-> %s%n" + AnsiColor.DEFAULT;

    /**
     * Utility classes should not have public constructor.
     */
    private Constants() {
    }
}
