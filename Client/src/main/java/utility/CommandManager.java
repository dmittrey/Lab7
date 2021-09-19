package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The class that serves the command
 */
public class CommandManager {

    private final CommandReader commandReader;
    private final Validator validator;
    private final RequestHandler requestHandler;
    private final Console console;
    private final Set<String> usedScripts;
    private final StudyGroupFactory studyGroupFactory;


    public CommandManager(CommandReader aCommandReader) {

        commandReader = aCommandReader;
        validator = Validator.getInstance();
        requestHandler = RequestHandler.getInstance();
        console = Console.getInstance();
        usedScripts = new HashSet<>();
        studyGroupFactory = new StudyGroupFactory();
    }


    public void transferCommand(Request aCommand) {

        if (validator.notObjectArgumentCommands(aCommand))
            console.print(RequestHandler.getInstance().send(aCommand) + "\n");

        else if (validator.objectArgumentCommands(aCommand)) {
            console.print(RequestHandler.getInstance().send(aCommand, studyGroupFactory.createStudyGroup()) + "\n");
        } else if (validator.validateScriptArgumentCommand(aCommand)) {
            executeScript(aCommand.getArg());
        } else {
            console.print(TextFormatting.getRedText("\tCommand entered incorrectly!\n"));
        }
    }

    private void executeScript(String scriptName) {

        if (usedScripts.add(scriptName)) {

            try {

                if (usedScripts.size() == 1) console.setExeStatus(true);

                ScriptReader scriptReader = new ScriptReader(this, commandReader, new File(scriptName));
                try {
                    scriptReader.read();

                    System.out.println(TextFormatting.getGreenText("\nThe script " + scriptName
                            + " was processed successfully!\n"));
                } catch (IOException exception) {

                    usedScripts.remove(scriptName);

                    if (usedScripts.size() == 0) console.setExeStatus(false);

                    if (!new File(scriptName).exists()) console.print(
                            TextFormatting.getRedText("\n\tThe script does not exist!\n"));
                    else if (!new File(scriptName).canRead()) console.print(
                            TextFormatting.getRedText("\n\tThe system does not have permission to read the file!\n"));
                    else console.print("\n\tWe have some problem's with script!\n");
                }

                usedScripts.remove(scriptName);

                if (usedScripts.size() == 0) console.setExeStatus(false);

            } catch (FileNotFoundException e) {
                console.print("\n\tScript not found!\n");
            }
        } else console.print(TextFormatting.getRedText("\nRecursion has been detected! Script " + scriptName +
                " will not be ran!\n"));
    }
}