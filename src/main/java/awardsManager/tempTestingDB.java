/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awardsManager;

import dao.DbDAO;
import dao.StorageInterface;
import domain.Application;
import domain.User;
import domain.Vote;

/**
 * JUST A CLASS FOR TEMP TESTING FOR THE DAOs
 *
 * @author loganbartosh
 */
public class tempTestingDB {
    private static StorageInterface dao = new DbDAO();
    
    public static void main(String[] args) {
        //First, let's create a user so we can create applications for that user.
        
        User user1 = new User ("StaffUserID", "Logan", "Bartosh", "barlo584@student.otago.ac.nz", "1207732", "COSC", "Applicant", "testpassword");
        System.out.println("Let's check the user got created properly in the domain");
        System.out.println(user1.toString());
        dao.addUser(user1);
        
        System.out.println("Let's check the user got created properly in the database");
        System.out.println(dao.getUser("StaffUserID").toString());
        
        //Now, we can create an application. Let's do this:
        
        Application app1 = new Application ("app1", "1207732", "CompSci", "www.cvsonline.com", "I am a good applicant...", "Daniel - reference", "Pending");
        System.out.println("Let's check the application got created properly in the domain");
        System.out.println(app1.toString());
        dao.addApplication(app1);
        
        System.out.println("Let's check the application got created properly in the database");
        System.out.println(dao.getApplication("app1").toString());
        
        //Now, let's add a vote to the application
        
        User user2 = new User ("CommittieMemberID", "Logan", "Bartosh", "barlo584@student.otago.ac.nz", "1207732c", "COSC", "Committie", "testpassword");
        dao.addUser(user2);
        Vote vote1 = new Vote ("1", "CommittieMemberID", "app1", "This person is a great applicant");
        System.out.println(vote1.toString());
        dao.addVote(vote1);
        
        //Let's testing the password check:
        System.out.println("Should be false: " + dao.checkPassword("StaffUserID", "password"));
        System.out.println("Should be true: " + dao.checkPassword("StaffUserID", "testpassword"));
        
        //Check getting application is okay:
        System.out.println(dao.getApplication("app1"));
        
        dao.winningApplication("x");
        
        
        
        
        
        
        
        
    }
}































