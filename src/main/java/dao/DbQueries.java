package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import domain.Application;
import domain.User;
import domain.Vote;


public class DbQueries {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/main/resources/Database/AMDatabase.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void addApplication(Application application) {
        String sql = "merge into Application (applicationID, userCandidate, category, linkToCV, personalStatement, references) values (?,?,?,?,?,?)";
        
        try (   Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, application.getApplicationID());
            stmt.setString(2, application.getUserCandidate());
            stmt.setString(3, application.getCategory());
            stmt.setString(4, application.getLinkToCV());
            stmt.setString(5, application.getPersonalStatement());
            stmt.setString(6, application.getReferences());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void addVote(Vote vote) {
        String sql = "merge into Vote (voteID, userID, applicationID, comment) values (?,?,?,?)";
        
        try (   Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, vote.getVoteID());
            stmt.setString(2, vote.getUserID());
            stmt.setString(3, vote.getApplicationID());
            stmt.setString(4, vote.getComment());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addUser(User user){
        String sql = "merge into Vote (userID, firstName, lastName, email, staffID, department, password) values (?,?,?,?,?,?,?)";
        
        try (   Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, user.getUserID());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getStaffID());
            stmt.setString(6, user.getDepartment());
            stmt.setString(7, user.getPassword());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }

    public User getUser(String ID){
        String sql = "select * from User where userID=?";
        User u = null;
        try (   Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();  // execute the statement
             while(rs.next()){   //Iterate through and obtain the required infromation for the user
                String userID = rs.getString("userID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String staffID = rs.getString("staffID");
                String department = rs.getString("department");
                String userType = rs.getString("userType");
                String password = rs.getString("password");
                u = new User(userID, firstName, lastName, email, staffID, department, userType, password);    //Create user object with information
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    public Boolean checkPassword (String ID, String passwd){
        String sql = "select * from User where userID=?";
        Boolean authenticate = false;
        try (   Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();  // execute the statement
            String password = rs.getString("password");
            if(password.equals(passwd)){
                authenticate = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
        return authenticate;
    }

    public Application getApplication(String ID){
        String sql = "select * from Application where applicationID=?";
        Application a = null;
        try (   Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();  // execute the statement
            while(rs.next()){   //Iterate through and obtain the required infromation for the user
                String applicationID = rs.getString("applicationID");
                String userCandidate = rs.getString("userCandidate");
                String category = rs.getString("category");
                String linkToCV = rs.getString("linkToCV");
                String personalStatement = rs.getString("personalStatement");
                String references = rs.getString("references");
                a = new Application(applicationID, userCandidate, category, linkToCV, personalStatement, references, "supressing error");    //Create Application object with information
            }
        return null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return a;
    }

    public Collection<Application> getApplications(){
        String sql = "SELECT * FROM Application ORDER BY applicationID";
        Collection<Application> applications = new ArrayList<>();
        try (   Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {    // copy the data from the student domain object into the SQL parameters
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){   //Iterate through and obtain the required infromation for the user
                String applicationID = rs.getString("applicationID");
                String userCandidate = rs.getString("userCandidate");
                String category = rs.getString("category");
                String linkToCV = rs.getString("linkToCV");
                String personalStatement = rs.getString("personalStatement");
                String references = rs.getString("references");
                Application a = new Application(applicationID, userCandidate, category, linkToCV, personalStatement, references, "supressing error");    //Create Application object with information
                applications.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return applications;
    }

    public Collection<Application> getApplications(String categoryChosen){
        String sql = "SELECT * FROM Application WHERE category = ";
        Collection<Application> applications = new ArrayList<>();
        try (   Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) 
            {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, categoryChosen);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){   //Iterate through and obtain the required infromation for the user
                String applicationID = rs.getString("applicationID");
                String userCandidate = rs.getString("userCandidate");
                String category = rs.getString("category");
                String linkToCV = rs.getString("linkToCV");
                String personalStatement = rs.getString("personalStatement");
                String references = rs.getString("references");
                Application a = new Application(applicationID, userCandidate, category, linkToCV, personalStatement, references, "surpressing error");    //Create Application object with information
                applications.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return applications;
    }

//    String winningApplication(String category){           NOT DONE 
//        String sql = "SELECT * FROM Application WHERE category = ";
//        Collection<Application> applications = new ArrayList<>();
//        try (   Connection dbCon = this.connect();
//                PreparedStatement stmt = dbCon.prepareStatement(sql);) 
//            {    // copy the data from the student domain object into the SQL parameters
//            stmt.setString(1, categoryChosen);
//            ResultSet rs = stmt.executeQuery();
//            while(rs.next()){   //Iterate through and obtain the required infromation for the user
//                String applicationID = rs.getString("applicationID");
//                String userCandidate = rs.getString("userCandidate");
//                String category = rs.getString("category");
//                String linkToCV = rs.getString("linkToCV");
//                String personalStatement = rs.getString("personalStatement");
//                String references = rs.getString("references");
//                Application a = new Application(applicationID, userCandidate, category, linkToCV, personalStatement, references);    //Create Application object with information
//                applications.add(a);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return applications;
//    }
}

