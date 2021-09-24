package utility;

import Database.DBWorker;
import data.StudyGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Receiver {

    private final CollectionManager collectionManager;
    private final DBWorker dbWorker;
    private final Map<String, ArrayDeque<String>> previousCommands;
    public static final Logger logger = LoggerFactory.getLogger("Register");

    public Receiver(CollectionManager aCollectionManager, DBWorker aDBWorker) {

        collectionManager = aCollectionManager;
        dbWorker = aDBWorker;
        previousCommands = new HashMap<>();
    }

    public Response info() {

        return new Response(TextFormatting.getBlueText("\nInformation about collection:\n")
                + collectionManager.getInfo());
    }

    public Response show() {

        if (collectionManager.getCollection().size() == 0)
            return new Response(TextFormatting.getRedText("\n\tCollection is empty!\n"));

        return new Response(collectionManager.getCollection());
    }

    public String add(StudyGroup aStudyGroup) {

        Integer id = dbWorker.addStudyGroup(aStudyGroup);

        if (id != 0) {
            collectionManager.add(aStudyGroup.setId(id));
            return TextFormatting.getGreenText("\n\tStudy group has been added!\n");
        } else {
            return TextFormatting.getRedText("\tThis element probably duplicates " +
                    "existing one and can't be added\n");
        }
    }

    public Response updateId(StudyGroup anUpgradedGroup, int anId) {

        String status = dbWorker.updateById(anUpgradedGroup, anId);

        if (status == null) {
            StudyGroup studyGroup = collectionManager.getId(anId);
            collectionManager.remove(studyGroup);
            anUpgradedGroup.setId(anId);
            collectionManager.add(anUpgradedGroup);
            this.addToHistory(anUpgradedGroup.getAuthor(), "update");
            return new Response(TextFormatting.getGreenText("\n\tObject has been updated!\n"));
        }

        return new Response(status);
    }

    public Response removeById(String anUsername, int anId) {

        String status = dbWorker.removeById(anId, anUsername);

        if (status == null) {
            StudyGroup studyGroup = collectionManager.getId(anId);
            collectionManager.remove(studyGroup);
            this.addToHistory(anUsername, "remove_by_id");
            return new Response(TextFormatting.getGreenText("\n\tObject has been removed!\n"));
        }

        return new Response(status);
    }

    public Response clear(String anUsername) {

        String status = dbWorker.clear(anUsername);

        if (status == null) {
            collectionManager.clear(anUsername);
            this.addToHistory(anUsername, "clear");
            return new Response(TextFormatting.getGreenText("\n\tSuccessful!\n"));
        }

        return new Response(status);
    }

    public String addIfMax(StudyGroup aStudyGroup) {

        if (collectionManager.getMax() != null && aStudyGroup.compareTo(collectionManager.getMax()) >= 0)
            return add(aStudyGroup);

        else return TextFormatting.getRedText("\n\tStudy group isn't max!\n");
    }

    public String addIfMin(StudyGroup aStudyGroup) {

        if (collectionManager.getMax() != null && aStudyGroup.compareTo(collectionManager.getMin()) <= 0)
            return add(aStudyGroup);

        else return TextFormatting.getRedText("\n\tStudy group isn't min!\n");
    }

    public Response history(String anUsername) {
        ArrayDeque<String> userCommands = previousCommands.get(anUsername);
        if (userCommands != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            userCommands.stream()
                    .map(command -> ")" + command + "\n")
                    .forEach(sb::append);
            return new Response(sb.toString());
        } else return new Response(TextFormatting.getRedText("\n\tNo commands executed!\n"));
    }

    public void addToHistory(String anUsername, String aCommand) {
        ArrayDeque<String> previousUserCommands = previousCommands.get(anUsername);
        if (previousUserCommands != null) {
            previousUserCommands.offerLast(aCommand);
            if (previousCommands.size() == 15) previousUserCommands.removeFirst();
        } else {
            previousCommands.put(anUsername, new ArrayDeque<>(14));
            previousCommands.get(anUsername).offerLast(aCommand);
        }
    }

    public Response minByStudentsCount(String anUsername) {

        this.addToHistory(anUsername, "min_by_students_count");

        if (collectionManager.getMinStudentsCount() != null)
            return new Response(collectionManager.getMinStudentsCount());
        else
            return new Response(TextFormatting.getRedText("\n\tThere are no study groups in the collection yet!\n"));
    }

    public Response countLessThanStudentsCount(Integer aCount, String anUsername) {

        this.addToHistory(anUsername, "count_less_than_students_count");

        if (collectionManager.getCollection().size() == 0)
            return new Response(TextFormatting.getRedText("\n\tCollection is empty!\n"));

        long count = collectionManager.getCollection().stream().filter(studyGroup ->
                studyGroup.getStudentsCount() < aCount).count();

        return new Response(TextFormatting.getGreenText("\n\tAmount of elements: " + count + "\n"));
    }

    public Response filterStartsWithName(String aStartName, String anUsername) {

        this.addToHistory(anUsername, "filter_starts_with_name");

        Set<StudyGroup> collection = collectionManager.getCollection();

        if (collection.size() == 0) return new Response(TextFormatting.getRedText("\n\tCollection is empty!\n"));

        Set<StudyGroup> groups = collection
                .stream()
                .filter(studyGroup -> studyGroup.getName().startsWith(aStartName))
                .collect(Collectors.toSet());

        if (groups.isEmpty()) return new Response(TextFormatting.getRedText("\n\tNo objects found!\n"));

        return new Response(groups);
    }

    public boolean registerUser(String username, String password) {
        return dbWorker.addUser(username, password);
    }

    public boolean loginUser(String anUsername, String aPassword) {
        return dbWorker.loginUser(anUsername, aPassword);
    }
}
