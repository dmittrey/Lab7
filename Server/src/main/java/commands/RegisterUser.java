package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;
import utility.TextFormatting;

public class RegisterUser extends CommandAbstract {

    private final Receiver receiver;

    public RegisterUser(Receiver aReceiver) {
        super("register", "add new user to system");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {

        String username = aRequest.getSession().getName();
        String password = aRequest.getSession().getPassword();
        if (!receiver.loginUser(username, password)) {
            if (receiver.registerUser(username, password)) return new Response("");
        }
        return new Response(TextFormatting.getRedText("\n\tThis account already registered!\n"));
    }
}
