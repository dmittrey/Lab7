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
        boolean sessionStatus = getSessionStatus();
        return sessionStatus ? new Session(getUsername(), getUserPassword(), TypeOfSession.Login)
                : new Session(getUsername(), getUserPassword(), TypeOfSession.Register);
    }

    /**
     * Type of Session
     *
     * @return true if login, false if register
     */
    private boolean getSessionStatus() {
        String answer;

        do {
            System.out.print(TextFormatting.getGreenText("\tDo you register or login? [\"r\", \"l\"]: "));
            try {
                answer = console.read();
            } catch (IOException e) {
                answer = null;
            }
        } while (answer == null || !answer.equals("r") && !answer.equals("l"));

        return answer.equals("l");
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
            Pattern passwordPattern = Pattern.compile("^\\s*\\b([\\d\\w]*)\\b\\s*");
            while (true) {
                console.print(TextFormatting.getGreenText("\tPlease, enter password! " +
                        "(You can skip this by keeping field in empty state): "));
                try {
                    password = console.read();
                    if (password != null && passwordPattern.matcher(password).find()) break;
                } catch (IOException e) {
                    password = null;
                }
                console.print(TextFormatting.getRedText("\tPassword should be string of letters and digits!\n"));
            }
            return password.trim();
        } else {
            String password;
            Pattern passwordPattern = Pattern.compile("^\\s*\\b([\\d\\w]*)\\b\\s*");
            while (true) {
                console.print(TextFormatting.getGreenText("\tPlease, enter password! " +
                        "(You can skip this by keeping field in empty state): "));
                password = new String(System.console().readPassword());
                if (passwordPattern.matcher(password).find()) break;
                console.print(TextFormatting.getRedText("\tPassword should be string of letters and digits!\n"));
            }
            return password.trim();
        }
    }
}
