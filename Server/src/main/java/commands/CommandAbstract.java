package commands;

import utility.Request;
import utility.Response;

/**
 * Abstract class for commands
 */
public abstract class CommandAbstract implements CommandInterface {

    private final String description;

    /**
     * Class constructor
     *
     * @param aDescription - Command's description
     */
    public CommandAbstract(String aDescription) {
        description = aDescription;
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
     * Universal method to executing commands
     */
    @Override
    public abstract Response execute(Request aRequest);
}