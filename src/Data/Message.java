package Data;


import java.io.Serializable;

public class Message implements Serializable {

    private String fromUser;
    private String toUser;
    private String text;

    public Message(String fromUser, String toUser, String text) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.text = text;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
