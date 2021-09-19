package commands;

import utility.Request;
import utility.Response;

/**
 * Interface for commands
 */
public interface CommandInterface {

    /**
     * Method for print command's description
     */
    String getDescription();

    /**
     * Method for execute command and return execution status
     */
    Response execute(Request aRequest);
}