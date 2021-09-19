package commands;

import data.StudyGroup;
import utility.CollectionManager;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

import java.util.Deque;

/**
 * Class to update study groups in collection by id
 */
public class UpdateId extends CommandAbstract {

    private final CollectionManager collectionManager;
    private final Deque<String> previousCommands;

    public UpdateId(CollectionManager aCollectionManager, Deque<String> aPreviousCommands) {
        super("update", "update the element`s value, whose ID is equal to the given. " +
                TextFormatting.getBlueText("\n\tYou should to enter ID after entering a command"));
        collectionManager = aCollectionManager;
        previousCommands = aPreviousCommands;
    }

    @Override
    public Response execute(Request aCommand) {

        String anArg = aCommand.getArg();
        StudyGroup upgradedGroup = aCommand.getStudyGroup();

        Object studyGroup = collectionManager.getId(Integer.parseInt(anArg));

        if (studyGroup != null) collectionManager.remove((StudyGroup) studyGroup);
        else {
            previousCommands.pollLast();
            return new Response(TextFormatting.getRedText("\n\tAn object with this id does not exist!\n"));
        }

        upgradedGroup.setId(Integer.parseInt(anArg));
        collectionManager.add(upgradedGroup);

        return new Response(TextFormatting.getGreenText("\n\tObject has been updated!\n"));
    }
}