package commands;

import utility.*;

/**
 * Class for remove all elements from collection
 */
public class Clear extends CommandAbstract {

    private final Receiver receiver;

    public Clear(Receiver aReceiver) {
        super("clear", "clear the collection");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        return receiver.clear(username);
    }
}