package Server.Implementation;


import Data.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{

    private int name;
    private Socket socket;
    private ServerImpl server;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    private boolean serviceRequested = true;

    public ServerThread(int num, Socket socket, ServerImpl server) {
        this.name = num;
        this.socket = socket;
        this.server = server;
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
        } else {
            send(false, "ERROR unknown Packet");
        }
    }

    private void handleLogin(Login login) {
        //TODO: handle DB
        System.out.println("Login recived: ");
        System.out.println("Username: " + login.getUserName());
        System.out.println("Password: " + login.getPassword());
        send(true, "Login OK");
    }

    private void handleRegistration(Registration registration) {
        //TODO: handle DB
        System.out.println("Registration recived: ");
        System.out.println("Username: " + registration.getUserName());
        System.out.println("Password: " + registration.getPassword());
        System.out.println("Email: " + registration.geteMail());
        System.out.println("Addres: " + registration.getAddress());
        System.out.println("Birthdate: " + registration.getBirthDate());
        System.out.println("Job: " + registration.getJob());
        System.out.println("Sex: " + registration.getSex());
        send(true, "Registration OK");
    }

    private void handleMessage(Message message) {
        //TODO: handle DB
        System.out.println("Message recived: ");
        System.out.println("From: " + message.getFromUser());
        System.out.println("To: " + message.getToUser());
        System.out.println("Text: " + message.getText());
        send(true, "Message OK");
    }

}
