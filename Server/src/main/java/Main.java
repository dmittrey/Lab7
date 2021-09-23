import Database.DBConnector;
import Database.DBInitializer;
import Database.DBWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.*;

import java.io.IOException;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger("Server");

    public static void main(String[] args) {

        logger.info("Entering server!");

        try (Scanner scanner = new Scanner(System.in);
             DatagramSocket datagramSocket = getDatagramSocket(scanner)) {

            logger.info("Server listening port " + datagramSocket.getLocalPort() + "!");

            CollectionManager collectionManager = new CollectionManager();
            DBWorker dbWorker = connectToDB();//отладить

            FileWorker fileWorker = new FileWorker(collectionManager);
            if (!fileWorker.getFromXmlFormat()) return;

            Invoker invoker = new Invoker(collectionManager, dbWorker);
            AutoGenFieldsSetter fieldsSetter = new AutoGenFieldsSetter(collectionManager.getHighUsedId());
            Executor deliverManager = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()/3);

            Runtime.getRuntime().addShutdownHook(new Thread(new ExitSaver(fileWorker)));

            while (true) {
                byte[] buf = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                datagramSocket.receive(packet);
                RequestReceiver requestReceiver = new RequestReceiver(datagramSocket, packet, invoker, deliverManager);
                requestReceiver.start();
            }
        } catch (IOException e) {
            logger.info("Some problem's with network!");
        }
    }

    private static DBWorker connectToDB(){
        Connection db;
        try {
            db = DBConnector.connect();
        } catch (SQLException e) {
            System.out.println("Connection establishing problems");
            e.printStackTrace();
            return null;
        }

        DBInitializer dbInitializer = new DBInitializer(db);
        try {
            dbInitializer.initialize();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            DBWorker dbWorker = new DBWorker(db);
            return dbWorker;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Hashing algorithm  not found");
            e.printStackTrace();
            return null;
        }
    }

    private static int getPort(Scanner scanner) {

        String arg;
        Pattern remoteHostPortPattern = Pattern.compile("^\\s*\\b(\\d{1,5})\\b\\s*");

        do {
            System.out.print(TextFormatting.getGreenText("Please, enter remote host port(1-65535): "));
            arg = scanner.nextLine();
        } while (!remoteHostPortPattern.matcher(arg).find() || (Integer.parseInt(arg.trim()) >= 65536)
                || (Integer.parseInt(arg.trim()) <= 0));

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