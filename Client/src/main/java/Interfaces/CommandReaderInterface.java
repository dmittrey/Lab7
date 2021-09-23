package Interfaces;

import utility.Command;

public interface CommandReaderInterface {

    /**
     * Turn on commands receiving from console
     */
    boolean enable();

    /**
     * Read one command from console
     */
    Command readCommand(String anInputString);
}
