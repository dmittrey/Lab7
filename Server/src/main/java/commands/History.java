package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TypeOfAnswer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        receiver.addToHistory(username, "history");
        ArrayBlockingQueue<String> userCommands = receiver.history(username);
        if (userCommands != null) {
            Map<String, String> userHistory = new ConcurrentHashMap<>();
            int i = 1;
            for (String command : userCommands) {
                userHistory.put(String.valueOf(i++), command);
            }
            return new Response(userHistory, TypeOfAnswer.SUCCESSFUL);
        } else {
            return new Response(TypeOfAnswer.EMPTYCOLLECTION);
        }
    }
}