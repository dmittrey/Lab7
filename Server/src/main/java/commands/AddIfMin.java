package commands;

import utility.Request;
import utility.Response;
import utility.TextFormatting;
import data.StudyGroup;
import utility.CollectionManager;

import java.util.Deque;

/**
 * Class for add minimal element in collection
 */
public class AddIfMin extends CommandAbstract {

    private final CollectionManager collectionManager;
    private final Deque<String> previousCommands;

    public AddIfMin(CollectionManager aCollectionManager, Deque<String> aPreviousCommands) {
        super("add_if_min", "add new element to the collection, if it`s value less, than " +
                "smallest element of this collection.");
        collectionManager = aCollectionManager;
        previousCommands = aPreviousCommands;
    }

    @Override
    public Response execute(Request aCommand) {

        StudyGroup studyGroup = aCommand.getStudyGroup();

        if (collectionManager.getMin() != null && studyGroup.compareTo(collectionManager.getMin()) >= 0) {
            previousCommands.pollLast();
            return new Response(TextFormatting.getRedText("\n\tStudy group isn't worst!\n"));
        }

        collectionManager.add(studyGroup);
        return new Response(TextFormatting.getGreenText("\n\n\tStudy group has been added!\n"));
    }
}