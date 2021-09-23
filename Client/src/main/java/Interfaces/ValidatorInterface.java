package Interfaces;

import utility.Command;

public interface ValidatorInterface {

    /**
     * Validate Commands without argument
     */
    boolean notObjectArgumentCommands(Command aCommand);

    /**
     * Validate Commands with object argument
     */
    boolean objectArgumentCommands(Command aCommand);

    /**
     * Validate Commands with script path argument
     */
    boolean validateScriptArgumentCommand(Command aCommand);
}
