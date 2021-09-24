package commands;

import utility.*;

/**
 * Class for print elements which name field starts with special substring
 */
public class FilterStartsWithName extends CommandAbstract {

    private final Receiver receiver;

    public FilterStartsWithName(Receiver aReceiver) {
        super("filter_starts_with_name", "output elements whose name field value starts " +
                "with the specified substring");
        receiver = aReceiver;
    }

    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        String startName = aRequest.getCommand().getArg();
        return receiver.filterStartsWithName(startName, username);
    }
}