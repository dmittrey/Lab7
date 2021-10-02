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

    public Receiver getReceiver() {
        return receiver;
    }

    public Response execute(Request aRequest) {
        String aCommand = aRequest.getCommand().getCommand();
        String username = aRequest.getSession().getName();
        String password = aRequest.getSession().getPassword();
        return (!commands.get(aCommand).getAuthorizationStatus()) ? commands.get(aCommand).execute(aRequest)
                : (aCommand.equals("register") || receiver.loginUser(username, password))
                    ? commands.get(aCommand).execute(aRequest)
                    : new Response(TypeOfAnswer.NOTMATCH);
    }

    private void initMap() {
        commands.put("help", new Help(commands, receiver));//Map<String, String>
        commands.put("info", new Info(receiver));//Map<String, String>
        commands.put("show", new Show(receiver));//Set<StudyGroup>
        commands.put("add", new Add(receiver));//TypeOfAnswer
        commands.put("update", new UpdateId(receiver));//TypeOfAnswer
        commands.put("remove_by_id", new RemoveById(receiver));//TypeOfAnswer
        commands.put("clear", new Clear(receiver));//TypeOfAnswer
        commands.put("add_if_max", new AddIfMax(receiver));//TypeOfAnswer
        commands.put("add_if_min", new AddIfMin(receiver));//TypeOfAnswer
        commands.put("history", new History(receiver));//Map<String, String>
        commands.put("min_by_students_count", new MinByStudentsCount(receiver));//StudyGroup
        commands.put("count_less_than_students_count", new CountLessThanStudentsCount(receiver));//Long
        commands.put("filter_starts_with_name", new FilterStartsWithName(receiver));//Set<StudyGroup>
        commands.put("register", new RegisterUser(receiver));//TypeOfAnswer
        commands.put("login", new LoginUser(receiver));//TypeOfAnswer
    }
}