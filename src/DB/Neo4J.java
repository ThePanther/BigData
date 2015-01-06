package DB;


import Data.Message;
import org.neo4j.cypher.internal.compiler.v2_0.functions.Str;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import javax.management.relation.Relation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Neo4J {

    private GraphDatabaseService graphDB;

    public Neo4J() {

    }

    public void init() {
        this.graphDB = new GraphDatabaseFactory().newEmbeddedDatabase("/home/nosql/neo4j-community-2.1.5/data/bigdata.db");
    }

    public long createUser(String user) {
        long id = 0;
        try (Transaction tx = graphDB.beginTx()){
            Node userNode = graphDB.createNode();
            userNode.setProperty("username",user);
            userNode.setProperty("ip","");
            userNode.setProperty("port",0);
            userNode.setProperty("online",false);
            id = userNode.getId();
            tx.success();
        }
        return id;
    }

    public boolean createCommunication(long fromUser, long toUser) {
        boolean res = false;
        try (Transaction tx = graphDB.beginTx()) {
            Node from = graphDB.getNodeById(fromUser);
            Node to = graphDB.getNodeById(toUser);
            from.createRelationshipTo(to, RelTypes.KNOWS);
            tx.success();
            res = true;
        }
        return res;
    }

    public boolean saveMessage(long user,Message message) {
        boolean res = false;
        try (Transaction tx = graphDB.beginTx()) {
            Node userNode = graphDB.getNodeById(user);
            Node messageNode = graphDB.createNode();
            messageNode.setProperty("from",message.getFromUser());
            messageNode.setProperty("to",message.getToUser());
            messageNode.setProperty("text",message.getText());
            userNode.createRelationshipTo(messageNode,RelTypes.SEND);
            tx.success();
            res = true;
        }
        return res;
    }

    public boolean loginUser(long id, String ip, int port) {
        boolean res = false;
        try (Transaction tx = graphDB.beginTx()) {
            Node userNode = graphDB.getNodeById(id);
            userNode.setProperty("ip",ip);
            userNode.setProperty("port",port);
            userNode.setProperty("online",true);
            tx.success();
            res = true;
        }
        return res;
    }

    public boolean logoutUser(long id) {
        boolean res = false;
        try (Transaction tx = graphDB.beginTx()) {
            Node userNode = graphDB.getNodeById(id);
            userNode.setProperty("online",false);
            tx.success();
            res = true;
        }
        return res;
    }

    public Map<String,String> getUserIP(long id) {
        Map<String,String> map = new HashMap<>();
        try (Transaction tx = graphDB.beginTx()){
            Node userNode = graphDB.getNodeById(id);
            if ((boolean) userNode.getProperty("online")) {
                map.put("ip",(String) userNode.getProperty("ip"));
                map.put("port",(""+(int)userNode.getProperty("port")));
            }
        }
        return map;
    }

    public ArrayList<String> getFriends(long id) {
        ArrayList<String> list = new ArrayList<>();
        try (Transaction tx = graphDB.beginTx()) {
            Node userNode = graphDB.getNodeById(id);
            Iterable<Relationship> relations = userNode.getRelationships(RelTypes.KNOWS);
            while (relations.iterator().hasNext()) {
                Relationship relationship = relations.iterator().next();
                Node friendNode = relationship.getOtherNode(userNode);
                list.add(friendNode.getProperty("username").toString());
            }
            tx.success();
        }
        return list;
    }

}


