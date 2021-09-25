package Interfaces;

import utility.Command;

import java.nio.ByteBuffer;

public interface ResponseHandlerInterface {

    /**
     * Return String representation of Server answer in buffer
     */
    String receive(ByteBuffer buffer);

    /**
     * Return reformatted errorInformation
     */
    String receive(String errorInformation);
}
