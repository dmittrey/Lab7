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
    private TypeOfAnswer responseStatus;
    private StudyGroup studyGroup;
    private Set<StudyGroup> collection;
    private boolean status;
    private long count;

    public Response(String anInformation) {
        information = anInformation;
    }

    public Response(TypeOfAnswer aTypeOfResponse) {
        responseStatus = aTypeOfResponse;
    }

    public Response(StudyGroup aStudyGroup) {
        studyGroup = aStudyGroup;
    }

    public Response(Set<StudyGroup> aCollection) {
        collection = aCollection;
    }

    public Response(boolean aBoolean) {
        status = aBoolean;
    }

    public Response(long aCount) {
        count = aCount;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public Set<StudyGroup> getCollection() {
        return collection;
    }

    public boolean getStatus() {
        return status;
    }

    public TypeOfAnswer getResponseStatus(){
        return responseStatus;
    }

    public String getInformation(){
        return information;
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