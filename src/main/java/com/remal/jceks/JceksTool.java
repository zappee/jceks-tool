package com.remal.jceks;

import com.remal.jceks.command.CopySecretKeyCommand;
import com.remal.jceks.command.ShowSecretKeyCommand;
import com.remal.jceks.picocli.CustomOptionRenderer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * JCEKS keystore command-line tool.
 *
 * <p>Copyright 2021 Arnold Somogyi</p>
 *
 * @author arnold.somogyi@gmail.com
 */
@Command(
        subcommands = {ShowSecretKeyCommand.class, CopySecretKeyCommand.class},
        synopsisSubcommandLabel = "(show | copy)",
        name = "jceks-tool",
        description = "JCEKS keystore command line tool.%n",
        usageHelpAutoWidth = true,
        commandListHeading = "%nCommands:%n",
        exitCodeListHeading = "%nExit codes:%n",
        exitCodeList = {
                CommandLine.ExitCode.OK + ": Successful program execution.",
                CommandLine.ExitCode.SOFTWARE + ": An unexpected error appeared while executing the tool.",
                CommandLine.ExitCode.USAGE + ": Exit code on Invalid input."},
        footer = JceksTool.FOOTER,
        footerHeading = JceksTool.FOOTER_HEADING)
public class JceksTool {

    /**
     * Command line interface footer.
     */
    public static final String FOOTER = "%nDocumentation, source code: https://github.com/zappee/jceks-tool.git%n";

    /**
     * Command line interface footer heading.
     */
    public static final String FOOTER_HEADING = "%nPlease report issues at arnold.somogyi@gmail.com.";

    /**
     * CLI definition: display the help message.
     */
    @Option(names = {"?", "-h", "--help"},
            description = "display this help message")

    /**
     * Entry point of the application.
     *
     * @param args command line arguments
     */
    public static void main(String... args) {
        CommandLine cmd = new CommandLine(new JceksTool());
        cmd.setHelpFactory(new CustomOptionRenderer());
        System.exit(cmd.execute(args));
    }
}
