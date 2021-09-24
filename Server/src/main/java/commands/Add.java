package commands;

import data.StudyGroup;
import utility.*;

/**
 * Class for add studyGroup in db and collection
 */
public class Add extends CommandAbstract {

    private final Receiver receiver;

    public Add(Receiver aReceiver) {
        super("add", "add new element to the collection");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        StudyGroup studyGroup = aRequest.getCommand().getStudyGroup();
        String username = aRequest.getSession().getName();
        String response = receiver.add(studyGroup);
        if (response.equals(TextFormatting.getGreenText("\n\tStudy group has been added!\n"))) {
            receiver.addToHistory(username, "add");
        }
        return new Response(response);
    }
}