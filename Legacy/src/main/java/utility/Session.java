package utility;

import java.io.Serializable;

/**
 * Class to serialize client's session
 */
public class Session implements Serializable {

    private final String name;
    private final String password;

    public Session(String aName, String aPassword) {
        name = aName;
        password = aPassword;
    }

    public Session(String aName) {
        name = aName;
        password = null;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
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
