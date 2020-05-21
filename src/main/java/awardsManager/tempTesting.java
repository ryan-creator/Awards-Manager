/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awardsManager;

import dao.CollectionDAO;
import dao.StorageInterface;
import domain.Application;
import domain.User;
import domain.Vote;

/**
 * JUST A CLASS FOR TEMP TESTING FOR THE DAOs
 *
 * @author loganbartosh
 */
public class tempTesting {
    private static StorageInterface dao = new CollectionDAO();
    
    public static void main(String[] args) {
        //Create some applications
        Application application1 = new Application("app1", "1297732", "Student", "www.cvplaceholder.com", "Logan's personal statement", "status", "References");
        dao.addApplication(application1);
        Application application2 = new Application("app2", "7777777", "Teacher", "www.cvplaceholder.com", "John Doe's personal statement", "status", "References");
        Application application3 = new Application("app3", "5555555", "Student", "www.cvplaceholder.com", "Jane Doe's personal statement", "status", "References");
        dao.addApplication(application2);
        dao.addApplication(application3);
        
        Vote vote1207732a = new Vote("vote1207732a", "committeeUser1", "app1", "Vote has been placed for temp reasons");
        Vote vote1207732b = new Vote("vote1207732b", "committeeUser1", "app1", "Vote has been placed for temp reasons");
        Vote vote1207732c = new Vote("vote1207732c", "committeeUser1", "app1", "Vote has been placed for temp reasons");
        
        Vote vote5555555b = new Vote("vote5555555b", "committeeUser1", "app3", "Vote has been placed for temp reasons");
        
        dao.addVote(vote1207732a);
        dao.addVote(vote1207732b);
        dao.addVote(vote1207732c);
        dao.addVote(vote5555555b);
        
        //Check everything is created correctly
        System.out.println("CHECKING APPLICATIONS HAVE BEEN CREATED CORRECTLY");
        System.out.println(application1.toString());
        System.out.println(application2.toString());
        System.out.println(application3.toString());
        //Checking everything is being added to the DAO correctly
        System.out.println("CHECKING APPLICATIONS ADDED TO DAO CORRECTLY");
        System.out.println(dao.getApplication("app1").toString());
        System.out.println(dao.getApplication("app2").toString());
        System.out.println(dao.getApplication("app3").toString());
        
        
        
        //Should print out the id app1
        System.out.println("CHECKING CALCULATION OF SCORE CORRECTLY");
        System.out.println(dao.winningApplication("Student"));
        
        
        
        //Testing Users
        System.out.println("CHECKING USER WORKS CORRECTLY");
        User user1 = new User ("StaffUserID", "Committee Name", "last name", "test@test.com", "99999", "COSC", "Test", "testpassword");
        System.out.println(user1.toString());
        dao.addUser(user1);
        System.out.println(dao.getUser("StaffUserID").toString());
        System.out.println("Should print out false as password incorrect");
        System.out.println(dao.checkPassword("StaffUserID", "test"));
        System.out.println("Should print out true as password correct");
        System.out.println(dao.checkPassword("StaffUserID", "testpassword"));
        
        
        
        
        
    }
}










