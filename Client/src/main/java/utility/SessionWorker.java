package utility;

import Interfaces.ConsoleInterface;
import Interfaces.SessionWorkerInterface;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Class that helps to get session settings from console
 */
public class SessionWorker implements SessionWorkerInterface {

    private final ConsoleInterface console;

    public SessionWorker(Console aConsole) {
        console = aConsole;
    }

    @Override
    public Session getSession() throws IOException {
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
    private boolean getSessionStatus() throws IOException{
        String answer;

        do {
            System.out.print(TextFormatting.getGreenText("\tDo you register or login? [\"r\", \"l\"]: "));
                answer = console.read();
        } while (answer == null || !answer.equals("r") && !answer.equals("l"));

        return answer.equals("l");
    }

    private String getUsername() throws IOException {

        String username;
        Pattern usernamePattern = Pattern.compile("^\\s*\\b(\\w+)\\b\\s*");

        while (true) {
            console.print(TextFormatting.getGreenText("\tPlease, enter username! (Example: Lololoshka1337): "));
            username = console.read();
            if (username != null && usernamePattern.matcher(username).find()) break;
            console.print(TextFormatting.getRedText("\tUsername should be not empty string of letters and digits!\n"));
        }

        return username.trim();
    }

    private String getUserPassword() throws IOException {
        if (System.console() == null) {
            String password;
            Pattern passwordPattern = Pattern.compile("^\\s*([\\d\\w]*)\\s*");
            while (true) {
                console.print(TextFormatting.getGreenText("\tPlease, enter password! " +
                        "(You can skip this by keeping field in empty state): "));
                password = console.read();
                if (password != null && passwordPattern.matcher(password).find()) break;
                console.print(TextFormatting.getRedText("\tPassword should be string of letters and digits!\n"));
            }
            return password.trim();
        } else {
            String password;
            Pattern passwordPattern = Pattern.compile("^\\s*([\\d\\w]*)\\s*");
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
