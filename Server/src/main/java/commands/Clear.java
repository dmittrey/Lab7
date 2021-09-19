package commands;

import utility.CollectionManager;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

/**
 * Class for remove all elements from collection
 */
public class Clear extends CommandAbstract {

    private final CollectionManager collectionManager;

    public Clear(CollectionManager aCollectionManager) {
        super("clear", "clear the collection");
        collectionManager = aCollectionManager;
    }

    @Override
    public Response execute(Request aCommand) {
        collectionManager.clear();

        return new Response(TextFormatting.getGreenText("\n\tSuccessful!\n"));
    }
}