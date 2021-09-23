package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;

/**
 * Class to save collection in xml file
 */
public class Save extends CommandAbstract {

    private final Receiver receiver;

    public Save(Receiver aReceiver) {
        super("save", "save the collection to file");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aCommand) {
        return receiver.save();
    }
}