package Interfaces;

import utility.Command;

public interface CommandManagerInterface {

    /**
     * Validate and complete aCommand
     */
    void transferCommand(Command aCommand);
}
