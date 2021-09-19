package utility;

import java.util.Date;

public class AutoGenFieldsSetter {

    private int lastUsedId;

    public AutoGenFieldsSetter(int aLastUsedId) {
        lastUsedId = aLastUsedId;
    }

    public Request setFields(Request aCommand) {

        if (aCommand.getStudyGroup() != null) {
            aCommand.getStudyGroup().setId(++lastUsedId);
            aCommand.getStudyGroup().setCreationDate(new Date());
        }

        return aCommand;
    }
}