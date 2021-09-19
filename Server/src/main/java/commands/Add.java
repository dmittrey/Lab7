package commands;

import data.StudyGroup;
import utility.CollectionManager;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

/**
 * Class for read study group from console and add this in collection
 */
public class Add extends CommandAbstract {

    private final CollectionManager collectionManager;

    public Add(CollectionManager aCollectionManager) {
        super("add", "add new element to the collection");
        collectionManager = aCollectionManager;
    }

    @Override
    public Response execute(Request aCommand) {

        StudyGroup studyGroup = aCommand.getStudyGroup();

        collectionManager.add(studyGroup);

        return new Response(TextFormatting.getGreenText("\n\tStudy group has been added!\n"));
    }
}