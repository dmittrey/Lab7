package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to connecting to database
 */
public class DBConnector {

    public static Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            //Database driver isn't exist
        }

        String login = System.getenv("DB_LOGIN");
        String password = System.getenv("DB_PASSWORD");
        String host = System.getenv("DB_HOST");
        if (login == null || password == null) {
            //Кидаем исключение
        }
        if (host == null) {
            host = "jdbc:postgresql://pg:5432/studs";
        }
        return DriverManager.getConnection(host, login, password);
    }
}
