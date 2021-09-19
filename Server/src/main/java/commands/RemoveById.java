package commands;

import utility.CollectionManager;

import java.util.Deque;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

/**
 * Class that remove object with current id from collection
 */
public class RemoveById extends CommandAbstract {

    private final CollectionManager collectionManager;
    private final Deque<String> previousCommands;

    public RemoveById(CollectionManager aCollectionManager, Deque<String> aPreviousCommands) {
        super("remove_by_id", "remove an element from the collection by ID." +
                TextFormatting.getBlueText("\n\tYou should to enter ID after entering a command"));
        collectionManager = aCollectionManager;
        previousCommands = aPreviousCommands;
    }

    @Override
    public Response execute(Request aCommand) {

        String anArg = aCommand.getArg();
        Integer anId = Integer.parseInt(anArg);

        if (!collectionManager.remove(collectionManager.getId(anId))) {
            previousCommands.pollLast();
            return new Response(TextFormatting.getRedText("\n\tAn object with this id does not exist!\n"));
        }

        return new Response(TextFormatting.getGreenText("\n\tObject has been removed!\n"));
    }
}