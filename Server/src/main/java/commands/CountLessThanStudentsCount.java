package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TypeOfAnswer;

/**
 * Class for count elements whose less than specified amount of students count
 */
public class CountLessThanStudentsCount extends CommandAbstract {

    private final Receiver receiver;

    public CountLessThanStudentsCount(Receiver aReceiver) {
        super("print the number of elements whose "
                + "studentsCount field value is less than the specified one", false);
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        String username = aRequest.getSession().getName();
        Integer count = Integer.valueOf(aRequest.getCommand().getArg());
        Long countStudyGroups = receiver.countLessThanStudentsCount(count, username);
        return (countStudyGroups == null)
                ? new Response(TypeOfAnswer.EMPTYCOLLECTION)
                : new Response(countStudyGroups, TypeOfAnswer.SUCCESSFUL);
    }
}