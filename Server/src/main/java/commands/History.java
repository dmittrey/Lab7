package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;

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
        return receiver.history(username);
    }
}