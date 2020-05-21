/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.CollectionDAO;
import dao.StorageInterface;
import domain.Application;
import domain.User;
import domain.Vote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author loganbartosh
 */
public class CollectionDAOTest {
    
    public CollectionDAOTest() {
    }
    
    //StorageInterface dao;
    
    @Before
    public void setUp() {
        //dao = new CollectionDAO();
    }
    
    @After
    public void tearDown() {
        //dao = null;
    }
    
    @Test
    public void addingApplicationTest(){
        StorageInterface dao = new CollectionDAO();
        Application application1 = new Application("app1", "1297732", "Student", "www.cvplaceholder.com", "Logan's personal statement", "status", "References");
//        assertEquals(0, dao.getApplications().size());
        dao.addApplication(application1);
//        assertEquals(1, dao.getApplications().size());
        assertEquals("app1", dao.getApplication("app1").getApplicationID());
        Application application2 = new Application("app1", "1297732", "Student", "www.cvplaceholder.com", "Logan's personal statement", "status", "References");
        dao.addApplication(application2);
//        assertEquals(2, dao.getApplications().size());
        assertEquals("app1", dao.getApplication("app1").getApplicationID()); //Checking that it still returns just app1
    }
    
    @Test
    public void addingUserTest(){
        StorageInterface dao = new CollectionDAO();
        User user1 = new User ("StaffUserID", "Committee Name", "last name", "test@test.com", "99999", "COSC", "Test", "testpassword");
        dao.addUser(user1);
        assertEquals("StaffUserID", dao.getUser("StaffUserID").getUserID());
        assertTrue(dao.checkPassword("StaffUserID", "testpassword"));
    }
    
    @Test
    public void addingVoteTest(){
        StorageInterface dao = new CollectionDAO();
        //Adding application first
        Application application1 = new Application ("app1", "1207732", "CompSci", "www.cvsonline.com", "I am a good applicant...", "Daniel - reference", "Pending");
        assertEquals(0, dao.getApplications().size());
        dao.addApplication(application1);
        //Add user for application to reference
        User user1 = new User ("StaffUserID", "Logan", "Bartosh", "barlo584@student.otago.ac.nz", "1207732", "COSC", "Applicant", "testpassword");
        dao.addUser(user1);
        assertEquals("StaffUserID", dao.getUser("StaffUserID").getUserID());
        //Now add the vote
        Vote vote1 = new Vote("vote1", "committeeUser1", "app1", "Vote has been placed for temp reasons");
        dao.addVote(vote1);
        assertEquals("app1", dao.winningApplication("CompSci"));
    }

}
