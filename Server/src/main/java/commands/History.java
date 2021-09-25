package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TypeOfAnswer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Class for displaying last 14 commands
 */
public class History extends CommandAbstract {

    private final Receiver receiver;

    public History(Receiver aReceiver) {
        super("history", "print the last 14 commands (without their arguments)");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        ArrayBlockingQueue<String> userCommands = receiver.history(username);
        receiver.addToHistory(username, "history");
        if (userCommands != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            userCommands.stream()
                    .map(command -> ")" + command + "\n")
                    .forEach(sb::append);
            return new Response(sb.toString());
        } else return new Response(TypeOfAnswer.EMPTYCOLLECTION);
    }
}