package DB;


import Data.Login;
import Data.Message;
import Data.Registration;
import Data.Usersearch;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MongoDB {

    private static MongoDB instance;
    private MongoClient mongoClient;
    private DB db;
    private DBCollection dbCollectionUser;
    private DBCollection dbCollectionMessage;

    public MongoDB() {

    }

    public static MongoDB getInstance () {
        if (MongoDB.instance == null) {
            MongoDB.instance = new MongoDB ();
        }
        return MongoDB.instance;
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

    public boolean register(Registration registration, long id) {
        boolean res = false;
        BasicDBObject bdO = new BasicDBObject("user",registration.getUserName());
        DBObject myDoc = dbCollectionUser.findOne(bdO);
        if ((myDoc != null)&&(myDoc.get("user").toString().equals(registration.getUserName()))) {
            return res;
        } else {
            Map<String,String> map = new HashMap<String, String>();
            map.put("user",registration.getUserName());
            map.put("password",registration.getPassword());
            map.put("id",""+id);
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
        if ((myDoc != null)&&(myDoc.get("password").toString().equals(login.getPassword()))) {
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
        if ((myDoc != null)&&(myDoc.get("user").toString().equals(usersearch.getUser()))) {
            res = true;
        }
        return res;
    }

    public long getUserNodeID(String user) {
        long id = -1;
        BasicDBObject basicDBObject = new BasicDBObject("user",user);
        DBObject myDoc = dbCollectionUser.findOne(basicDBObject);
        if (myDoc != null) {
             id = Long.parseLong(myDoc.get("id").toString());
        }
        return id;
    }

    public Registration getUser (String username) {
        Registration registration = null;
        BasicDBObject basicDBObject = new BasicDBObject("user",username);
        DBObject myDoc = dbCollectionUser.findOne(basicDBObject);
        if (myDoc != null) {

            registration = new Registration(myDoc.get("mail").toString(),myDoc.get("user").toString(),myDoc.get("password").toString(),"localhost",0);
            registration.setJob("");//myDoc.get("job").toString());
            registration.setAddress("");//myDoc.get("address").toString());
            registration.setBirthDate(myDoc.get("birthdate").toString());
            registration.setSex(myDoc.get("sex").toString());

        }
        return registration;
    }

    public ArrayList<String> messageContained (String text) {
        ArrayList<String> list = new ArrayList<>();
        BasicDBObject basicDBObject = new BasicDBObject("text",text);
        DBCursor cursor = dbCollectionMessage.find(basicDBObject);
        while (cursor.hasNext()) {
            DBObject myDoc = cursor.next();
            list.add(myDoc.get("from").toString());
        }
        return list;
    }

}
