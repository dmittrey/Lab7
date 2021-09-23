package commands;

import utility.Request;
import utility.Response;

import java.util.Deque;

/**
 * Class for displaying last 14 commands
 */
public class History extends CommandAbstract {

    private final Deque<String> previousCommands;

    public History(Deque<String> aPreviousCommands) {
        super("history", "print the last 14 commands (without their arguments)");
        previousCommands = aPreviousCommands;
    }

    @Override
    public Response execute(Request aCommand) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        previousCommands.stream()
                .map(command -> ")" + command + "\n")
                .forEach(sb::append);

        return new Response(sb.toString());
    }
}