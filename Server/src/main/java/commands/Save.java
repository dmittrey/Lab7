package commands;

import utility.FileWorker;
import utility.Request;
import utility.Response;

import java.util.logging.Logger;

/**
 * Class to save collection in xml file
 */
public class Save extends CommandAbstract {

    private final FileWorker fileWorker;
    final Logger logger = Logger.getLogger(FileWorker.class.getCanonicalName());

    public Save(FileWorker aFileWorker) {
        super("save", "save the collection to file");
        fileWorker = aFileWorker;
    }

    @Override
    public Response execute(Request aCommand) {

        logger.info(fileWorker.saveToXml().toString());
        return new Response("");
    }
}