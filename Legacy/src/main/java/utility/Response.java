package utility;

import data.StudyGroup;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class to serialize answer's from server
 */
public class Response implements Serializable {

    private Map<String, List<String>> information = null;
    private Set<StudyGroup> setOfStudyGroups = null;
    private final TypeOfAnswer status;
    private StudyGroup studyGroup = null;
    private Long count = null;

    public Response(Map<String, List<String>> anInformation, TypeOfAnswer aStatus) {
        information = anInformation;
        status = aStatus;
    }

    public Response(Set<StudyGroup> aSetOfStudyGroups, TypeOfAnswer aStatus) {
        setOfStudyGroups = aSetOfStudyGroups;
        status = aStatus;
    }

    public Response(StudyGroup aStudyGroup, TypeOfAnswer aStatus) {
        studyGroup = aStudyGroup;
        status = aStatus;
    }

    public Response(Long aCount, TypeOfAnswer aStatus) {
        count = aCount;
        status = aStatus;
    }

    public Response(TypeOfAnswer aStatus) {
        status = aStatus;
    }

    public Map<String, List<String>> getInformation() {
        return information;
    }

    public Set<StudyGroup> getSetOfStudyGroups() {
        return setOfStudyGroups;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public TypeOfAnswer getStatus() {
        return status;
    }
}