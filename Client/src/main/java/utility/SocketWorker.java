package utility;

import Interfaces.ResponseHandlerInterface;
import Interfaces.SocketWorkerInterface;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Class to work with client's socket
 */
public class SocketWorker implements SocketWorkerInterface {

    private DatagramChannel datagramChannel;
    private final ResponseHandlerInterface responseHandler;

    public SocketWorker(SocketAddress aSocketAddress) {
        responseHandler = ResponseHandler.getInstance();
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.configureBlocking(false);
            datagramChannel.connect(aSocketAddress);
        } catch (IOException e) {
            Console.getInstance().print("Some problem's with network!");
        }
    }

    @Override
    public String sendRequest(byte[] serializedRequest) {

        try {
            ByteBuffer buf = ByteBuffer.wrap(serializedRequest);
            do {
                datagramChannel.write(buf);
            } while (buf.hasRemaining());
            return responseHandler.receive(receiveAnswer());
        } catch (IOException exception) {
            RequestHandler.getInstance().setSocketStatus(false);
            return TextFormatting.getRedText("\tCommand didn't send, try again!\n");
        }
    }

    private String receiveAnswer() {

        long timeStart = System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (true) {
            if ((System.currentTimeMillis() - timeStart) < 5000) {
                try {
                    datagramChannel.receive(buffer);
                    if (buffer.position() != 0) {
                        return ResponseHandler.getInstance().receive(buffer);
                    }
                } catch (IOException ignored) {
                }
            } else {
                RequestHandler.getInstance().setSocketStatus(false);
                return TextFormatting.getRedText("\n\tServer isn't available at the moment! " +
                        "Please, select another remote host!\n\n");
            }
        }
    }
}