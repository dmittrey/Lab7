package utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.Executor;

public class RequestReceiver extends Thread {

    private final DatagramPacket datagramPacket;
    private final DatagramSocket datagramSocket;
    private final RequestHandler requestHandler;
    public static final Logger logger = LoggerFactory.getLogger("Server");

    public RequestReceiver(DatagramSocket aDatagramSocket, DatagramPacket aDatagramPacket, Invoker anInvoker,
                           AutoGenFieldsSetter aFieldsSetter, Executor aDeliverManager) {

        datagramSocket = aDatagramSocket;
        requestHandler = new RequestHandler(anInvoker, aFieldsSetter, aDeliverManager);
        datagramPacket = aDatagramPacket;
    }

    @Override
    public void run() {

        try {
            ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData()));
            Request request = (Request) inObj.readObject();
            logger.info("Server received command: " + request.toString()
                    + " from " + datagramPacket.getSocketAddress());

            requestHandler.process(request, datagramSocket, datagramPacket.getSocketAddress());

        } catch (ClassNotFoundException e) {
            logger.info("Client sent outdated request!");
        } catch (IOException e) {
            logger.info("Some problem's with getting request!");
        }
    }
}