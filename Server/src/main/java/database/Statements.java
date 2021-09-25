package database;

/**
 * Enum of database universal statements
 */
public enum Statements {

    addStudyGroup("INSERT INTO s312502StudyGroups " +
            "(id, name, xCoordinate, yCoordinate, studentsCount, averageMark, formOfEducation, " +
            "semesterEnum, groupAdminName, groupAdminWeight, groupAdminHairColor, author) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),

    generateId("SELECT nextval('ids')"),

    addUserWithPassword("INSERT INTO s312502Users (username, hashPassword) VALUES(?, ?)"),

    checkUser("SELECT * FROM s312502Users WHERE username=? AND hashPassword=?"),

    updateStudyGroup("UPDATE s312502StudyGroups SET " +
            "name=?, xCoordinate=?, yCoordinate=?, studentsCount=?, averageMark=?, formOfEducation=?, " +
            "semesterEnum=?, groupAdminName=?, groupAdminWeight=?, groupAdminHairColor=? " +
            "WHERE id = ?"),

    getById("SELECT * FROM s312502StudyGroups WHERE id = ?"),

    deleteById("DELETE FROM s312502StudyGroups WHERE id = ?"),

    clearAllByUser("DELETE FROM s312502StudyGroups WHERE author = ?"),

    takeAll("SELECT * FROM s312502StudyGroups");

    private final String statement;

    Statements(String aStatement) {
        statement = aStatement;
    }

    public String getStatement() {
        return statement;
    }
}
