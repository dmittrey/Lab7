package utility;

import database.DBWorker;
import data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Class that receive requests from commands and execute their
 * <p>
 * see Pattern Command
 */
public class Receiver {

    private final CollectionManager collectionManager;
    private final DBWorker dbWorker;
    private final Map<String, ArrayBlockingQueue<String>> previousCommands;
    public static final Logger logger = LoggerFactory.getLogger("Receiver");

    public Receiver(CollectionManager aCollectionManager, DBWorker aDBWorker) {
        collectionManager = aCollectionManager;
        dbWorker = aDBWorker;
        previousCommands = new ConcurrentHashMap<>();
        getCollection();
    }

    private void getCollection() {
        try {
            ResultSet data = dbWorker.getCollection();
            while (data.next()) {
                collectionManager.add(new StudyGroup(
                        data.getInt(1),
                        data.getString(2),
                        new Coordinates(data.getInt(3), data.getDouble(4)),
                        data.getDate(5),
                        data.getInt(6),
                        data.getString(7) != null ? Double.valueOf(data.getString(7)) : null,
                        data.getString(8) != null ?
                                FormOfEducation.valueOf(data.getString(8)) : null,
                        Semester.valueOf(data.getString(9)),
                        new Person(data.getString(10),
                                data.getLong(11),
                                Color.valueOf(data.getString(12))),
                        data.getString(13)));
            }
        } catch (SQLException ignored) {

        }
    }

    public Map<String, String> info() {
        return collectionManager.getInfo();
    }

    public Set<StudyGroup> show() {
        if (collectionManager.getCollection().size() == 0) return null;
        else return collectionManager.getCollection();
    }

    public TypeOfAnswer add(StudyGroup aStudyGroup) {
        Integer id = dbWorker.addStudyGroup(aStudyGroup);

        if (id != 0) {
            collectionManager.add(aStudyGroup.setId(id));
            this.addToHistory(aStudyGroup.getAuthor(), "add");
            return TypeOfAnswer.SUCCESSFUL;
        } else {
            return TypeOfAnswer.DUPLICATESDETECTED;
        }
    }

    public TypeOfAnswer updateId(StudyGroup anUpgradedGroup, int anId) {
        TypeOfAnswer status = dbWorker.updateById(anUpgradedGroup, anId);

        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            StudyGroup studyGroup = collectionManager.getId(anId);
            collectionManager.remove(studyGroup);
            anUpgradedGroup.setId(anId);
            collectionManager.add(anUpgradedGroup);
            this.addToHistory(anUpgradedGroup.getAuthor(), "update");
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public TypeOfAnswer removeById(String anUsername, int anId) {
        TypeOfAnswer status = dbWorker.removeById(anId, anUsername);

        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            StudyGroup studyGroup = collectionManager.getId(anId);
            collectionManager.remove(studyGroup);
            this.addToHistory(anUsername, "remove_by_id");
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public TypeOfAnswer clear(String anUsername) {
        TypeOfAnswer status = dbWorker.clear(anUsername);

        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
            collectionManager.clear(anUsername);
            this.addToHistory(anUsername, "clear");
            return TypeOfAnswer.SUCCESSFUL;
        } else return status;
    }

    public TypeOfAnswer addIfMax(StudyGroup aStudyGroup) {
        if (collectionManager.getMax() != null && aStudyGroup.compareTo(collectionManager.getMax()) >= 0)
            return add(aStudyGroup);
        else return TypeOfAnswer.ISNTMAX;
    }

    public TypeOfAnswer addIfMin(StudyGroup aStudyGroup) {
        if (collectionManager.getMax() != null && aStudyGroup.compareTo(collectionManager.getMin()) <= 0)
            return add(aStudyGroup);
        else return TypeOfAnswer.ISNTMIN;
    }

    public ArrayBlockingQueue<String> history(String anUsername) {
        return previousCommands.get(anUsername);
    }

    public void addToHistory(String anUsername, String aCommand) {
        ArrayBlockingQueue<String> previousUserCommands = previousCommands.get(anUsername);
        if (previousCommands.putIfAbsent(anUsername, new ArrayBlockingQueue<>(14)) != null) {
            if (previousUserCommands.size() == 14) previousUserCommands.poll();
            previousUserCommands.offer(aCommand);
        }
    }

    public StudyGroup minByStudentsCount(String anUsername) {
        this.addToHistory(anUsername, "min_by_students_count");
        return collectionManager.getMinStudentsCount();
    }

    public Long countLessThanStudentsCount(Integer aCount, String anUsername) {
        this.addToHistory(anUsername, "count_less_than_students_count");

        if (collectionManager.getCollection().size() == 0) return null;
        return collectionManager.getCollection()
                .stream()
                .filter(studyGroup -> studyGroup.getStudentsCount() < aCount)
                .count();
    }

    public Set<StudyGroup> filterStartsWithName(String aStartName, String anUsername) {
        this.addToHistory(anUsername, "filter_starts_with_name");

        Set<StudyGroup> collection = collectionManager.getCollection();
        if (collection.size() == 0) return null;

        return collection.stream()
                .filter(studyGroup -> studyGroup.getName().startsWith(aStartName))
                .collect(Collectors.toSet());
    }

    public boolean registerUser(String username, String password) {
        return dbWorker.addUser(username, password);
    }

    public boolean loginUser(String anUsername, String aPassword) {
        return dbWorker.loginUser(anUsername, aPassword);
    }
}