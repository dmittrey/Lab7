package Interfaces;

import utility.Command;

public interface SocketWorkerInterface {

    /**
     * Send Request to the server
     */
    String sendRequest(byte[] serializedRequest);
}
