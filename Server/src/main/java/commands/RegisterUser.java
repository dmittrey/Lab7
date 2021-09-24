package commands;

import utility.Receiver;
import utility.Request;
import utility.Response;

public class RegisterUser extends CommandAbstract{

    private final Receiver receiver;

    public RegisterUser(Receiver aReceiver) {
        super("register", "add new user to system");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {

        String username = aRequest.getSession().getName();
        String password = aRequest.getSession().getPassword();
        return receiver.registerUser(username, password) ? new Response(true) : new Response(false);
    }
}
