package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to initialize database tables
 */
public class DBInitializer {

    private final Connection dbConnection;

    public DBInitializer(Connection aDbConnection) {
        dbConnection = aDbConnection;
    }

    public void initialize() throws SQLException {

        Statement stmt = dbConnection.createStatement();

        stmt.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1");

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS s312502StudyGroups (" +
                "id int PRIMARY KEY," +
                "name varchar(255) NOT NULL CHECK(name<>'')," +
                "xCoordinate int," +
                "yCoordinate bigint NOT NULL," +
                "creationDate date DEFAULT (current_date)," +
                "studentsCount int NOT NULL CHECK(studentsCount > 0)," +
                "averageMark bigint CHECK(averageMark > 0)," +
                "formOfEducation varchar(20) " +
                "CHECK(formOfEducation='DISTANCE_EDUCATION' OR " +
                "formOfEducation='FULL_TIME_EDUCATION' OR " +
                "formOfEducation='EVENING_CLASSES')," +
                "semesterEnum varchar(6) NOT NULL " +
                "CHECK(semesterEnum='SECOND' OR " +
                "semesterEnum='THIRD' OR " +
                "semesterEnum='FOURTH' OR " +
                "semesterEnum='FIFTH' OR " +
                "semesterEnum='SIXTH')," +
                "groupAdminName varchar(255) NOT NULL CHECK(groupAdminName<>'')," +
                "groupAdminWeight bigint NOT NULL CHECK(groupAdminWeight>0)," +
                "groupAdminHairColor varchar(6) NOT NULL " +
                "CHECK(groupAdminHairColor='BLACK' OR " +
                "groupAdminHairColor='BLUE' OR " +
                "groupAdminHairColor='YELLOW' OR " +
                "groupAdminHairColor='WHITE' OR " +
                "groupAdminHairColor='BROWN')," +
                "author varchar(255)" +
                ")");

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS s312502Users (" +
                "username varchar(255) PRIMARY KEY," +
                "hashPassword BYTEA DEFAULT (null)" +
                ")");
    }
}