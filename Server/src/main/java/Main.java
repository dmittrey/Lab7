import utility.*;

import java.net.*;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        final Logger logger = Logger.getLogger(Main.class.getCanonicalName());

        logger.info("Entering server!");

        try (Scanner scanner = new Scanner(System.in)) {

            DatagramSocket datagramSocket = getDatagramSocket(scanner);

            logger.info("Server listening port " + datagramSocket.getLocalPort() + "!");

            CollectionManager collectionManager = new CollectionManager();

            FileWorker fileWorker = new FileWorker(collectionManager);
            if (!fileWorker.getFromXmlFormat()) return;

            AutoGenFieldsSetter fieldsSetter = new AutoGenFieldsSetter(collectionManager.getHighUsedId());

            Invoker invoker = new Invoker(collectionManager, fileWorker);

            Deliver deliver = new Deliver(invoker, fieldsSetter);
            Receiver receiver = new Receiver(deliver, datagramSocket);

            Runtime.getRuntime().addShutdownHook(new Thread(new ExitSaver(fileWorker)));

            receiver.start();
        }
    }

    private static int getPort(Scanner scanner) {

        String arg;
        Pattern remoteHostPortPattern = Pattern.compile("^\\s*(\\d{1,5})\\s*");

        do {
            System.out.print(TextFormatting.getGreenText("Please, enter remote host port(1-65535): "));
            arg = scanner.nextLine();
        } while (!(remoteHostPortPattern.matcher(arg).find() && (Integer.parseInt(arg.trim()) < 65536)
                && (Integer.parseInt(arg.trim()) > 0)));

        return Integer.parseInt(arg.trim());
    }

    private static DatagramSocket getDatagramSocket(Scanner scanner) {

        while (true) {
            try {
                return new DatagramSocket(getPort(scanner));
            } catch (SocketException ignored) {
                System.out.println(TextFormatting.getRedText("Socket could not bind to the specified local port!"));
            }
        }
    }
}