package utility;

import data.StudyGroup;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Class to serialize answer's from server
 */
public class Response implements Serializable {

    private Map<String, String> information = null;
    private Set<StudyGroup> setOfStudyGroups = null;
    private final TypeOfAnswer status;
    private StudyGroup studyGroup = null;
    private Long count = null;

    public Response(Map<String, String> anInformation, TypeOfAnswer aStatus) {
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

    public Map<String, String> getInformation() {
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

    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return information + "\n" +
                setOfStudyGroups + "\n" +
                status + "\n" +
                studyGroup + "\n" +
                count + "\n";
    }
}