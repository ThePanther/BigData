package Server.Implementation;


import Data.*;

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

    public ServerThread(int num, Socket socket) {
        this.name = num;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (serviceRequested) {
            try {
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
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
        } else {
            send(false, "ERROR unknown Packet");
        }
    }

    private void handleLogin(Login login) {
        //TODO: handle DB

        messageSender = new MessageSender(login.getUserName());

        System.out.println("Login received: ");
        System.out.println("Username: " + login.getUserName());
        System.out.println("Password: " + login.getPassword());
        send(true, "Login OK");
    }

    private void handleRegistration(Registration registration) {
        //TODO: handle DB
        System.out.println("Registration received: ");
        System.out.println("Username: " + registration.getUserName());
        System.out.println("Password: " + registration.getPassword());
        System.out.println("Email: " + registration.geteMail());
        System.out.println("Address: " + registration.getAddress());
        System.out.println("Birthday: " + registration.getBirthDate());
        System.out.println("Job: " + registration.getJob());
        System.out.println("Sex: " + registration.getSex());
        send(true, "Registration OK");
    }

    private void handleMessage(Message message) {
        //TODO: handle DB
        try {
            //TODO CLIENT IP form DB
            messageSender.connectToClient("clientIP",0);
            messageSender.sendMessage(message.getToUser(),message.getText());
            messageSender.disconnectFromClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Message received: ");
        System.out.println("From: " + message.getFromUser());
        System.out.println("To: " + message.getToUser());
        System.out.println("Text: " + message.getText());
        send(true, "Message OK");
    }

    private void handleLogout(Logout logout) {
        //TODO: handle DB
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

}
