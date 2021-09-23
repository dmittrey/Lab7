package utility;

import Interfaces.ConsoleInterface;
import Interfaces.SessionWorkerInterface;

import java.io.IOException;
import java.util.regex.Pattern;

public class SessionWorker implements SessionWorkerInterface {

    private final ConsoleInterface console;

    public SessionWorker(Console aConsole) {
        console = aConsole;
    }

    @Override
    public Session getSession() {
        console.print(TextFormatting.getBlueText("\nAuthorization(Registration): \n"));
        return new Session(getUsername(), getUserPassword());
    }

    private String getUsername() {

        String username;
        Pattern usernamePattern = Pattern.compile("^\\s*\\b(\\w+)\\b\\s*");

        while (true) {
            console.print(TextFormatting.getGreenText("\tPlease, enter username! (Example: Lololoshka1337): "));
            try {
                username = console.read();
                if (username != null && usernamePattern.matcher(username).find()) break;
            } catch (IOException e) {
                username = null;
            }
            console.print(TextFormatting.getRedText("\tUsername should be not empty string of letters and digits!\n"));
        }

        return username.trim();
    }

    private String getUserPassword() {

        if (System.console() == null) {

            String password;
            Pattern passwordPattern = Pattern.compile("^\\s*\\b([\\d\\w]+)\\b\\s*");
            while (true) {
                console.print(TextFormatting.getGreenText("\tPlease, enter password! (Example: 232323): "));
                try {
                    password = console.read();
                    if (password != null && passwordPattern.matcher(password).find()) break;
                } catch (IOException e) {
                    password = null;
                }
                console.print(TextFormatting.getRedText("\tPassword should be not empty string of letters and digits!\n"));
            }
            return password.trim();
        } else {

            String password;
            Pattern passwordPattern = Pattern.compile("^\\s*\\b([\\d\\w]+)\\b\\s*");

            do {
                console.print(TextFormatting.getGreenText("\tPlease, enter password! (Example: 232323): "));
                password = new String(System.console().readPassword());
                console.print(TextFormatting.getRedText("\tPassword should be not empty string of letters and digits!\n"));
            } while (!passwordPattern.matcher(password).find());
            return password.trim();
        }
    }
}
