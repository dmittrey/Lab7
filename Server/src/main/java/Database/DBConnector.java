package Database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    public static final Logger logger = LoggerFactory.getLogger("Database");

    public Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.warn("DB driver not found!");
            return null;
        }
        String login = System.getenv("DB_LOGIN");
        String password = System.getenv("DB_PASSWORD");
        String host = System.getenv("DB_HOST");
        if (login == null || password == null) {
            logger.warn("Env variables DB_LOGIN or DB_PASSWORD are not matched!");
            return null;
        }
        if (host == null) {
            host = "jdbc:postgresql://pg:5432/studs";
        }
        return DriverManager.getConnection(host, login, password);
    }
}