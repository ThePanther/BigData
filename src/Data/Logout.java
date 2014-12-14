package Data;

import java.io.Serializable;


public class Logout implements Serializable {

    private String userName;

    public Logout(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
