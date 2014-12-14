package Server.Implementation;


import Data.Message;
import Data.Packet;
import Data.Type;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MessageSender {

    private String userName;
    private Socket socket;

    private ObjectOutputStream output;

    public MessageSender(String userName) {
        this.userName = userName;
    }

    public void connectToClient(String clientIP, int clientPort) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(clientIP,clientPort));

        output = new ObjectOutputStream(socket.getOutputStream());
    }

    private void send(Packet packet) throws IOException {
        output.writeObject(packet);
        output.flush();
    }

    public void sendMessage(String toUser, String text) throws IOException, ClassNotFoundException {
        Packet packet = new Packet(Type.MESSAGE);
        Message message = new Message(this.userName,toUser,text);
        packet.setMessage(message);
        send(packet);
    }

    public void disconnectFromClient() throws IOException {
        socket.close();
    }

}
