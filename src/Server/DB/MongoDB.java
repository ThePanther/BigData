package Server.DB;


import Data.Login;
import Data.Message;
import Data.Registration;
import Data.Usersearch;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class MongoDB {

    private MongoClient mongoClient;
    private DB db;
    private DBCollection dbCollectionUser;
    private DBCollection dbCollectionMessage;

    public MongoDB() {

    }

    public void init(){
        try {
            this.mongoClient = new MongoClient("localhost", 27017);
            this.db = mongoClient.getDB ("bigdata");
            this.dbCollectionUser = db.getCollection("user");
            this.dbCollectionMessage = db.getCollection("message");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public boolean register(Registration registration) {
        boolean res = false;
        BasicDBObject bdO = new BasicDBObject("user",registration.getUserName());
        DBObject myDoc = dbCollectionUser.findOne(bdO);
        if (myDoc.get("user").toString().equals(registration.getUserName())) {
            return res;
        } else {
            Map<String,String> map = new HashMap<String, String>();
            map.put("user",registration.getUserName());
            map.put("password",registration.getPassword());
            map.put("address",registration.getAddress());
            map.put("mail",registration.geteMail());
            map.put("birthdate",registration.getBirthDate());
            map.put("job",registration.getJob());
            map.put("sex",registration.getSex());
            dbCollectionUser.insert(new BasicDBObject(map));
            res = true;
        }
        return res;
    }

    public boolean login(Login login) {
        boolean res = false;
        BasicDBObject bdO = new BasicDBObject("user",login.getUserName());
        DBObject myDoc = dbCollectionUser.findOne(bdO);
        if (myDoc.get("password").toString().equals(login.getPassword())) {
            res = true;
        }
        return res;
    }

    public void saveMessage(Message message) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("from",message.getFromUser());
        map.put("to",message.getToUser());
        map.put("text",message.getText());
        map.put("timestamp",""+System.currentTimeMillis());
        dbCollectionMessage.insert(new BasicDBObject(map));
    }

    public boolean searchUser(Usersearch usersearch) {
        boolean res = false;
        BasicDBObject basicDBObject = new BasicDBObject("user",usersearch.getUser());
        DBObject myDoc = dbCollectionUser.findOne(basicDBObject);
        if (myDoc.get("user").toString().equals(usersearch.getUser())) {
            res = true;
        }
        return res;
    }

}
