/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.gson.JsonObject;
import dao.StorageInterface;
import domain.Application;
import domain.User;
import java.util.Random;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author Ubaada
 */
public class UserModule extends Jooby {

    /*
Route                                   Method  Use     Auth.
----------------------------------------------------------------
api/user/                               [POST]  (D)     Dep(Only Nom)
api/user/login (return session-token)   [POST]  (A)     All
api/user/logout                         [POST]  All     All
     */
    public UserModule(StorageInterface dbDAO, SessionManager sessionManager) {
        //Nominee account created by department account
        post("/api/user", (req, rsp) -> {
            User loggedUser = sessionManager.getUser(req);
            if (loggedUser.getUserType()
                    .equals("Department")) {
                Nomination nom = req.body().to(Nomination.class);
                User newUserAccount = new User(nom.first_name + "-" + nom.last_name, nom.first_name,
                        nom.last_name, nom.email, loggedUser.userID, loggedUser.getDepartment(), "Nominee", generatePassword());
                Application newAppl = new Application(generatePassword(),
                        newUserAccount.getUserID(), nom.category, "", "", "", "Pending");
                dbDAO.addUser(newUserAccount);
                dbDAO.addApplication(newAppl);
                rsp.status(Status.CREATED);
            } else {
                rsp.status(Status.UNAUTHORIZED);
            }
        }
        );

        //for loging in, user_id & password gets posted.
        //if the combination is correct: a token is generated using sessionManager
        //And sent to back to the webpage to store in a cookie for further requests.
        post("/api/user/login", (req, rsp) -> {
            Credentials cred = req.body().to(Credentials.class
            );
            if (dbDAO.checkPassword(cred.user_id, cred.password)) {
                //delete old session cookies if any exist
                sessionManager.deleteSession(req);

                User user = dbDAO.getUser(cred.user_id);
                String token = sessionManager.issueToken(user);
                //send part of user's details
                JsonObject userDetails = new JsonObject();
                userDetails.addProperty("firstName", user.getFirstName());
                userDetails.addProperty("lastName", user.getLastName());
                userDetails.addProperty("department", user.getDepartment());
                userDetails.addProperty("userType", user.getUserType());
                userDetails.addProperty("userID", user.getUserID());
                rsp.cookie("session-token", token).send(userDetails);
                rsp.status(Status.OK);
            } else {
                rsp.status(Status.BAD_REQUEST).send("Wrong pw or userID");
            }
        });

        //logout, delete session from sessionManager and delete the corresponding
        //token cookie on client's end
        post("/api/user/logout", (req, rsp) -> {
            boolean del = sessionManager.deleteSession(req);
            if (del == true) {
                rsp.status(Status.OK).send("Logged out");
            } else {
                rsp.status(Status.BAD_REQUEST);
            }
            rsp.clearCookie("session-token");
        });

    }

    //for generating passwords
    public static String generatePassword() {
        return new Random().ints(10, 33, 122).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /* for parsing user_id and password sent as json object */
    public class Credentials {

        public String user_id;
        public String password;
    }
//this class is partially user account details and partially  applcation

    public class Nomination {

        public String first_name;
        public String last_name;
        public String email;
        public String category;

        @Override
        public String toString() {
            return "Nomination{" + "first_name=" + first_name + ", last_name=" + last_name + ", email=" + email + ", category=" + category + '}';
        }

       
    }
}
