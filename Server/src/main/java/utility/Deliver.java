package utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

/**
 * Class to deliver response to client
 */
public class Deliver implements Runnable {

    private final DatagramSocket datagramSocket;
    private final Response answer;
    private final SocketAddress socketAddress;
    public static final Logger logger = LoggerFactory.getLogger("Deliver");

    public Deliver(DatagramSocket aDatagramSocket, Response anAnswer, SocketAddress aSocketAddress) {
        datagramSocket = aDatagramSocket;
        answer = anAnswer;
        socketAddress = aSocketAddress;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outObj = new ObjectOutputStream(byteArrayOutputStream);
            outObj.writeObject(answer);

            DatagramPacket packet = new DatagramPacket(byteArrayOutputStream.toByteArray(),
                    byteArrayOutputStream.size(), socketAddress);

            datagramSocket.send(packet);
            logger.info("Server send an answer!");
        } catch (IOException e) {
            logger.info("Some troubles with sending answer!");
        }
    }
}
