package utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * Class that receiving requests
 */
public class RequestReceiver implements Runnable {

    private final DatagramPacket datagramPacket;
    private final DatagramSocket datagramSocket;
    private final RequestHandler requestHandler;
    public static final Logger logger = LoggerFactory.getLogger("Server");

    public RequestReceiver(DatagramSocket aDatagramSocket, DatagramPacket aDatagramPacket,
                           Invoker anInvoker, Executor aDeliverManager, ForkJoinPool aForkJoinPool) throws SocketException {

        datagramSocket = aDatagramSocket;
        requestHandler = new RequestHandler(anInvoker, aDeliverManager, aForkJoinPool);
        datagramPacket = aDatagramPacket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData()));
            Request request = AutoGenFieldsSetter.setFields((Request) inObj.readObject());
            logger.info("Server received command: {}", request);

            requestHandler.process(request, datagramSocket, datagramPacket.getSocketAddress());
        } catch (ClassNotFoundException e) {
            logger.info("Client sent outdated request!");
        } catch (IOException e) {
            logger.info("Some problem's with getting request!");
        }
    }
}