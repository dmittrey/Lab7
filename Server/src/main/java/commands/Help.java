package commands;

import utility.Request;
import utility.Response;
import utility.TextFormatting;

import java.util.Map;

/**
 * Class for displaying all commands with explanations
 */
public class Help extends CommandAbstract {

    private final Map<String, CommandAbstract> commands;

    public Help(Map<String, CommandAbstract> aCommands) {
        super("help", "display help for available commands");
        commands = aCommands;
    }

    @Override
    public Response execute(Request aRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(TextFormatting.getBlueText("\nList of commands:\n\n"));

        commands.keySet()
                .stream()
                .map(command -> "\t" + commands.get(command).getDescription() + "\n\n")
                .forEach(sb::append);
        sb.append("\t")
                .append("execute_script : Read and execute script from entered file")
                .append(TextFormatting.getBlueText("\n\tYou should to enter script name after entering a command"))
                .append("\n\n");
        sb.append("\t")
                .append("exit : end the program (without saving it to a file)")
                .append("\n\n");

        return new Response(sb.toString());
    }
}