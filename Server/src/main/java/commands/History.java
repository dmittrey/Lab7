package commands;

import java.util.Queue;
import utility.Request;
import utility.Response;

/**
 * Class for displaying last 14 commands
 */
public class History extends CommandAbstract {

    Queue<String> previousCommands;

    public History(Queue<String> aPreviousCommands) {
        super("history", "print the last 14 commands (without their arguments)");
        previousCommands = aPreviousCommands;
    }

    @Override
    public Response execute(Request aCommand) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        previousCommands.stream().map(command -> ")" + command + "\n").forEach(sb::append);

        return new Response(sb.toString());
    }
}