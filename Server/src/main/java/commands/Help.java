package commands;

import java.util.Map;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

/**
 * Class for displaying all commands with explanations
 */
public class Help extends CommandAbstract {

    private final Map<String, CommandAbstract> commandsInfo;

    public Help(Map<String, CommandAbstract> aCommands) {
        super("help", "display help for available commands");
        commandsInfo = aCommands;
    }

    @Override
    public Response execute(Request aCommand) {

        StringBuilder sb = new StringBuilder();
        sb.append(TextFormatting.getBlueText("\nList of commands:\n\n"));

        commandsInfo.keySet().stream().filter(command -> !command.equals("save")).
                map(command -> "\t" + commandsInfo.get(command).getDescription() + "\n\n").
                forEach(sb::append);

        sb.append("\t").append("exit : end the program (without saving it to a file)").append("\n\n");

        return new Response(sb.toString());
    }
}