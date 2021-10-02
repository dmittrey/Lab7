package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;

/**
 * Class that remove object with current id from collection
 */
public class RemoveById extends CommandAbstract {

    private final Receiver receiver;

    public RemoveById(Receiver aReceiver) {
        super("remove an element from the collection by ID", true);
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        int id = Integer.parseInt(aRequest.getCommand().getArg());
        return new Response(receiver.removeById(username, id));
    }
}