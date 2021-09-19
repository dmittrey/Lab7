package utility;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

public class Receiver {

    private final Deliver deliver;
    private final DatagramSocket datagramSocket;

    final Logger logger = Logger.getLogger(Receiver.class.getCanonicalName());


    public Receiver(Deliver aDeliver, DatagramSocket aDatagramSocket) {

        datagramSocket = aDatagramSocket;
        deliver = aDeliver;
    }

    public void start() {

        while (true) {

            try {
                byte[] buf = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                datagramSocket.receive(packet);

                ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                Request request = (Request) inObj.readObject();
                logger.info("Server received command: " + request.toString());

                deliver.answer(request, datagramSocket, packet.getSocketAddress());
            } catch (IOException e) {
                logger.info("Some problem's with network!");
            } catch (ClassNotFoundException e) {
                logger.info("Client sent outdated request!");
            }
        }
    }
}