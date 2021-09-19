package utility;

import data.StudyGroup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

/**
 * Class to completing requests
 */
public class RequestHandler {

    private static RequestHandler instance;
    private InetSocketAddress socketAddress;
    private boolean socketStatus;


    public static RequestHandler getInstance() {
        if (instance == null) instance = new RequestHandler();
        return instance;
    }

    private RequestHandler() {
    }

    public String send(Request aRequest) {
        try {
            SocketWorker socketWorker = new SocketWorker(socketAddress);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream outObj = new ObjectOutputStream(byteArrayOutputStream);
            outObj.writeObject(aRequest);
            return socketWorker.sendRequest(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            return TextFormatting.getRedText("\tRequest can't be serialized, call programmer!\n");
        }
    }

    public String send(Request aRequest, StudyGroup studyGroup) {

        if (studyGroup != null) {
            aRequest.addStudyGroup(studyGroup);
            return send(aRequest);
        } else return TextFormatting.getRedText("\tStudy group isn't exist, try again!\n");
    }

    public void setRemoteHostSocketAddress(InetSocketAddress aSocketAddress) {
        socketAddress = aSocketAddress;
    }

    public String getInformation() {

        return TextFormatting.getGreenText("\n\t\t\t\t\u0020----------------") +
                TextFormatting.getGreenText("\n\t\t\t\tConnection status:") +
                TextFormatting.getGreenText("\n\t\t\t\t------------------\n") +
                "Remote host address:\t" + TextFormatting.getGreenText(String.valueOf(socketAddress.getAddress())) + "\n\n" +
                "Remote host port:\t" + TextFormatting.getGreenText(String.valueOf(socketAddress.getPort())) + "\n\n";
    }

    public void setSocketStatus(boolean aSocketStatus) {
        socketStatus = aSocketStatus;
    }

    public boolean getSocketStatus() {
        return socketStatus;
    }
}