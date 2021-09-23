package commands;

import utility.*;

/**
 * Class to print all elements from collection in stdout
 */
public class Show extends CommandAbstract {

    private final Receiver receiver;

    public Show(Receiver aReceiver) {
        super("show", "print all elements in string representation to standard output");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aCommand) {
        return receiver.show();
    }
}