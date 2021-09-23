package Interfaces;

import java.io.IOException;

public interface ScriptReaderInterface {

    /**
     * Starts to reading and execute each command in script
     */
    void read() throws IOException;
}
