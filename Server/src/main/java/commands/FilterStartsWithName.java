package commands;

import data.StudyGroup;
import utility.CollectionManager;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for print elements which name field starts with special substring
 */
public class FilterStartsWithName extends CommandAbstract {

    private final HashSet<StudyGroup> collection;

    public FilterStartsWithName(CollectionManager aCollectionManager) {
        super("filter_starts_with_name", "output elements whose name field value starts " +
                "with the specified substring");
        collection = aCollectionManager.getCollection();
    }

    public Response execute(Request aCommand) {

        String anArg = aCommand.getArg();

        if (collection.size() == 0) return new Response(TextFormatting.getRedText("\n\tCollection is empty!\n"));

        Set<StudyGroup> groups = collection.stream().
                filter(studyGroup -> studyGroup.getName().startsWith(anArg)).
                collect(Collectors.toSet());

        if (groups.isEmpty()) return new Response(TextFormatting.getRedText("\n\tNo objects found!\n"));

        return new Response(groups);
    }
}