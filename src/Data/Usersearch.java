package Data;


import java.io.Serializable;

public class Usersearch implements Serializable{

    private String user;

    public Usersearch(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
