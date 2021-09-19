package utility;

import data.StudyGroup;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * Class to work with collection
 */
@XmlRootElement(name = "studyGroups")
public class CollectionManager {

    private HashSet<StudyGroup> studyGroups;
    private final HashSet<Integer> usedId;
    private int highUsedId;
    private final String initTime;

    public CollectionManager() {
        studyGroups = new HashSet<>();
        initTime = new Date().toString();
        usedId = new HashSet<>();
        highUsedId = 0;
    }

    public String getInfo() {

        return "Type of collection" + "  \t\t\t:\t" + "HashSet" + "\n" +
                "Type of collection items" + "\t\t:\t" + "Study groups" + "\n" +
                "Priority" + "\t\t\t\t:\t" + "Student's count, Average mark, Creation date" + "\n" +
                "Initialization date" + "\t\t\t:\t" + initTime + "\n" +
                "Number of items in te collection" + "\t:\t" + studyGroups.size() + "\n\n";
    }

    public HashSet<StudyGroup> getCollection() {
        return studyGroups;
    }

    @XmlElement(name = "studyGroup")
    public void setCollection(HashSet<StudyGroup> aStudyGroups) {
        studyGroups = aStudyGroups;
    }

    public void add(StudyGroup studyGroup) {

        Integer newGroupId = studyGroup.getId();

        if (!usedId.add(newGroupId)) studyGroups.remove(this.getId(newGroupId));

        if (studyGroups.add(studyGroup)) {
            usedId.add(studyGroup.getId());
            if (highUsedId < studyGroup.getId()) highUsedId = studyGroup.getId();
        } else usedId.remove(studyGroup.getId());
    }

    public boolean remove(StudyGroup studyGroup) {
        return studyGroups.remove(studyGroup);
    }

    public StudyGroup getMinStudentsCount() {

        return studyGroups.stream().min(Comparator.comparing(StudyGroup::getStudentsCount)).orElse(null);
    }

    public StudyGroup getMin() {

        return studyGroups.stream().min(StudyGroup::compareTo).orElse(null);
    }

    public StudyGroup getMax() {

        return studyGroups.stream().max(StudyGroup::compareTo).orElse(null);
    }

    public StudyGroup getId(Integer key) {

        return studyGroups.stream().filter(sg -> sg.getId().equals(key)).findAny().orElse(null);
    }

    public void clear() {
        studyGroups.clear();
    }

    public int getHighUsedId() {
        return highUsedId;
    }
}