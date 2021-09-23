package Database;

public enum Statements {

    insertWorker("INSERT INTO s312502StudyGroups " +
            "(id, name, xCoordinate, yCoordinate, salary, startDate, endDate," +
            " status,height, eyeColor, hairColor, nationality, creator ) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )"),

    generateId("SELECT nextval('ids')"),

    insertUserWithPassword("INSERT INTO s312502Users (login, hashed_password) VALUES(?, ?)"),

    checkUser("SELECT * FROM s312502Users WHERE login=? AND hashed_password=?"),

    updateWorker("UPDATE s312502StudyGroups SET " +
            "name=?, xcoordinate=?, ycoordinate=?, salary=?, startdate=?, enddate=?," +
            " status=?,height=?, eyecolor=?, haircolor=?, nationality=? " +
            "WHERE id = ?"),

    getById("SELECT * FROM s312502StudyGroups WHERE id = ?"),

    deleteById("DELETE FROM s312502StudyGroups WHERE id = ?"),

    clearAllByUser("DELETE FROM s312502StudyGroups WHERE creator = ?"),

    takeAll("SELECT * FROM s312502StudyGroups");

    private final String statement;

    Statements (String aStatement) {
        statement = aStatement;
    }

    public String getStatement() {
        return statement;
    }
}
