package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class to works with user's input
 */
public class ConsoleWorker {

    private final Scanner scanner;
    final Logger logger = Logger.getLogger(ConsoleWorker.class.getCanonicalName());

    public ConsoleWorker(Scanner aScanner) {
        scanner = aScanner;
    }

    public void print(Object anObj) {
        System.out.print(anObj);
    }

    public String read() {

        try {
            return scanner.nextLine().trim();
        } catch (NoSuchElementException exception) {
            logger.info("Server was turned off!");
            System.exit(0);
            return null;
        }
    }
}