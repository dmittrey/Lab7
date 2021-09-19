package utility;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.logging.Logger;

public class Deliver {

    private final Invoker invoker;
    private final AutoGenFieldsSetter fieldsSetter;
    final Logger logger = Logger.getLogger(Deliver.class.getCanonicalName());

    public Deliver(Invoker anInvoker, AutoGenFieldsSetter aFieldsSetter) {
        invoker = anInvoker;
        fieldsSetter = aFieldsSetter;
    }

    public void answer(Request aCommand, DatagramSocket aDatagramSocket, SocketAddress aSocketAddress) throws IOException {
        Response anAnswer = invoker.execute(fieldsSetter.setFields(aCommand));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outObj = new ObjectOutputStream(byteArrayOutputStream);
        outObj.writeObject(anAnswer);

        DatagramPacket packet = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(),
                aSocketAddress);
        aDatagramSocket.send(packet);
        logger.info("Server send an answer!");
    }
}
