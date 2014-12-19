import Data.Login;
import Data.Message;
import Data.Registration;
import Data.Usersearch;
import Server.DB.MongoDB;

public class Main {

    public static void main(String[] args) {
        MongoDB mongoDB = new MongoDB();
        System.out.println("init");
        mongoDB.init();
        System.out.println("init OK");
        System.out.println("registrierung");
        Registration registration = new Registration("test@gmx.de","testUser","abc");
        registration.setAddress("");
        registration.setBirthDate("");
        registration.setJob("");
        registration.setSex("");
        boolean reg = mongoDB.register(registration);
        System.out.println("registrierung " + reg);
        System.out.println("login");
        boolean log = mongoDB.login(new Login("testUser","abc"));
        System.out.println("login " + log);
        System.out.println("search User");
        boolean search = mongoDB.searchUser(new Usersearch("testUser"));
        System.out.println("search User " + search);
        System.out.println("save Message");
        mongoDB.saveMessage(new Message("testUser","HansWurst","hallo dies ist ein test"));
        System.out.println("save Message OK");

    }

}
