package commands;

import utility.Request;
import utility.Response;

/**
 * Abstract class for commands
 */
public abstract class CommandAbstract implements CommandInterface {

    private final String description;
    private final boolean isRequiredAuthorization;

    /**
     * Class constructor
     *
     * @param aDescription - Command's description
     */
    public CommandAbstract(String aDescription, boolean anIsRequiredAuthorization) {
        description = aDescription;
        isRequiredAuthorization = anIsRequiredAuthorization;
    }

    /**
     * Method for print information about command
     *
     * @return String with command's name and description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Method to determine authorization status
     *
     * @return booleans status
     */
    @Override
    public boolean getAuthorizationStatus() {
        return isRequiredAuthorization;
    }

    /**
     * Universal method to executing commands
     */
    @Override
    public abstract Response execute(Request aRequest);
}