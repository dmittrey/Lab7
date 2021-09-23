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

public class RequestReceiver extends Thread {

    private final DatagramPacket datagramPacket;
    private final DatagramSocket datagramSocket;
    private final RequestHandler requestHandler;
    public static final Logger logger = LoggerFactory.getLogger("Server");

    public RequestReceiver(DatagramSocket aDatagramSocket, DatagramPacket aDatagramPacket,
                           Invoker anInvoker, Executor aDeliverManager) throws SocketException {

        datagramSocket = aDatagramSocket;
        // Сделал для того, чтобы не создавать под каждый новый запрос новый тред и тем самым корректно
        // выводить команды в history для каждого клиента
        datagramSocket.connect(aDatagramPacket.getSocketAddress());
        requestHandler = new RequestHandler(anInvoker, aDeliverManager);
        datagramPacket = aDatagramPacket;
    }

    @Override
    public void run() {

        try {
            while (true) {
                ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData()));
                Request request = AutoGenFieldsSetter.setFields((Request) inObj.readObject());
                logger.info("Server received command: " + request.toString()
                        + " from " + datagramPacket.getSocketAddress());

                requestHandler.process(request, datagramSocket, datagramPacket.getSocketAddress());

                byte[] buf = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                datagramSocket.receive(packet);
            }
        } catch (ClassNotFoundException e) {
            logger.info("Client sent outdated request!");
        } catch (IOException e) {
            logger.info("Some problem's with getting request!");
        } finally {
            datagramSocket.close();
        }
    }
}