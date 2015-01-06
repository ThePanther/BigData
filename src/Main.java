import Data.Login;
import Data.Message;
import Data.Registration;
import Data.Usersearch;
import DB.MongoDB;
import DB.Neo4J;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        MongoDB mongoDB = new MongoDB();
        Neo4J neo4J = new Neo4J();
        System.out.println("Mongo init");
        mongoDB.init();
        System.out.println("Mongo init OK");
        System.out.println("Neo4J init");
        neo4J.init();
        System.out.println("Neo4J init OK");

        System.out.println("Neo4j create user");
        long id = neo4J.createUser("testUser");
        System.out.println("Neo4J create user OK id: " + id);

        System.out.println("Mongo registrierung");
        Registration registration = new Registration("test@gmx.de","testUser","abc");
        registration.setAddress("");
        registration.setBirthDate("");
        registration.setJob("");
        registration.setSex("");
        boolean reg = mongoDB.register(registration,id);
        System.out.println("Mongo registrierung " + reg);

        System.out.println("Mongo login");
        boolean log = mongoDB.login(new Login("testUser","abc"));
        System.out.println("Mongo login " + log);
        System.out.println("Mongo search User");
        boolean search = mongoDB.searchUser(new Usersearch("testUser"));
        System.out.println("Mongo search User " + search);
        System.out.println("Mongo save Message");
        Message message = new Message("testUser","HansWurst","hallo dies ist ein test");
        mongoDB.saveMessage(message);
        System.out.println("Mongo save Message OK");

        System.out.println("Mongo get UserID");
        long id3 = mongoDB.getUserNodeID("testUser");
        System.out.println("Mongo get UserID OK id: "+ id3);

        System.out.println("Neo4J create HansWurst");
        long id2 = neo4J.createUser("HansWurst");
        System.out.println("Neo4J create HansWurst OK id: " +id2);
        System.out.println("Neo4J create communication");
        neo4J.createCommunication(id, id2);
        System.out.println("Neo4J create communication OK");
        System.out.println("Neo4J login");
        neo4J.loginUser(id,"1.2.3.4",50001);
        System.out.println("Neo4J login OK");
        System.out.println("Neo4J get UserIP");
        Map<String,String> map = neo4J.getUserIP(id);
        System.out.println("Neo4J get UserIP OK ip: " + map.get("ip") + " port: " + map.get("port"));


        neo4J.logoutUser(id);
        neo4J.saveMessage(id,message);
    }

}
