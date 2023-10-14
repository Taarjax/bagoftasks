package bagoftasks.server;
import bagoftasks.proto.*;
import java.rmi.*;

public class Server {
    public static void main(String[] args) {
        try {
            ImplBagOfTasks bot = new ImplBagOfTasks();
            Naming.rebind("bot", bot);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
