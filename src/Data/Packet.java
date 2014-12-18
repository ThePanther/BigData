package Data;


import java.io.Serializable;

public class Packet implements Serializable{

    private Type type;
    private Login login = null;
    private Logout logout = null;
    private Registration registration = null;
    private Message message = null;
    private Usersearch usersearch = null;
    private Response response = null;

    public Packet(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Logout getLogout() {
        return logout;
    }

    public void setLogout(Logout logout) {
        this.logout = logout;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Usersearch getUsersearch() {
        return usersearch;
    }

    public void setUsersearch(Usersearch usersearch) {
        this.usersearch = usersearch;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
