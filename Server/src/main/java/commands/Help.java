package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

import java.util.Map;

/**
 * Class for displaying all commands with explanations
 */
public class Help extends CommandAbstract {

    private final Map<String, CommandAbstract> commands;
    private final Receiver receiver;

    public Help(Map<String, CommandAbstract> aCommands, Receiver aReceiver) {
        super("help", "display help for available commands");
        commands = aCommands;
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(TextFormatting.getBlueText("\nList of commands:\n\n"));

        commands.keySet()
                .stream()
                .filter(str -> !(str.equals("register") || str.equals("login")))
                .map(command -> "\t" + commands.get(command).getDescription() + "\n\n")
                .forEach(sb::append);
        sb.append("\t")
                .append("execute_script : Read and execute script from entered file")
                .append(TextFormatting.getBlueText("\n\tYou should to enter script name after entering a command"))
                .append("\n\n");
        sb.append("\t")
                .append("exit : end the program (without saving it to a file)");

        receiver.addToHistory(username, "help");
        return new Response(sb.toString());
    }
}