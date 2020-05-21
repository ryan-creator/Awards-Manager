/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;




import dao.DbDAO;
import dao.StorageInterface;

import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;

/**
 *
 * @author Ubaada
 */
public class Server extends Jooby {
    StorageInterface dbDAO = new DbDAO();
    SessionManager sessionManager = new SessionManager();

    public static void main(String[] args) throws Exception {
        
        Server server = new Server();
        server.port(8081);
        
        //uncomment this line if multithreading doesn't work  (closes upon start up) on your IDE
        //server.start();
        
        //start server
        CompletableFuture.runAsync(() -> {
                server.start();
        });

        server.onStarted(() -> {
                System.out.println("\nServer ready. Press Enter to stop service.");
        });

        System.in.read();
        System.exit(0);
    }
    
    public Server() {
        //bind all modules
        use(new Gzon());
        use(new ApplicationModule(dbDAO, sessionManager));
        use(new UserModule(dbDAO, sessionManager));
        use(new VoteModule(dbDAO,sessionManager));
        use(new AssetModule());
    }
}
