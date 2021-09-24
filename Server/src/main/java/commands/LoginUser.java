package commands;

import utility.*;

public class LoginUser extends CommandAbstract {

    private final Receiver receiver;

    public LoginUser(Receiver aReceiver) {
        super("register", "add new user to system");
        receiver = aReceiver;
    }

    @Override
    public Response execute(Request aRequest) {

        String username = aRequest.getSession().getName();
        String password = aRequest.getSession().getPassword();
        return receiver.loginUser(username, password)
                ? new Response("")
                : new Response(TextFormatting.getRedText("\n\tAccount with this parameters doesn't exist! " +
                "Reboot the client to authorize again!\n"));
    }
}
