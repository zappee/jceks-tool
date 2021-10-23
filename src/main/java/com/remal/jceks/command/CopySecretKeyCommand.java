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
import java.security.KeyStore;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Implementation of the 'copy' command.
 *
 * <p>Copyright 2021 Arnold Somogyi</p>
 *
 * @author arnold.somogyi@gmail.com
 */
@Command(
        name = "copy",
        sortOptions = false,
        usageHelpAutoWidth = true,
        description = "Copy a secret key from the source keystore to a target keystore.",
        descriptionHeading = "%n",
        optionListHeading = "%n",
        footerHeading = JceksTool.FOOTER_HEADING,
        footer = JceksTool.FOOTER)
public class CopySecretKeyCommand implements Callable<Integer> {

    /**
     * CLI definition: display the help message.
     */
    @Option(names = {"-q", "--quiet"},
            description = "In this mode nothing will be printed to the output.")
    private boolean quiet;

    /**
     * CLI definition: set the source keystore path.
     */
    @Option(
            names = {"-s", "--source-keystore"},
            description = "path to the source keystore",
            required = true)
    String sourceKeystoreLocation;

    /**
     * Exclusive CLI group definition for source keystore password.
     */
    @ArgGroup(multiplicity = "1")
    SourceKeystorePasswordGroup sourceKeystorePasswordGroup;

    static class SourceKeystorePasswordGroup {

        /**
         * CLI definition: set the source keystore password.
         */
        @Option(
                names = {"-p", "--source-keystore-password"},
                description = "password for the source keystore",
                required = true)
        String sourceKeystorePassword;

        /**
         * CLI definition: set the path of the source keystore password file.
         */
        @Option(
                names = {"-f", "--source-keystore-password-file"},
                description = "source keystore password file",
                required = true)
        String sourceKeystorePasswordFile;
    }

    /**
     * CLI definition: set the source keystore alias name.
     */
    @Option(
            names = {"-a", "--source-alias"},
            description = "alias name of the source keystore entry",
            required = true)
    String sourceAlias;

    /**
     * Exclusive CLI group definition for the source keystore entry password.
     */
    @ArgGroup(multiplicity = "1")
    SourceEntryPasswordGroup sourceEntryPasswordGroup;

    static class SourceEntryPasswordGroup {

        /**
         * CLI definition: set the source keystore entry password.
         */
        @Option(
                names = {"-e", "--source-entry-password"},
                description = "password for the source keystore entry",
                required = true)
        String sourceEntryPassword;

        /**
         * CLI definition: set the path of the source keystore entry password file.
         */
        @Option(
                names = {"-n", "--source-entry-password-file"},
                description = "source keystore entry password file",
                required = true)
        String sourceEntryPasswordFile;
    }

    /**
     * CLI definition: set the target keystore path.
     */
    @Option(
            names = {"-t", "--target-keystore"},
            description = "path to the target keystore",
            required = true)
    String targetKeystoreLocation;

    /**
     * Exclusive CLI group definition for target keystore password.
     */
    @ArgGroup(multiplicity = "1")
    TargetKeystorePasswordGroup targetKeystorePasswordGroup;

    static class TargetKeystorePasswordGroup {

        /**
         * CLI definition: set the target keystore password.
         */
        @Option(
                names = {"-o", "--target-keystore-password"},
                description = "password for the target keystore",
                required = true)
        String targetKeystorePassword;

        /**
         * CLI definition: set the path of the target keystore password file.
         */
        @Option(
                names = {"-u", "--target-keystore-password-file"},
                description = "target keystore password file",
                required = true)
        String targetKeystorePasswordFile;
    }

    /**
     * CLI definition: set the target keystore alias name.
     */
    @Option(
            names = {"-l", "--target-alias"},
            description = "alias name of the target keystore entry",
            required = true)
    String targetAlias;

    /**
     * Exclusive CLI group definition for the target keystore entry password.
     */
    @ArgGroup(multiplicity = "1")
    TargetEntryPasswordGroup targetEntryPasswordGroup;

    static class TargetEntryPasswordGroup {

        /**
         * CLI definition: set the target keystore entry password.
         */
        @Option(
                names = {"-r", "--target-entry-password"},
                description = "password for the target keystore entry",
                required = true)
        String targetEntryPassword;

        /**
         * CLI definition: set the path of the target keystore entry password file.
         */
        @Option(
                names = {"-z", "--target-entry-password-file"},
                description = "target keystore entry password file",
                required = true)
        String targetEntryPasswordFile;
    }

    /**
     * Execute the command and computes a result.
     *
     * @return exit code
     */
    @Override
    public Integer call() {
        PrintStream logWriter = this.quiet ? DevNullPrintStream.getPrintStream() : System.out;

        String sourceKeystorePassword = Objects.isNull(sourceKeystorePasswordGroup.sourceKeystorePassword)
                ? FileReader.firstLine(logWriter, sourceKeystorePasswordGroup.sourceKeystorePasswordFile)
                : sourceKeystorePasswordGroup.sourceKeystorePassword;

        String sourceEntryPassword = Objects.isNull(sourceEntryPasswordGroup.sourceEntryPassword)
                ? FileReader.firstLine(logWriter, sourceEntryPasswordGroup.sourceEntryPasswordFile)
                : sourceEntryPasswordGroup.sourceEntryPassword;

        String targetKeystorePassword = Objects.isNull(targetKeystorePasswordGroup.targetKeystorePassword)
                ? FileReader.firstLine(logWriter, targetKeystorePasswordGroup.targetKeystorePasswordFile)
                : targetKeystorePasswordGroup.targetKeystorePassword;

        String targetEntryPassword = Objects.isNull(targetEntryPasswordGroup.targetEntryPassword)
                ? FileReader.firstLine(logWriter, targetEntryPasswordGroup.targetEntryPasswordFile)
                : targetEntryPasswordGroup.targetEntryPassword;

        JceksKeystore sourceKeystore = new JceksKeystore(
                logWriter,
                sourceKeystoreLocation,
                sourceKeystorePassword.toCharArray());

        JceksKeystore targetKeystore = new JceksKeystore(
                logWriter,
                targetKeystoreLocation,
                targetKeystorePassword.toCharArray());

        KeyStore.Entry entry = sourceKeystore.getEntry(sourceAlias, sourceEntryPassword.toCharArray());
        targetKeystore.addEntry(entry, targetAlias, targetEntryPassword.toCharArray());

        return CommandLine.ExitCode.OK;
    }
}
