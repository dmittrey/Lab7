package commands;

import utility.*;

/**
 * Class for displaying all information about collection
 */
public class Info extends CommandAbstract {

    private final Receiver receiver;

    public Info(Receiver aReceiver) {
        super("info", "Print information about the collection (type, "
                + "initialization date, number of elements, etc.) to standard output");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aCommand) {
        return receiver.info();
    }
}