import database.DBConnector;
import database.DBInitializer;
import database.DBWorker;
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
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Pattern;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger("Server");

    public static void main(String[] args) {

        logger.info("Entering server!");

        try (Scanner scanner = new Scanner(System.in);
             DatagramSocket datagramSocket = getDatagramSocket(scanner)) {

            logger.info("Server listening port {} !", datagramSocket.getLocalPort());

            CollectionManager collectionManager = new CollectionManager();
            DBWorker dbWorker = connectToDB();
            if (dbWorker == null) return;
            Receiver receiver = new Receiver(collectionManager, dbWorker);

            Executor deliverManager = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 3);
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() / 3);

            Runtime.getRuntime().addShutdownHook(new Thread(new ExitSaver()));

            while (true) {
                byte[] buf = new byte[4096];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                datagramSocket.receive(packet);
                RequestReceiver requestReceiver = new RequestReceiver(datagramSocket, packet,
                        new Invoker(receiver), deliverManager, forkJoinPool);
                Thread t = new Thread(requestReceiver);
                t.start();
            }
        } catch (IOException e) {
            logger.info("Some problem's with network!");
        }
    }

    private static DBWorker connectToDB() {

        Connection db;
        try {
            db = new DBConnector().connect();
            if (db == null) return null;
        } catch (SQLException e) {
            logger.warn("Connection establishing problems");
            return null;
        }

//        DBConnector dbConnector = new DBConnector();
//        Connection db = dbConnector.connect();

        DBInitializer dbInitializer = new DBInitializer(db);
        try {
            dbInitializer.initialize();
        } catch (SQLException e) {
            logger.warn("Something wrong with db!");
            return null;
        }

        try {
            return new DBWorker(db);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("Hashing algorithm not found!");
            return null;
        }
    }

    private static int getPort(Scanner scanner) {
        String arg;
        Pattern remoteHostPortPattern = Pattern.compile("^\\s*\\b(\\d{1,5})\\b\\s*");

        do {
            System.out.print(TextFormatting.getGreenText("Please, enter local host port(1-65535): "));
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
                logger.warn("Socket could not bind to the specified local port!");
            }
        }
    }
}