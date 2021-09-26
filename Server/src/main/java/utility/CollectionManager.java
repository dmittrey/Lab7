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

    public Map<String, List<String>> getInfo() {
        Map<String, List<String>> typesOfInfo = new HashMap<>();
        typesOfInfo.put("Type of collection", Collections.singletonList("HashSet"));
        typesOfInfo.put("Type of collection items", Collections.singletonList("Study groups"));
        typesOfInfo.put("Priority", Collections.singletonList("Student's count, Average mark, Creation date"));
        typesOfInfo.put("Initialization date", Collections.singletonList(initTime));
        typesOfInfo.put("Number of items in te collection", Collections.singletonList(String.valueOf(studyGroups.size())));
        return typesOfInfo;
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