/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Application;
import domain.User;
import domain.Vote;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author loganbartosh
 */
public class CollectionDAO implements StorageInterface {

    private static Collection<Application> applications = new HashSet<>();
    private static Collection<User> users = new HashSet<>();
    
    @Override
    public void addApplication(Application application) {
        applications.add(application);
        application.setStatus("Application Added");
    }

    @Override
    //Adding a vote - which will be attached to an application via the application's ID
    public void addVote(Vote vote) {
        for (Application app : applications) {
            if(app.getApplicationID().equals(vote.getApplicationID())){
                app.addVote(vote);
                app.setStatus("Voting Underway");
            }
        }
    }
 

    @Override
    //Adding a user
    public void addUser(User user) {
        users.add(user);
    }
    
     @Override
    //Adding a user
    public User getUser(String id) {
        for(User user : users){
            if(user.getUserID().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    //When signing in, to check password matches one stored.
    public Boolean checkPassword(String userID, String password) {
        for(User user : users){
            if(user.getUserID().equals(userID)){
                if(user.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    //Retrieve Application
    public Application getApplication(String applicationID) {
        for (Application app : applications) {
            if(app.getApplicationID().equals(applicationID)){
                return app;
            }
        }
        return null;
    }

    @Override
    //Get all applications
    public Collection<Application> getApplications() {
        return applications;
    }

    @Override
    //Not sure if we need this functionality yet - so thought I'd wait until later
    //to implement it.
    public Collection<Application> getApplications(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    //Get the winning applicaition in the category
    public String winningApplication(String category) {
        //Get all applications in a certain category
        //Count the number of votes for each applicaition
        //Return the application with largest number
        //of votes
        int winningAppScore = 0;
        String winningAppID = "";
        for (Application app : applications) {
           app.setStatus("Voting Complete");
           if(app.calVote()>winningAppScore){
               winningAppScore = app.calVote();
               winningAppID = app.getApplicationID();
           }
        }
        return winningAppID;
    }
    
    @Override
    public Collection<String> getCategories(){
        return new ArrayList<String>();
    }
    
    @Override
    public Collection<Application> getApplications(String category, String department) {
        return new ArrayList<Application>();
    }
    
    @Override
    public Application getApplicationByUserID(String userID) {
         return new Application();
    }
    
    @Override
    public void updateApplication(Application appl) {
        
    }
}

