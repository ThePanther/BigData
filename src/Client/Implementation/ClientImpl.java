package Client.Implementation;


import Data.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientImpl {

    private Socket socket;
    private String serverIP;
    private String clientIP;
    private int serverPort;
    private int clientPort;
    private String userName;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientImpl(String serverIP, int serverPort, String clientIP, int clientPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientIP = clientIP;
        this.clientPort = clientPort;
    }

    public void connectToServer() throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(serverIP,serverPort));
        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();
        input = new ObjectInputStream(socket.getInputStream());
    }

    private void send(Packet packet) throws IOException {
        output.writeObject(packet);
        output.flush();
    }

    private Response read() throws IOException, ClassNotFoundException {
        Packet incomingPacket = (Packet) input.readObject();
        if (incomingPacket.getType() == Type.RESPONSE) {
            return incomingPacket.getResponse();
        } else {
            System.out.println("Unknown Packet from Server");
            return new Response(false, "No Response");
        }
    }

    public Response login(String userName, String password) throws IOException, ClassNotFoundException {
        Packet packet = new Packet(Type.LOGIN);
        this.userName = userName;
        Login login = new Login(userName,password,this.clientIP,this.clientPort);
        packet.setLogin(login);
        send(packet);
        return read();
    }

    public Response logout(String userName) throws IOException, ClassNotFoundException {
        Packet packet = new Packet(Type.LOGOUT);
        Logout logout = new Logout(userName);
        packet.setLogout(logout);
        send(packet);
        return read();
    }

    public Response register(String eMail, String userName, String password, String birthDate, String sex, String address, String job) throws IOException, ClassNotFoundException {
        Packet packet = new Packet(Type.REGISTRATION);
        Registration registration = new Registration(eMail,userName,password,this.clientIP,this.clientPort);
        registration.setBirthDate(birthDate);
        registration.setSex(sex);
        registration.setAddress(address);
        registration.setJob(job);
        packet.setRegistration(registration);
        send(packet);
        return read();
    }

    public Response sendMessage(String toUser, String text) throws IOException, ClassNotFoundException {
        Packet packet = new Packet(Type.MESSAGE);
        Message message = new Message(this.userName,toUser,text);
        packet.setMessage(message);
        send(packet);
        return read();
    }

    public Response searchUser(String user) throws IOException, ClassNotFoundException {
        Packet packet = new Packet(Type.USERSEARCH);
        Usersearch usersearch = new Usersearch(user);
        packet.setUsersearch(usersearch);
        send(packet);
        return read();
    }

    public void disconnectFromServer() throws IOException {
        socket.close();
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
