package commands;

import utility.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

        Map<String, String> setOfCommands = commands.keySet()
                .stream()
                .filter(str -> !(str.equals("register") || str.equals("login")))
                .collect(Collectors.toMap(command -> command, command -> commands.get(command).getDescription()));

        receiver.addToHistory(username, "help");
        return new Response(setOfCommands, TypeOfAnswer.SUCCESSFUL);
    }
}