package utility;

import java.io.Serializable;

/**
 * Class to serialize requests to server
 */
public class Request implements Serializable {

    private final Command command;
    private final Session session;

    public Request(Command aCommand, Session aSession) {
        command = aCommand;
        session = aSession;
    }

    public Command getCommand(){
        return command;
    }

    public Session getSession(){
        return session;
    }

    @Override
    public String toString() {
        return command.toString() + "from (" + session.toString() + ")";
    }
}