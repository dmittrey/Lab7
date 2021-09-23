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
        return receiver.addIfMax(studyGroup);
    }
}