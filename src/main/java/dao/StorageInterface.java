/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Application;
import domain.User;
import domain.Vote;
import java.util.Collection;

/**
 *
 * @author loganbartosh
 */
public interface StorageInterface {
    
    //Adding an application
    void addApplication (Application application);
    
    //Adding a vote - which will be attached to an application via the application's ID
    void addVote (Vote vote);
    
    //Adding a user
    void addUser(User user);
    
    //Retrieving a yser
    User getUser(String id);
    
    //When signing in, to check password matches one stored.
    Boolean checkPassword (String userID, String password);
    
    //Retrieve Application
    Application getApplication(String applicationID);
    
    //Retrieve all applications
    Collection<Application> getApplications();
    
    //Retrieve all applicaitons of specific category
    Collection<Application> getApplications(String category);
    
    //Get the winning applicaition in the category
    String winningApplication(String category);

    //Get all the award categories
    Collection<String> getCategories();
    
    //Retrieve all applicaitons of specific category of a department
    Collection<Application> getApplications(String category, String department);
    
    Application getApplicationByUserID(String userID);
    
    void updateApplication(Application appl);
}
