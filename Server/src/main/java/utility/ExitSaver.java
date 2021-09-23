package utility;

import java.util.logging.Logger;

public class ExitSaver implements Runnable {

    final Logger logger = Logger.getLogger(ExitSaver.class.getCanonicalName());

    @Override
    public void run() {
        logger.info("Client shutdown server!");
    }
}
