package commands;

import data.StudyGroup;
import utility.*;

/**
 * Class for add maximum element in collection
 */
public class AddIfMax extends CommandAbstract {

    private final Receiver receiver;

    public AddIfMax(Receiver aReceiver) {
        super("add_if_max", "add new element to the collection, if it`s greater, " +
                "than biggest element of this collection.");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        StudyGroup studyGroup = aRequest.getCommand().getStudyGroup();
        String username = aRequest.getSession().getName();
        TypeOfAnswer status = receiver.addIfMax(studyGroup);
        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            receiver.addToHistory(username, "add_if_max");
        }
        return new Response(status);
    }
}