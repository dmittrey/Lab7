package commands;

import utility.CollectionManager;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

/**
 * Class for displaying all information about collection
 */
public class Info extends CommandAbstract {

    private final CollectionManager collectionManager;

    public Info(CollectionManager aCollectionManager) {
        super("info", "Print information about the collection (type, "
                + "initialization date, number of elements, etc.) to standard output");
        collectionManager = aCollectionManager;
    }

    @Override
    public Response execute(Request aCommand) {

        return new Response(TextFormatting.getBlueText("\nInformation about collection:\n")
                + collectionManager.getInfo());
    }
}