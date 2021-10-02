package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TypeOfAnswer;

/**
 * Class for displaying all information about collection
 */
public class Info extends CommandAbstract {

    private final Receiver receiver;

    public Info(Receiver aReceiver) {
        super("Print information about the collection (type, "
                + "initialization date, number of elements, etc.) to standard output", false);
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        receiver.addToHistory(username, "info");
        return new Response(receiver.info(), TypeOfAnswer.SUCCESSFUL);
    }
}