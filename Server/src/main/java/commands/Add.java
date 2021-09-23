package commands;

import utility.*;

/**
 * Class for add studyGroup in db and collection
 */
public class Add extends CommandAbstract {

    private final Receiver receiver;

    public Add(Receiver aReceiver) {
        super("add", "add new element to the collection");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        return receiver.add(aRequest);
    }
}