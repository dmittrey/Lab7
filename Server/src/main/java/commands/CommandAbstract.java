package commands;

import utility.Request;
import utility.Response;

/**
 * Abstract class for commands
 */
public abstract class CommandAbstract implements CommandInterface {

    private final String name;
    private final String description;

    /**
     * Class constructor
     *
     * @param aName        - Command's name
     * @param aDescription - Command's description
     */
    public CommandAbstract(String aName, String aDescription) {
        name = aName;
        description = aDescription;
    }

    /**
     * Method for print information about command
     *
     * @return String with command's name and description
     */
    @Override
    public String getDescription() {

        switch (name) {
            case "help":
            case "info":
            case "show":
            case "clear":
            case "save":
            case "exit":
            case "history":
            case "min_by_students_count":
                return name + " : " + description;
            case "update":
                return name + " id {element} : " + description;
            case "add":
            case "add_if_max":
            case "add_if_min":
                return name + " {element} : " + description;
            case "remove_by_id":
                return name + " id : " + description;
            case "execute_script":
                return name + " file_name : " + description;
            case "count_less_than_students_count":
                return name + " studentsCount : " + description;
            default:
                return name + " name : " + description;
        }
    }

    /**
     * Universal method to executing commands
     */
    @Override
    public abstract Response execute(Request aRequest);
}