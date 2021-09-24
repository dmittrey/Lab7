package utility;

import data.StudyGroup;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to work with collection
 */
public class CollectionManager {

    private Set<StudyGroup> studyGroups;
    private final String initTime;

    public CollectionManager() {
        studyGroups = Collections.newSetFromMap(new ConcurrentHashMap<>());
        initTime = new Date().toString();
    }

    public String getInfo() {
        return "Type of collection" + "  \t\t\t:\t" + "HashSet" + "\n" +
                "Type of collection items" + "\t\t:\t" + "Study groups" + "\n" +
                "Priority" + "\t\t\t\t:\t" + "Student's count, Average mark, Creation date" + "\n" +
                "Initialization date" + "\t\t\t:\t" + initTime + "\n" +
                "Number of items in te collection" + "\t:\t" + studyGroups.size() + "\n\n";
    }

    public Set<StudyGroup> getCollection() {
        return studyGroups;
    }

    public void setCollection(HashSet<StudyGroup> aStudyGroups) {
        studyGroups = aStudyGroups;
    }

    public void add(StudyGroup studyGroup) {
        studyGroups.add(studyGroup);
    }

    public boolean remove(StudyGroup studyGroup) {
        return studyGroups.remove(studyGroup);
    }

    public StudyGroup getMinStudentsCount() {
        return studyGroups
                .stream()
                .min(Comparator.comparing(StudyGroup::getStudentsCount))
                .orElse(null);
    }

    public StudyGroup getMin() {
        return studyGroups
                .stream()
                .min(StudyGroup::compareTo)
                .orElse(null);
    }

    public StudyGroup getMax() {
        return studyGroups
                .stream()
                .max(StudyGroup::compareTo)
                .orElse(null);
    }

    public StudyGroup getId(Integer key) {
        return studyGroups
                .stream()
                .filter(sg -> sg.getId().equals(key))
                .findAny()
                .orElse(null);
    }

    public void clear(String username) {
        studyGroups
                .stream()
                .filter(sg -> sg.getAuthor().equals(username))
                .forEach(sg -> studyGroups.remove(sg));
    }
}