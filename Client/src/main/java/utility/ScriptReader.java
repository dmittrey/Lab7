package utility;

import Interfaces.CommandManagerInterface;
import Interfaces.CommandReaderInterface;
import Interfaces.ScriptReaderInterface;

import java.io.*;

/**
 * Class for reading script
 */
public class ScriptReader implements ScriptReaderInterface {

    private final File file;
    private final CommandManagerInterface commandManager;
    private final CommandReaderInterface commandReader;

    public ScriptReader(CommandManager aCommandManager, CommandReaderInterface aCommandReader, File aFile)
            throws FileNotFoundException {

        file = aFile;
        commandManager = aCommandManager;
        commandReader = aCommandReader;
    }

    public void read() throws IOException {

        String nextLine;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            do {
                nextLine = bufferedReader.readLine();
                if (nextLine == null) return;

                Console.getInstance().setBufferedReader(bufferedReader);

                Command newCommand = commandReader.readCommand(nextLine + " ");
                commandManager.transferCommand(newCommand);
            } while (true);
        }
    }
}