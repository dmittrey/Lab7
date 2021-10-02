package commands;

import data.StudyGroup;
import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TypeOfAnswer;

/**
 * Class print object form collection
 */
public class MinByStudentsCount extends CommandAbstract {

    private final Receiver receiver;

    public MinByStudentsCount(Receiver aReceiver) {
        super("print object from the collection whose " +
                "studentsCount field value is minimal", false);
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        StudyGroup minGroup = receiver.minByStudentsCount(username);
        return (minGroup == null)
                ? new Response(TypeOfAnswer.EMPTYCOLLECTION)
                : new Response(minGroup, TypeOfAnswer.SUCCESSFUL);
    }
}