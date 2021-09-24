package utility;

import Database.DBWorker;
import data.StudyGroup;

import java.util.Set;
import java.util.stream.Collectors;

public class Receiver {

    private final CollectionManager collectionManager;
    private final DBWorker dbWorker;

    public Receiver(CollectionManager aCollectionManager, DBWorker aDBWorker) {

        collectionManager = aCollectionManager;
        dbWorker = aDBWorker;
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

    public Response add(StudyGroup aStudyGroup) {

        Integer id = dbWorker.addStudyGroup(aStudyGroup);

        if (id != 0) {
            collectionManager.add(aStudyGroup.setId(id));
            return null;
        } else {
            return new Response(TextFormatting.getRedText("\tThis element probably duplicates " +
                    "existing one and can't be added\n"));
        }
    }

    public Response updateId(StudyGroup anUpgradedGroup, int anId) {

        String status = dbWorker.updateById(anUpgradedGroup, anId);

        if (status == null) {
            StudyGroup studyGroup = collectionManager.getId(anId);
            collectionManager.remove(studyGroup);
            anUpgradedGroup.setId(anId);
            collectionManager.add(anUpgradedGroup);
            return new Response(TextFormatting.getGreenText("\n\tObject has been updated!\n"));
        }

        return new Response(status);
    }

    public Response removeById(String anUsername, int anId) {

        String status = dbWorker.removeById(anId, anUsername);

        if (status == null) {
            StudyGroup studyGroup = collectionManager.getId(anId);
            collectionManager.remove(studyGroup);
            return new Response(TextFormatting.getGreenText("\n\tObject has been removed!\n"));
        }

        return new Response(status);
    }

    public Response clear(String anUsername) {

        String status = dbWorker.clear(anUsername);

        if (status == null) {
            collectionManager.clear(anUsername);
            return new Response(TextFormatting.getGreenText("\n\tSuccessful!\n"));
        }

        return new Response(status);
    }

    public Response addIfMax(StudyGroup aStudyGroup) {

        if (collectionManager.getMax() != null && aStudyGroup.compareTo(collectionManager.getMax()) >= 0)
            return add(aStudyGroup);

        else return new Response(TextFormatting.getRedText("\n\tStudy group isn't max!\n"));
    }

    public Response addIfMin(StudyGroup aStudyGroup) {

        if (collectionManager.getMax() != null && aStudyGroup.compareTo(collectionManager.getMin()) <= 0)
            return add(aStudyGroup);

        else return new Response(TextFormatting.getRedText("\n\tStudy group isn't min!\n"));
    }

    public Response minByStudentsCount() {

        if (collectionManager.getMinStudentsCount() != null)
            return new Response(collectionManager.getMinStudentsCount());
        else
            return new Response(TextFormatting.getRedText("\n\tThere are no study groups in the collection yet!\n"));
    }

    public Response countLessThanStudentsCount(Integer aCount) {

        if (collectionManager.getCollection().size() == 0)
            return new Response(TextFormatting.getRedText("\n\tCollection is empty!\n"));

        long count = collectionManager.getCollection().stream().filter(studyGroup ->
                studyGroup.getStudentsCount() < aCount).count();

        return new Response(TextFormatting.getGreenText("\n\tAmount of elements: " + count + "\n"));
    }

    public Response filterStartsWithName(String aStartName) {

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
        System.out.println(2);
        return dbWorker.addUser(username, password);
    }

    public boolean loginUser(String anUsername, String aPassword) {
        return dbWorker.loginUser(anUsername, aPassword);
    }
}
