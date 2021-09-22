package utility;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for storage available commands
 */
public class AvailableCommands {

    public final Set<String> noArgumentCommands = new HashSet<>();
    public final Set<String> numArgumentCommands = new HashSet<>();
    public final Set<String> stringArgumentCommands = new HashSet<>();
    public final Set<String> objectArgumentCommands = new HashSet<>();
    public final String scriptArgumentCommand;
    public final String objAndNumArgumentCommand;

    public AvailableCommands() {

        Collections.addAll(noArgumentCommands,
                "help",
                "info",
                "show",
                "clear",
                "history",
                "min_by_students_count");

        Collections.addAll(numArgumentCommands,
                "remove_by_id",
                "count_less_than_students_count");

        Collections.addAll(stringArgumentCommands,
                "filter_starts_with_name");

        Collections.addAll(objectArgumentCommands,
                "add",
                "add_if_max",
                "add_if_min");

        scriptArgumentCommand = "execute_script";

        objAndNumArgumentCommand = "update";
    }
}