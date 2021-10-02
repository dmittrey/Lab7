package commands;

import data.StudyGroup;
import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TypeOfAnswer;

import java.util.Set;

/**
 * Class for print elements which name field starts with special substring
 */
public class FilterStartsWithName extends CommandAbstract {

    private final Receiver receiver;

    public FilterStartsWithName(Receiver aReceiver) {
        super("output elements whose name field value starts " +
                "with the specified substring", false);
        receiver = aReceiver;
    }

    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        String startName = aRequest.getCommand().getArg();
        Set<StudyGroup> setOfGroups = receiver.filterStartsWithName(startName, username);
        return (setOfGroups == null)
                ? new Response(TypeOfAnswer.EMPTYCOLLECTION)
                : (setOfGroups.size() == 0)
                ? new Response(TypeOfAnswer.OBJECTNOTEXIST)
                : new Response(setOfGroups, TypeOfAnswer.SUCCESSFUL);
    }
}