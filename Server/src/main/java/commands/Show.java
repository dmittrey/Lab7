package commands;

import data.StudyGroup;
import utility.TypeOfAnswer;
import utility.*;

import java.util.Set;

/**
 * Class to print all elements from collection in stdout
 */
public class Show extends CommandAbstract {

    private final Receiver receiver;

    public Show(Receiver aReceiver) {
        super("show", "print all elements in string representation to standard output");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aCommand) {
        String username = aCommand.getSession().getName();
        receiver.addToHistory(username, "show");

        Set<StudyGroup> studyGroups = receiver.show();
        if (studyGroups == null) return new Response(TypeOfAnswer.EMPTYCOLLECTION);
        else return new Response(studyGroups, TypeOfAnswer.SUCCESSFUL);
    }
}