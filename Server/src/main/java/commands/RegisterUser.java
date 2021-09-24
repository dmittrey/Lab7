package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

public class RegisterUser extends CommandAbstract{

    private final Receiver receiver;

    public RegisterUser(Receiver aReceiver) {
        super("register", "add new user to system");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {
        System.out.println(1);
        String username = aRequest.getSession().getName();
        String password = aRequest.getSession().getPassword();
        System.out.println(2);
        return receiver.registerUser(username, password) ? null
                : new Response(TextFormatting.getRedText("\n\tThis account already registered!\n"));
    }
}
