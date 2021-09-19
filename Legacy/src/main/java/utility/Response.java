package utility;

import data.StudyGroup;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

/**
 * Class to serialize answer's from server
 */
public class Response implements Serializable {

    private String information;
    private StudyGroup studyGroup;
    private Set<StudyGroup> collection;

    public Response(String anInformation) {
        information = anInformation;
    }

    public Response(StudyGroup aStudyGroup) {
        studyGroup = aStudyGroup;
    }

    public Response(Set<StudyGroup> aCollection) {
        collection = aCollection;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public Set<StudyGroup> getCollection() {
        return collection;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (information != null)
            sb.append(information);

        if (studyGroup != null)
            sb.append(studyGroup).append("\n");

        if (collection != null)
            collection.stream().sorted(Comparator.comparing(StudyGroup::getCoordinates)).
                    forEach(sg -> sb.append(sg).append("\n"));

        return sb.toString();
    }
}