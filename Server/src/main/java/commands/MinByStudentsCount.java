package commands;

import utility.CollectionManager;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

/**
 * Class print object form collection
 */
public class MinByStudentsCount extends CommandAbstract {

    private final CollectionManager collectionManager;

    public MinByStudentsCount(CollectionManager aCollectionManager) {
        super("min_by_students_count", "print any object from the collection whose " +
                "studentsCount field value is minimal");
        collectionManager = aCollectionManager;
    }

    @Override
    public Response execute(Request aCommand) {

        if (collectionManager.getMinStudentsCount() != null)
            return new Response(collectionManager.getMinStudentsCount());
        else
            return new Response(TextFormatting.getRedText("\n\tThere are no study groups in the collection yet!\n"));
    }
}