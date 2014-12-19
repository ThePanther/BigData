package Server.Implementation;


import Data.*;
import Server.DB.MongoDB;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{

    private int name;
    private Socket socket;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    private boolean serviceRequested = true;

    private MessageSender messageSender;

    private MongoDB mongoDB;

    public ServerThread(int num, Socket socket) {
        this.name = num;
        this.socket = socket;
        this.mongoDB = new MongoDB();
        mongoDB.init();
    }

    @Override
    public void run() {
        while (serviceRequested) {
            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                input = new ObjectInputStream(socket.getInputStream());

                while (!socket.isClosed()) {
                    doTransaction();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void send(boolean state, String text) {
        Packet packet = new Packet(Type.RESPONSE);
        packet.setResponse(new Response(state,text));
        try {
            output.writeObject(packet);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doTransaction() throws IOException, ClassNotFoundException {

        Packet incomingPacket = (Packet) input.readObject();

        if (incomingPacket.getType() == Type.LOGIN) {
            handleLogin(incomingPacket.getLogin());
        } else if (incomingPacket.getType() == Type.REGISTRATION) {
            handleRegistration(incomingPacket.getRegistration());
        } else if (incomingPacket.getType() == Type.MESSAGE) {
            handleMessage(incomingPacket.getMessage());
        } else if (incomingPacket.getType() == Type.LOGOUT) {
            handleLogout(incomingPacket.getLogout());
        } else if (incomingPacket.getType() == Type.USERSEARCH){
            handleUsersearch(incomingPacket.getUsersearch());
        } else {
            send(false, "ERROR unknown Packet");
        }
    }

    private void handleLogin(Login login) {
        boolean res = mongoDB.login(login);
        if (res) {
            messageSender = new MessageSender(login.getUserName());
            System.out.println("Login received: ");
            System.out.println("Username: " + login.getUserName());
            System.out.println("Password: " + login.getPassword());
            send(true, "Login OK");
        } else {
            System.out.println("Login received: ");
            System.out.println("Username: " + login.getUserName());
            System.out.println("Password: " + login.getPassword());
            send(false, "Login NOT OK");
        }
    }

    private void handleRegistration(Registration registration) {
        boolean res = true;//TODO:mongoDB.register(registration);
        if (res) {
            messageSender = new MessageSender(registration.getUserName());
            System.out.println("Registration received: ");
            System.out.println("Username: " + registration.getUserName());
            System.out.println("Password: " + registration.getPassword());
            System.out.println("Email: " + registration.geteMail());
            System.out.println("Address: " + registration.getAddress());
            System.out.println("Birthday: " + registration.getBirthDate());
            System.out.println("Job: " + registration.getJob());
            System.out.println("Sex: " + registration.getSex());
            send(true, "Registration OK");
        } else {
            System.out.println("Registration received: ");
            System.out.println("Username: " + registration.getUserName());
            System.out.println("Password: " + registration.getPassword());
            System.out.println("Email: " + registration.geteMail());
            System.out.println("Address: " + registration.getAddress());
            System.out.println("Birthday: " + registration.getBirthDate());
            System.out.println("Job: " + registration.getJob());
            System.out.println("Sex: " + registration.getSex());
            send(false, "Registration NOT OK");
        }

    }

    private void handleMessage(Message message) {
        //TODO: handle Neo4j
        mongoDB.saveMessage(message);
        System.out.println("Message received: ");
        System.out.println("From: " + message.getFromUser());
        System.out.println("To: " + message.getToUser());
        System.out.println("Text: " + message.getText());
        send(true, "Message OK");

        try {
            //TODO CLIENT IP  and port form Neo4J
            messageSender.connectToClient("localhost",50002);
            messageSender.sendMessage(message.getToUser(),message.getText());
            messageSender.disconnectFromClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleLogout(Logout logout) {
        //TODO: handle Neo4J
        System.out.println("Logout received: ");
        System.out.println("Username: " + logout.getUserName());
        send(true, "Logout OK");
        try {
            socket.close();
            serviceRequested = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUsersearch(Usersearch usersearch) {
        boolean res = mongoDB.searchUser(usersearch);
        if (res) {
            System.out.println("Usersearch received: ");
            System.out.println("Username: " + usersearch.getUser());
            send(true, "User found");
        } else {
            System.out.println("Usersearch received: ");
            System.out.println("Username: " + usersearch.getUser());
            send(false, "User NOT found");
        }
    }

}
