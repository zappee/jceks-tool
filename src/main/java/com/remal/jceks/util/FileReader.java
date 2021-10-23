package com.remal.jceks.util;

import picocli.CommandLine;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Text file util.
 *
 * <p>Copyright 2021 Arnold Somogyi</p>
 *
 * @author arnold.somogyi@gmail.com
 */
public class FileReader {

    /**
     * Read the first line of the given text file.
     *
     * @param logWriter log writer stream
     * @param pathToFile path to the text file
     * @return the first line of the file
     */
    public static String firstLine(PrintStream logWriter, String pathToFile) {
        try {
            logWriter.printf(Constants.INFO_MESSAGE, "reading the '" + pathToFile + "' file...");
            return Files.readAllLines(Paths.get(pathToFile)).get(0);
        } catch (IOException e) {
            logWriter.printf(Constants.ERROR_MESSAGE, e);
            System.exit(CommandLine.ExitCode.SOFTWARE);
        }

        return null;
    }

    /**
     * Utility classes should not have public constructor.
     */
    private FileReader() {
    }
}
