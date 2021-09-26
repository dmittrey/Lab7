package utility;

import Interfaces.ResponseHandlerInterface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

/**
 * Class to response processing
 */
public class ResponseHandler implements ResponseHandlerInterface {

    private static ResponseHandler instance;

    private ResponseHandler() {
    }

    public static ResponseHandler getInstance() {
        if (instance == null) instance = new ResponseHandler();
        return instance;
    }

    @Override
    public String receive(ByteBuffer buffer) {
        try {
            ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            Response response = (Response) inObj.readObject();
            return Animator.getInstance().animate(response);
        } catch (ClassNotFoundException e) {
            return TextFormatting.getRedText("\tServer version is unsupported!\n");
        } catch (InvalidClassException e) {
            return TextFormatting.getRedText("\tYour version is outdated!\n");
        } catch (IOException e) {
            return TextFormatting.getRedText("\tResponse is broken! Try again!\n");
        }
    }

    @Override
    public String receive(String errorInformation) {
        return errorInformation;
    }
}