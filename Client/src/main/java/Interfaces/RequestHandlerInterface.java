package Interfaces;

import data.StudyGroup;
import utility.Command;
import utility.Session;

import java.net.InetSocketAddress;

public interface RequestHandlerInterface {

    /**
     * Complete Command and Session into Request
     */
    String send(Command aCommand);

    /**
     * Complete Command with new Object and Session into Request
     */
    String send(Command aCommand, StudyGroup studyGroup);

    /**
     * Set destination of produced requests
     */
    void setRemoteHostSocketAddress(InetSocketAddress aSocketAddress);

    /**
     * Return information about RequestHandler
     */
    String getInformation();

    /**
     * Set Socket alive status(If Server is shut down)
     */
    void setSocketStatus(boolean aSocketStatus);

    /**
     * Return socket alive status
     */
    boolean getSocketStatus();

    void setSession(Session aSession);
}
