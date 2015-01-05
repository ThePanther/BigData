package Data;


import java.io.Serializable;

public class Login implements Serializable {

    private String userName;
    private String password;
    private String clientIP;
    private int clientPort;

    public Login(String userName, String password, String clientIP, int clientPort) {
        this.userName = userName;
        this.password = password;
        this.clientIP = clientIP;
        this.clientPort = clientPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }
}
