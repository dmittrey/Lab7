package commands;

import utility.*;

/**
 * Class for read study group from console and add this in collection
 */
public class Add extends CommandAbstract {

    private final Receiver receiver;

    public Add(Receiver aReceiver) {
        super("add", "add new element to the collection");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aCommand) {
        return receiver.add(aCommand);
    }
}