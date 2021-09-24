package Interfaces;

import utility.Session;

import java.io.IOException;

public interface SessionWorkerInterface {

    /**
     * Get Username and Password from console
     */
    Session getSession() throws IOException;
}
