package Database;

import data.StudyGroup;
import utility.TextFormatting;

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

    public DBWorker(Connection connection) throws NoSuchAlgorithmException {
        db = connection;
        digest = MessageDigest.getInstance("SHA-512");
    }

    public ResultSet getCollection() {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.takeAll.getStatement());
            return preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            return null;
        }
    }

    public Integer addStudyGroup(StudyGroup aStudyGroup) {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.addStudyGroup.getStatement());
            Integer newId = setStudyGroupToStatement(preparedStatement, aStudyGroup);
            preparedStatement.executeUpdate();
            return newId;
        } catch (SQLException throwables) {
            return 0;
        }
    }

    public String updateById(StudyGroup aStudyGroup, int anId) {
        try {
            String getStatus = getById(anId, aStudyGroup.getAuthor());
            if (getStatus != null) return getStatus;

            PreparedStatement preparedStatement = db.prepareStatement(Statements.updateStudyGroup.getStatement());
            setUpdatedStudyGroupToStatement(preparedStatement, aStudyGroup);
            preparedStatement.executeUpdate();
            return null;
        } catch (SQLException throwables) {
            return TextFormatting.getRedText("\n\tSome problem's with DB, try again!\n");
        }
    }

    public String removeById(int anId, String anUsername) {
        try {
            String getStatus = getById(anId, anUsername);
            if (getStatus != null) return getStatus;

            PreparedStatement preparedStatement = db.prepareStatement(Statements.deleteById.getStatement());
            preparedStatement.setInt(1, anId);
            preparedStatement.executeUpdate();
            return null;
        } catch (SQLException throwables) {
            return TextFormatting.getRedText("\n\tSome problem's with DB, try again!\n");
        }
    }

    public String getById(int anId, String anUsername) throws SQLException {
        PreparedStatement preparedStatement = db.prepareStatement(Statements.getById.getStatement());
        preparedStatement.setInt(1, anId);
        ResultSet deletingStudyGroup = preparedStatement.executeQuery();

        if (!deletingStudyGroup.next())
            return TextFormatting.getRedText("\n\tAn object with this id does not exist!\n");

        if (!deletingStudyGroup.getString("author").equals(anUsername))
            return TextFormatting.getRedText("\n\tPermission denied\n");

        return null;
    }

    public String clear(String username) {
        try {
            PreparedStatement preparedStatement = db.prepareStatement(Statements.clearAllByUser.getStatement());
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return null;
        } catch (SQLException throwables) {
            return TextFormatting.getRedText("\n\tAn object with this id does not exist!\n");
        }
    }

    public boolean addUser(String anUsername, String aPassword) {
        try {
            PreparedStatement insertStatement = db.prepareStatement(Statements.addUserWithPassword.getStatement());
            insertStatement.setString(1, anUsername);
            insertStatement.setBytes(2, getHash(aPassword));
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean loginUser(String anUsername, String aPassword) {
        try {
            PreparedStatement checkStatement = db.prepareStatement(Statements.checkUser.getStatement());
            checkStatement.setString(1, anUsername);
            checkStatement.setBytes(2, getHash(aPassword));
            ResultSet user = checkStatement.executeQuery();
            return user.next();
        } catch (SQLException e) {
            e.printStackTrace();
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
            return null;
        }
    }

    private Integer setStudyGroupToStatement(PreparedStatement stmt, StudyGroup sg) throws SQLException {
        Integer newId = generateId();
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
        return digest.digest(str.getBytes(StandardCharsets.UTF_8));
    }
}
