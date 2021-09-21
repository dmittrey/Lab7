package utility;

import java.io.IOException;
import java.util.regex.Pattern;

public class SessionWorker {

    private final Console console;

    public SessionWorker(Console aConsole) {
        console = aConsole;
    }

    public Session getSession() {

        return new Session(getUserLogin(), getUserPassword());
    }

    private String getUserLogin() {

        String arg;
        Pattern remoteHostPortPattern = Pattern.compile("^\\s*\\b(\\w+)\\b\\s*");

        do {
            console.print(TextFormatting.getGreenText("\nPlease, enter username! (Example: Lololoshka1337): "));
            try {
                arg = console.read();
            } catch (IOException e) {
                arg = null;
            }
        } while (arg == null || !remoteHostPortPattern.matcher(arg).find() || (Integer.parseInt(arg.trim()) >= 65536)
                || (Integer.parseInt(arg.trim()) <= 0));

        return arg.trim();

    }

    private String getUserPassword() {

        String arg;
        Pattern remoteHostPortPattern = Pattern.compile("^\\s*\\b([\\d\\w]+)\\b\\s*");

        do {
            console.print(TextFormatting.getGreenText("\nPlease, enter password! (Example: 232323): "));
            try {
                arg = console.read();
            } catch (IOException e) {
                arg = null;
            }
        } while (arg == null || !remoteHostPortPattern.matcher(arg).find() || (Integer.parseInt(arg.trim()) >= 65536)
                || (Integer.parseInt(arg.trim()) <= 0));

        return arg.trim();
    }
}
