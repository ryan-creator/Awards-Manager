/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import domain.User;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import org.jooby.Cookie;
import org.jooby.Request;

/**
 *
 * @author Ubaada
 */
public class SessionManager {
    //to generate random string as a token
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    //session token and their users
    private HashMap<String, User> sessions = new HashMap<String, User>();

    //check if the session token is for a valid session
    public boolean isSessionValid(String token) {
        return sessions.containsKey(token);
    }
    
    //check if the session token in the request is for a valid session
    public boolean isSessionValid(Request req) {
        String token = req.cookie("session-token").value();
        return isSessionValid(token);
    }
    
    //for logging out
    public void deleteSession(String token) {
        System.out.println("del--> " + token);
        sessions.remove(token);
    }
    public boolean deleteSession(Request req) {
        for (Cookie c : req.cookies()) {
            if (c.name().equals("session-token") && isSessionValid(req.cookie("session-token").value())) {
                String token = req.cookie("session-token").value();
                
                deleteSession(token);
                return true;
            }
        }
        return false;
    }
    
    //get user associated with the token
    public User getUser(String token) {
        if (isSessionValid(token)) {
            return sessions.get(token);
        } else {
            //session not valid return an empty user
            return new User("", "", "", "", "", "", "", "");
        }
    }
    
     //get user associated with the token in the request
    public User getUser(Request req) {
        for (Cookie c : req.cookies()) {
            if (c.name().equals("session-token")) {
                String token = req.cookie("session-token").value();
                return this.getUser(token);
            }
        }
        //cookie doesn't exist
        return this.getUser("");
        
    }
    
    //generate a random token, put it in (token,User) pair list
    //the (token, User) pair list is used to get User associated with logged in session.
    public String issueToken(User user) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String token = base64Encoder.encodeToString(randomBytes);
        sessions.put(token, user);
        System.out.println(sessions.toString());
        return token;
    }
}
