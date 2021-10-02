package commands;

import data.StudyGroup;
import utility.Receiver;
import utility.Request;
import utility.Response;

/**
 * Class to update study groups in collection by id
 */
public class UpdateId extends CommandAbstract {

    private final Receiver receiver;

    public UpdateId(Receiver aReceiver) {
        super("update the element`s value, whose ID is equal to the given", true);
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        StudyGroup upgradedGroup = aRequest.getCommand().getStudyGroup();
        int id = Integer.parseInt(aRequest.getCommand().getArg());
        return new Response(receiver.updateId(upgradedGroup, id));
    }
}