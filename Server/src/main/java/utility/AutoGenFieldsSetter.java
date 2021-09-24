package utility;

import data.StudyGroup;

import java.util.Date;

/**
 * Set autogenerate fields to group
 */
public class AutoGenFieldsSetter {

    public static Request setFields(Request aRequest) {
        StudyGroup studyGroup = aRequest.getCommand().getStudyGroup();
        String author = aRequest.getSession().getName();

        if (studyGroup != null) {
            studyGroup.setCreationDate(new Date());
            studyGroup.setAuthor(author);
        }
        return aRequest;
    }
}