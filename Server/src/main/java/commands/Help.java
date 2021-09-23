package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;

/**
 * Class for displaying all commands with explanations
 */
public class Help extends CommandAbstract {

    private final Receiver receiver;

    public Help(Receiver aReceiver) {
        super("help", "display help for available commands");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aCommand) {
        return receiver.help();
    }
}