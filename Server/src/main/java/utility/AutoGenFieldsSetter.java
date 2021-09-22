package utility;

import data.StudyGroup;

import java.util.Date;

public class AutoGenFieldsSetter {

    private int lastUsedId;

    public AutoGenFieldsSetter(int aLastUsedId) {
        lastUsedId = aLastUsedId;
    }

    public Request setFields(Request aCommand) {

        StudyGroup studyGroup = aCommand.getCommand().getStudyGroup();

        if (studyGroup != null) {
            studyGroup.setId(++lastUsedId);
            studyGroup.setCreationDate(new Date());
        }

        return aCommand;
    }
}