package commands;

import data.StudyGroup;
import utility.*;

/**
 * Class for add minimal element in collection
 */
public class AddIfMin extends CommandAbstract {

    private final Receiver receiver;

    public AddIfMin(Receiver aReceiver) {
        super("add_if_min", "add new element to the collection, if it`s value less, than " +
                "smallest element of this collection");
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