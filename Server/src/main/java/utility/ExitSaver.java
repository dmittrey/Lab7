package utility;

import java.util.logging.Logger;

public class ExitSaver implements Runnable {

    private final FileWorker fileWorker;
    final Logger logger = Logger.getLogger(ExitSaver.class.getCanonicalName());

    public ExitSaver(FileWorker aFileWorker) {
        fileWorker = aFileWorker;
    }

    @Override
    public void run() {
        logger.info("Client shutdown server!");
        fileWorker.saveToXml();
    }
}
