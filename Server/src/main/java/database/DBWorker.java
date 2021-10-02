package database;

import data.StudyGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.TypeOfAnswer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Class to work with database
 */
public class DBWorker {

    private final Connection db;
    private final MessageDigest digest;
    public static final Logger logger = LoggerFactory.getLogger("Receiver");

    public DBWorker(Connection connection) throws NoSuchAlgorithmException {
        db = connection;
        digest = MessageDigest.getInstance("SHA-512");
    }

    public ResultSet getCollection() {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.takeAll.getStatement());
            return preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            logger.warn("SQL problem with taking all collection!");
            return null;
        }
    }

    public Integer addStudyGroup(StudyGroup aStudyGroup) {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.addStudyGroup.getStatement());
            Integer newId = setStudyGroupToStatement(preparedStatement, aStudyGroup);
            preparedStatement.executeUpdate();
            return (newId == null) ? 0 : newId;
        } catch (SQLException throwables) {
            logger.warn("SQL problem with adding new element!");
            return 0;
        }
    }

    public TypeOfAnswer updateById(StudyGroup aStudyGroup, int anId) {
        try {
            TypeOfAnswer status = getById(anId, aStudyGroup.getAuthor());
            if (!status.equals(TypeOfAnswer.SUCCESSFUL)) return status;

            PreparedStatement preparedStatement = db.prepareStatement(Statements.updateStudyGroup.getStatement());
            setUpdatedStudyGroupToStatement(preparedStatement, aStudyGroup);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException throwables) {
            logger.warn("SQL problem with updating element !");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public TypeOfAnswer removeById(int anId, String anUsername) {
        try {
            TypeOfAnswer status = getById(anId, anUsername);
            if (!status.equals(TypeOfAnswer.SUCCESSFUL)) return status;

            PreparedStatement preparedStatement = db.prepareStatement(Statements.deleteById.getStatement());
            preparedStatement.setInt(1, anId);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException throwables) {
            logger.warn("SQL problem with removing element!");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public TypeOfAnswer getById(int anId, String anUsername) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = db.prepareStatement(Statements.getById.getStatement());
            preparedStatement.setInt(1, anId);
            ResultSet deletingStudyGroup = preparedStatement.executeQuery();

            if (!deletingStudyGroup.next())
                return TypeOfAnswer.OBJECTNOTEXIST;

            if (!deletingStudyGroup.getString("author").equals(anUsername))
                return TypeOfAnswer.PERMISSIONDENIED;

            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException throwables) {
            logger.warn("SQL problem with getting element!");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public TypeOfAnswer clear(String username) {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.clearAllByUser.getStatement());
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return TypeOfAnswer.SUCCESSFUL;
        } catch (SQLException throwables) {
            logger.warn("SQL problem with removing elements!");
            return TypeOfAnswer.SQLPROBLEM;
        }
    }

    public boolean addUser(String anUsername, String aPassword) {
        try {
            PreparedStatement insertStatement = db.prepareStatement(Statements.addUserWithPassword.getStatement());
            insertStatement.setString(1, anUsername);
            insertStatement.setBytes(2, getHash(aPassword));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.warn("SQL problem with adding user!");
            return false;
        }
    }

    public boolean loginUser(String anUsername, String aPassword) {
        try {
            PreparedStatement checkStatement = db.prepareStatement(Statements.checkUser.getStatement());
            checkStatement.setString(1, anUsername);
            checkStatement.setBytes(2, getHash(aPassword));
            ResultSet user = checkStatement.executeQuery();
            return user.next();
        } catch (SQLException e) {
            logger.warn("SQL problem with logging user!");
            return false;
        }
    }

    private Integer generateId() {
        try {
            Statement statement = db.createStatement();
            ResultSet resultSet = statement.executeQuery(Statements.generateId.getStatement());
            if (resultSet.next()) {
                return resultSet.getInt("nextval");
            }
            return null;
        } catch (SQLException throwables) {
            logger.warn("SQL problem with generating id!");
            return null;
        }
    }

    private Integer setStudyGroupToStatement(PreparedStatement stmt, StudyGroup sg) throws SQLException {
        Integer newId = generateId();
        if (newId == null) return null;

        sg.setId(newId);
        stmt.setInt(1, sg.getId());
        stmt.setString(2, sg.getName());
        stmt.setInt(3, sg.getCoordinates().getX());
        stmt.setDouble(4, sg.getCoordinates().getY());
        stmt.setInt(5, sg.getStudentsCount());
        if ((sg.getAverageMark() == null)) {
            stmt.setNull(6, Types.BIGINT);
        } else {
            stmt.setDouble(6, sg.getAverageMark());
        }
        if ((sg.getFormOfEducation() == null)) {
            stmt.setNull(7, Types.VARCHAR);
        } else {
            stmt.setString(7, sg.getFormOfEducation().toString());
        }
        stmt.setString(8, sg.getSemesterEnum().toString());
        stmt.setString(9, sg.getGroupAdmin().getName());
        stmt.setLong(10, sg.getGroupAdmin().getWeight());
        stmt.setString(11, sg.getGroupAdmin().getHairColor().toString());
        stmt.setString(12, sg.getAuthor());

        return newId;
    }

    private void setUpdatedStudyGroupToStatement(PreparedStatement stmt, StudyGroup sg) throws SQLException {
        sg.setId(generateId());
        stmt.setString(1, sg.getName());
        stmt.setInt(2, sg.getCoordinates().getX());
        stmt.setDouble(3, sg.getCoordinates().getY());
        stmt.setInt(4, sg.getStudentsCount());
        if ((sg.getAverageMark() == null)) {
            stmt.setNull(5, Types.BIGINT);
        } else {
            stmt.setDouble(5, sg.getAverageMark());
        }
        if ((sg.getFormOfEducation() == null)) {
            stmt.setNull(6, Types.VARCHAR);
        } else {
            stmt.setString(6, sg.getFormOfEducation().toString());
        }
        stmt.setString(7, sg.getSemesterEnum().toString());
        stmt.setString(8, sg.getGroupAdmin().getName());
        stmt.setLong(9, sg.getGroupAdmin().getWeight());
        stmt.setString(10, sg.getGroupAdmin().getHairColor().toString());
        stmt.setInt(11, sg.getId());
    }

    private byte[] getHash(String str) {
        return (str == null)
                ? digest.digest("null".getBytes(StandardCharsets.UTF_8))
                : digest.digest(str.getBytes(StandardCharsets.UTF_8));
    }
}