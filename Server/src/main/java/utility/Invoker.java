package utility;

import commands.*;

import java.util.*;

/**
 * Proxy class to redirect commands and write the executed commands to the queue
 */
public class Invoker {

    private final CollectionManager collectionManager;
    private final Map<String, CommandAbstract> commands;
    private final Deque<String> previousCommands;
    private final FileWorker fileWorker;

    public Invoker(CollectionManager aCollectionManager, FileWorker aFileWorker) {

        collectionManager = aCollectionManager;
        commands = new HashMap<>();
        previousCommands = new ArrayDeque<>(14);
        fileWorker = aFileWorker;
        initMap();
    }

    public Response execute(Request newCommand) {

        String aCommand = newCommand.getCommand();

        previousCommands.offerLast(aCommand);
        if (previousCommands.size() == 15) previousCommands.removeFirst();

        return commands.get(aCommand).execute(newCommand);
    }

    private void initMap() {
        commands.put("help", new Help(commands));
        commands.put("info", new Info(collectionManager));
        commands.put("show", new Show(collectionManager));
        commands.put("add", new Add(collectionManager));
        commands.put("update", new UpdateId(collectionManager, previousCommands));
        commands.put("remove_by_id", new RemoveById(collectionManager, previousCommands));
        commands.put("clear", new Clear(collectionManager));
        commands.put("save", new Save(fileWorker));
        commands.put("execute_script", new ExecuteScript());
        commands.put("add_if_max", new AddIfMax(collectionManager, previousCommands));
        commands.put("add_if_min", new AddIfMin(collectionManager, previousCommands));
        commands.put("history", new History(previousCommands));
        commands.put("min_by_students_count", new MinByStudentsCount(collectionManager));
        commands.put("count_less_than_students_count", new CountLessThanStudentsCount(collectionManager));
        commands.put("filter_starts_with_name", new FilterStartsWithName(collectionManager));
    }
}