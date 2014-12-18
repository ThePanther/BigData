package Client.Implementation;

import Client.GUI.ChatGUI;
import Data.Message;
import Data.Packet;
import Data.Response;
import Data.Type;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ReceiverThread  extends Thread {

    private int name;
    private Socket socket;
    private ChatGUI chatGUI;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    private MessageReceiver messageReceiver;

    public ReceiverThread(int num, Socket socket, ChatGUI chatGUI, MessageReceiver messageReceiver) {
        this.name = num;
        this.socket = socket;
        this.messageReceiver = messageReceiver;
        this.chatGUI = chatGUI;
    }

    @Override
    public void run() {
        while (messageReceiver.getServiceRequested()) {
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

    private void doTransaction() throws IOException, ClassNotFoundException {

        Packet incomingPacket = (Packet) input.readObject();

        if (incomingPacket.getType() == Type.MESSAGE) {
            handleMessage(incomingPacket.getMessage());
        }
    }

    private void handleMessage(Message message) {
        chatGUI.addText(message.getFromUser(),message.getText());
        if (!chatGUI.getContacts().contains(message.getFromUser())) {
            chatGUI.addUser(message.getFromUser());
        }
        System.out.println("Message received: ");
        System.out.println("From: " + message.getFromUser());
        System.out.println("To: " + message.getToUser());
        System.out.println("Text: " + message.getText());
        send(true,"Message OK");
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

}
