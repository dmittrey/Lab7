package commands;

import utility.CollectionManager;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

/**
 * Class to print all elements from collection in stdout
 */
public class Show extends CommandAbstract {

    private final CollectionManager collectionManager;

    public Show(CollectionManager aCollectionManager) {
        super("show", "print all elements in string representation to standard output");
        collectionManager = aCollectionManager;
    }

    @Override
    public Response execute(Request aCommand) {

        if (collectionManager.getCollection().size() == 0)
            return new Response(TextFormatting.getRedText("\n\tCollection is empty!\n"));

        return new Response(collectionManager.getCollection());
    }
}