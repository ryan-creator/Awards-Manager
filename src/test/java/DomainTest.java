/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class DomainTest {
    
    public DomainTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

     @Test
    public void applicationCreationTest(){
        Application application1 = new Application("app1", "1297732", "Student", "www.cvplaceholder.com", "Logan's personal statement", "References", "Status");
        assertEquals("app1", application1.getApplicationID());
        assertEquals("1297732", application1.getUserCandidate());
        assertEquals("Student", application1.getCategory());
        assertEquals("www.cvplaceholder.com", application1.getLinkToCV());
        assertEquals("Logan's personal statement", application1.getPersonalStatement());
        assertEquals("References", application1.getReferences());
        assertEquals("Status", application1.getStatus());
    }
    
    @Test
    public void modifyApplicationTest(){
        Application application1 = new Application("app1", "1297732", "Student", "www.cvplaceholder.com", "Logan's personal statement", "References", "Status");
        application1.setReferences("changedreference");
        assertEquals("app1", application1.getApplicationID());
        assertEquals("1297732", application1.getUserCandidate());
        assertEquals("Student", application1.getCategory());
        assertEquals("www.cvplaceholder.com", application1.getLinkToCV());
        assertEquals("Logan's personal statement", application1.getPersonalStatement());
        assertEquals("changedreference", application1.getReferences());
        assertEquals("Status", application1.getStatus());
    }
    
    @Test
    public void userCreationTest(){
        User user1 = new User ("StaffUserID", "Committee Name", "last name", "test@test.com", "99999", "COSC", "Test", "testpassword");
        assertEquals("StaffUserID", user1.getUserID());
        assertEquals("Committee Name", user1.getFirstName());
        assertEquals("last name", user1.getLastName());
        assertEquals("test@test.com", user1.getEmail());
        assertEquals("99999", user1.getStaffID());
        assertEquals("COSC", user1.getDepartment());
        assertEquals("Test", user1.getUserType());
        assertEquals("testpassword", user1.getPassword());
    }
    
    @Test
    public void modifyUserTest(){
        User user1 = new User ("StaffUserID", "Committee Name", "last name", "test@test.com", "99999", "COSC", "Test", "testpassword");
        user1.setDepartment("Information Science");
        assertEquals("StaffUserID", user1.getUserID());
        assertEquals("Committee Name", user1.getFirstName());
        assertEquals("last name", user1.getLastName());
        assertEquals("test@test.com", user1.getEmail());
        assertEquals("99999", user1.getStaffID());
        assertEquals("Information Science", user1.getDepartment());
        assertEquals("Test", user1.getUserType());
        assertEquals("testpassword", user1.getPassword());
    }
    
    @Test
    public void voteCreationTest(){
        Vote vote1 = new Vote("vote1207732a", "committeeUser1", "app1", "Vote has been placed for temp reasons");
        assertEquals("vote1207732a", vote1.getVoteID());
        assertEquals("committeeUser1", vote1.getUserID());
        assertEquals("app1", vote1.getApplicationID());
        assertEquals("Vote has been placed for temp reasons", vote1.getComment());
    }
    @Test
    public void modifyVoteTest(){
        Vote vote1 = new Vote("vote1207732a", "committeeUser1", "app1", "Vote has been placed for temp reasons");
        vote1.setApplicationID("app2");
        assertEquals("vote1207732a", vote1.getVoteID());
        assertEquals("committeeUser1", vote1.getUserID());
        assertEquals("app2", vote1.getApplicationID());
        assertEquals("Vote has been placed for temp reasons", vote1.getComment());
        
        
    }

    
}
