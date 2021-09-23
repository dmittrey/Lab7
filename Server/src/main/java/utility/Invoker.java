package utility;

import Database.DBWorker;
import commands.*;

import java.util.*;

/**
 * Invoker class from pattern Command to logging commands from client
 */
public class Invoker {

    private final Map<String, CommandAbstract> commands;
    private final Deque<String> previousCommands;
    private final Receiver receiver;

    public Invoker(CollectionManager aCollectionManager, DBWorker aDBWorker) {

        previousCommands = new ArrayDeque<>(14);
        commands = new HashMap<>();
        initMap();
        receiver = new Receiver(aCollectionManager, aDBWorker, commands, previousCommands);
    }

    public Response execute(Request newCommand) {

        String aCommand = newCommand.getCommand().getCommand();

        previousCommands.offerLast(aCommand);
        if (previousCommands.size() == 15) previousCommands.removeFirst();

        return commands.get(aCommand).execute(newCommand);
    }

    private void initMap() {
        commands.put("help", new Help(receiver));
        commands.put("info", new Info(receiver));
        commands.put("show", new Show(receiver));
        commands.put("add", new Add(receiver));
        commands.put("update", new UpdateId(receiver));
        commands.put("remove_by_id", new RemoveById(receiver));
        commands.put("clear", new Clear(receiver));
        commands.put("save", new Save(receiver));
        commands.put("add_if_max", new AddIfMax(receiver));
        commands.put("add_if_min", new AddIfMin(receiver));
        commands.put("history", new History(receiver));
        commands.put("min_by_students_count", new MinByStudentsCount(receiver));
        commands.put("count_less_than_students_count", new CountLessThanStudentsCount(receiver));
        commands.put("filter_starts_with_name", new FilterStartsWithName(receiver));
    }
}