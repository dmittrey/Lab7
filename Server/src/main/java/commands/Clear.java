package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;

/**
 * Class for remove all elements from collection
 */
public class Clear extends CommandAbstract {

    private final Receiver receiver;

    public Clear(Receiver aReceiver) {
        super("clear the collection", true);
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        return new Response(receiver.clear(username));
    }
}