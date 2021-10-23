package com.remal.jceks.command;

import com.remal.jceks.JceksTool;
import com.remal.jceks.keystore.JceksKeystore;
import com.remal.jceks.util.DevNullPrintStream;
import com.remal.jceks.util.FileReader;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Implementation of the 'show' command.
 *
 * <p>Copyright 2021 Arnold Somogyi</p>
 *
 * @author arnold.somogyi@gmail.com
 */
@Command(
        name = "show",
        sortOptions = false,
        usageHelpAutoWidth = true,
        description = "Show the value of a secret key.",
        descriptionHeading = "%n",
        optionListHeading = "%n",
        footerHeading = JceksTool.FOOTER_HEADING,
        footer = JceksTool.FOOTER)
public class ShowSecretKeyCommand implements Callable<Integer> {

    /**
     * CLI definition: display the help message.
     */
    @Option(names = {"-q", "--quiet"},
            description = "In this mode nothing will be printed to the output.")
    private boolean quiet;

    /**
     * CLI definition: set the keystore path.
     */
    @Option(names = {"-k", "--keystore"},
            description = "path to the keystore",
            required = true)
    String keystoreLocation;

    /**
     * Exclusive CLI group definition for keystore password.
     */
    @ArgGroup(multiplicity = "1")
    KeystorePasswordGroup keystorePasswordGroup;

    static class KeystorePasswordGroup {

        /**
         * CLI definition: set the keystore password.
         */
        @Option(names = {"-p", "--keystore-password"},
                description = "password for the keystore",
                required = true)
        String keystorePassword;

        /**
         * CLI definition: set the path of the keystore password file.
         */
        @Option(names = {"-f", "--keystore-password-file"},
                description = "keystore password file",
                required = true)
        String keystorePasswordFile;
    }

    /**
     * CLI definition: set the keystore alias name.
     */
    @Option(names = {"-a", "--alias"},
            description = "alias name of the keystore entry",
            required = true)
    String alias;

    /**
     * Exclusive CLI group definition for the keystore entry password.
     */
    @ArgGroup(multiplicity = "1")
    EntryPasswordGroup entryPasswordGroup;

    static class EntryPasswordGroup {

        /**
         * CLI definition: set the keystore entry password.
         */
        @Option(names = {"-e", "--entry-password"},
                description = "password for the keystore entry",
                required = true)
        String entryPassword;

        /**
         * CLI definition: set the path of the keystore entry password file.
         */
        @Option(names = {"-n", "--entry-password-file"},
                description = "keystore entry password file",
                required = true)
        String entryPasswordFile;
    }

    /**
     * Execute the command and computes a result.
     *
     * @return exit code
     */
    @Override
    public Integer call() {
        PrintStream logWriter = this.quiet ? DevNullPrintStream.getPrintStream() : System.out;

        String keystorePassword = Objects.isNull(keystorePasswordGroup.keystorePassword)
                ? FileReader.firstLine(logWriter, keystorePasswordGroup.keystorePasswordFile)
                : keystorePasswordGroup.keystorePassword;

        String entryPassword = Objects.isNull(entryPasswordGroup.entryPassword)
                ? FileReader.firstLine(logWriter, entryPasswordGroup.entryPasswordFile)
                : entryPasswordGroup.entryPassword;

        JceksKeystore keystore = new JceksKeystore(logWriter, keystoreLocation, keystorePassword.toCharArray());
        String secretKey = keystore.getSecretKeyValue(alias, entryPassword.toCharArray());
        logWriter.printf("%n-----BEGIN PRIVATE KEY-----%n%s%n------END PRIVATE KEY------%n", secretKey);

        return CommandLine.ExitCode.OK;
    }
}
