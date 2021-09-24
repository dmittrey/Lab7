package utility;

import commands.*;

import java.util.*;

/**
 * Invoker class from pattern Command to logging commands from client
 */
public class Invoker {

    private final Map<String, CommandAbstract> commands;
    private final Receiver receiver;


    public Invoker(Receiver aReceiver) {
        commands = new HashMap<>();
        receiver = aReceiver;
        initMap();
    }

    public Receiver getReceiver(){
        return receiver;
    }

    public Response execute(Request aRequest) {
        boolean status;
        String aCommand = aRequest.getCommand().getCommand();

        if (aRequest.getSession().getTypeOfSession() == TypeOfSession.Register) {
            status = commands.get("register").execute(aRequest).getStatus();
            if (!status) return new Response(TextFormatting.getRedText("\n\tThis account already registered!\n"));
        } else {
            status = commands.get("login").execute(aRequest).getStatus();
            if (!status)
                return new Response(TextFormatting.getRedText("\n\tAccount with this parameters doesn't exist! " +
                        "Reboot the client to authorize again!\n"));
        }

        return commands.get(aCommand).execute(aRequest);
    }

    private void initMap() {
        commands.put("help", new Help(commands, receiver));
        commands.put("info", new Info(receiver));
        commands.put("show", new Show(receiver));
        commands.put("add", new Add(receiver));
        commands.put("update", new UpdateId(receiver));
        commands.put("remove_by_id", new RemoveById(receiver));
        commands.put("clear", new Clear(receiver));
        commands.put("add_if_max", new AddIfMax(receiver));
        commands.put("add_if_min", new AddIfMin(receiver));
        commands.put("history", new History(receiver));
        commands.put("min_by_students_count", new MinByStudentsCount(receiver));
        commands.put("count_less_than_students_count", new CountLessThanStudentsCount(receiver));
        commands.put("filter_starts_with_name", new FilterStartsWithName(receiver));
        commands.put("register", new RegisterUser(receiver));
        commands.put("login", new LoginUser(receiver));
    }
}