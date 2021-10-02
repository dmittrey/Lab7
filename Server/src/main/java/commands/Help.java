package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TypeOfAnswer;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class for displaying all commands with explanations
 */
public class Help extends CommandAbstract {

    private final Map<String, CommandAbstract> commands;
    private final Receiver receiver;

    public Help(Map<String, CommandAbstract> aCommands, Receiver aReceiver) {
        super("display help for available commands", false);
        commands = aCommands;
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();

        Map<String, String> mapOfCommands = commands.keySet()
                .stream()
                .filter(str -> !(str.equals("register") || str.equals("login")))
                .collect(Collectors.toConcurrentMap(command -> command, command -> commands.get(command).getDescription()));
        mapOfCommands.put("execute_script", "Read and execute script from entered file");
        mapOfCommands.put("exit", "end the program");
        receiver.addToHistory(username, "help");
        return new Response(mapOfCommands, TypeOfAnswer.SUCCESSFUL);
    }
}