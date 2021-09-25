package commands;

import utility.*;

/**
 * Class that remove object with current id from collection
 */
public class RemoveById extends CommandAbstract {

    private final Receiver receiver;

    public RemoveById(Receiver aReceiver) {
        super("remove_by_id", "remove an element from the collection by ID." +
                TextFormatting.getBlueText("\n\tYou should to enter ID after entering a command"));
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        int id = Integer.parseInt(aRequest.getCommand().getArg());
        return new Response(receiver.removeById(username, id));
    }
}