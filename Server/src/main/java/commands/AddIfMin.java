package commands;

import data.StudyGroup;
import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TypeOfAnswer;

/**
 * Class for add minimal element in collection
 */
public class AddIfMin extends CommandAbstract {

    private final Receiver receiver;

    public AddIfMin(Receiver aReceiver) {
        super("add new element to the collection, if it`s value less, than " +
                "smallest element of this collection", true);
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        StudyGroup studyGroup = aRequest.getCommand().getStudyGroup();
        String username = aRequest.getSession().getName();
        TypeOfAnswer status = receiver.addIfMin(studyGroup);
        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            receiver.addToHistory(username, "add_if_min");
        }
        return new Response(status);
    }
}