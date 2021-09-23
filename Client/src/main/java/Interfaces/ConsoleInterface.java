package Interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public interface ConsoleInterface {

    /**
     * Set new scanner into console
     */
    void setScanner(Scanner aScanner);

    /**
     * Print a string representation of anObj
     */
    void print(Object anObj);

    /**
     * Print string representation if script isn't executing
     */
    void print(Object anObj, boolean printPermission);

    /**
     * Read new string from console
     *
     * @throws IOException if we touch ctrl+d
     */
    String read() throws IOException;

    /**
     * Return status of script executing
     */
    boolean getExeStatus();

    /**
     * Set second source of read if we execute script
     */
    void setBufferedReader(BufferedReader aBufferedReader);

    /**
     * Set script executing status
     */
    void setExeStatus(boolean anExeStatus);
}
