package utility;

import java.io.Serializable;

/**
 * Class to serialize client's session
 */
public class Session implements Serializable {

    private final String name;
    private final String password;
    private final TypeOfSession typeOfSession;

    public Session (String aName, String aPassword, TypeOfSession aTypeOfSession) {
        name = aName;
        password = (aPassword.trim().equals("")) ? null : aPassword;
        typeOfSession = aTypeOfSession;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public TypeOfSession getTypeOfSession(){
        return typeOfSession;
    }

    public boolean isPasswordSpecified(){
        return password != null;
    }

    @Override
    public String toString(){
        return "username: " + name
                + "\npassword: " + (password != null ? password : "unspecified");
    }
}
