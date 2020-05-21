/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package dao;

import domain.Application;
import domain.User;
import domain.Vote;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author loganbartosh
 */
public class DbDAO implements StorageInterface {

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

    @Override
    public void addApplication(Application application) {
        String sql = "INSERT into Application (applicationID, userCandidate, category, linkToCV, personalStatement, candidateReferences, status) values (?,?,?,?,?,?,?)";

        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, application.getApplicationID());
            stmt.setString(2, application.getUserCandidate());
            stmt.setString(3, application.getCategory());
            stmt.setString(4, application.getLinkToCV());
            stmt.setString(5, application.getPersonalStatement());
            stmt.setString(6, application.getReferences());
            stmt.setString(7, application.getStatus());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        //Using the other method in here to add the votes if we're re-adding an application.
        for (Vote vote : application.getVoteList()) {
            addVote(vote);
        }
    }

    @Override
    public void addVote(Vote vote) {
        String sql = "insert into Vote (voteID, userID, applicationID, comment) values (?,?,?,?)";

        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, vote.getVoteID());
            stmt.setString(2, vote.getUserID());
            stmt.setString(3, vote.getApplicationID());
            stmt.setString(4, vote.getComment());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT into User (userID, firstName, lastName, email, staffID, department, userType, password) values (?,?,?,?,?,?,?,?)";

        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, user.getUserID());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getStaffID());
            stmt.setString(6, user.getDepartment());
            stmt.setString(7, user.getUserType());
            stmt.setString(8, user.getPassword());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public User getUser(String id) {
        String sql = "select * from User where userID=?";
        User u = null;
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();  // execute the statement
            while (rs.next()) {   //Iterate through and obtain the required infromation for the user
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

    @Override
    public Boolean checkPassword(String ID, String passwd) {
        String sql = "select * from User where userID=?";
        Boolean authenticate = false;
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();  // execute the statement
            String password = rs.getString("password");
            if (password.equals(passwd)) {
                authenticate = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return authenticate;
    }

    @Override
    public Application getApplication(String appID) {
        String sql = "select * from Application where applicationID=?";
        Application a = null;
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, appID);
            ResultSet rs = stmt.executeQuery();  // execute the statement
            while (rs.next()) {   //Iterate through and obtain the required infromation for the user
                String applicationID = rs.getString("applicationID");
                String userCandidate = rs.getString("userCandidate");
                String category = rs.getString("category");
                String linkToCV = rs.getString("linkToCV");
                String personalStatement = rs.getString("personalStatement");
                String references = rs.getString("candidateReferences");
                String status = rs.getString("status");
                a = new Application(applicationID, userCandidate, category, linkToCV, personalStatement, references, status);    //Create Application object with information
                a.setVotes(getVotes(appID));
            }
            return a;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null; // should never get to here, unless there's no applications.
    }

    public Collection<Vote> getVotes(String appID) {
        String sql = "SELECT * FROM Vote WHERE applicationID = ?";
        Collection<Vote> votes = new ArrayList<>();
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, appID);
            ResultSet rs = stmt.executeQuery();  // execute the statement
            while (rs.next()) {   //Iterate through and obtain the required infromation for the user
                String voteID = rs.getString("voteID");
                String userID = rs.getString("userID");
                String applicationID = rs.getString("applicationID");
                String comment = rs.getString("comment");
                Vote tempVote = new Vote(voteID, userID, applicationID, comment);
                votes.add(tempVote);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return votes;

    }

    @Override
    public Collection<Application> getApplications() {
        String sql = "SELECT * FROM Application ORDER BY applicationID";
        Collection<Application> applications = new ArrayList<>();
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {   //Iterate through and obtain the required infromation for the user
                String applicationID = rs.getString("applicationID");
                String userCandidate = rs.getString("userCandidate");
                String category = rs.getString("category");
                String linkToCV = rs.getString("linkToCV");
                String personalStatement = rs.getString("personalStatement");
                String references = rs.getString("references");
                String status = rs.getString("status");
                Application a = new Application(applicationID, userCandidate, category, linkToCV, personalStatement, references, status);    //Create Application object with information
                a.setVotes(getVotes(applicationID));
                applications.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return applications;
    }

    @Override
    public Collection<Application> getApplications(String categoryChosen) {
        String sql = "SELECT * FROM Application WHERE category = ?";
        Collection<Application> applications = new ArrayList<>();
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, categoryChosen);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {   //Iterate through and obtain the required infromation for the user
                String applicationID = rs.getString("applicationID");
                String userCandidate = rs.getString("userCandidate");
                String category = rs.getString("category");
                String linkToCV = rs.getString("linkToCV");
                String personalStatement = rs.getString("personalStatement");
                String references = rs.getString("candidateReferences");
                String status = rs.getString("status");
                Application a = new Application(applicationID, userCandidate, category, linkToCV, personalStatement, references, status);    //Create Application object with information
                a.setVotes(getVotes(applicationID));
                applications.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return applications;
    }

    @Override
    public String winningApplication(String category) {
        //Get a list of application IDs within category.
        //Iterate throught list, find the one with most votes.

        String sql = "SELECT COUNT(*) FROM application WHERE category = ?";
        int numOfApps = 0;
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {   //Iterate through and obtain the required infromation for the user
                numOfApps = Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        String[] id = new String[numOfApps];
        int[] count = new int[numOfApps];

        return null;
    }

    @Override
    public Collection<String> getCategories() {
//        String sql = "SELECT * FROM Awards";
//        Collection<String> categories = new ArrayList<>();
//        try (Connection dbCon = this.connect();
//                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {   //Iterate through and obtain the required infromation for the user
//                String caetegory = rs.getString("Award");
//                categories.add(caetegory);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
        //return categories;
        ArrayList<String> placeholder = new ArrayList<String>();
        placeholder.add("Best-Teacher");
        placeholder.add("Best-Researcher");
        placeholder.add("Best-Lab-Instructor");
        return placeholder;
    }

    @Override
    public Collection<Application> getApplications(String categoryChosen, String department) {
        String sql = "Select * From User INNER JOIN Application on User.userID = Application.userCandidate "
                + "where category= ? AND department = ?";
        Collection<Application> applications = new ArrayList<>();
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, categoryChosen);
            stmt.setString(2, department);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {   //Iterate through and obtain the required infromation for the user
                String applicationID = rs.getString("applicationID");
                String userCandidate = rs.getString("userCandidate");
                String category = rs.getString("category");
                String linkToCV = rs.getString("linkToCV");
                String personalStatement = rs.getString("personalStatement");
                String references = rs.getString("candidateReferences");
                String status = rs.getString("status");
                Application a = new Application(applicationID, userCandidate, category, linkToCV, personalStatement, references, status);    //Create Application object with information
                a.setVotes(getVotes(applicationID));
                applications.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return applications;
    }

    @Override
    public Application getApplicationByUserID(String userID) {
        String sql = "select * from Application where userCandidate=?";
        Application a = null;
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();  // execute the statement
            while (rs.next()) {   //Iterate through and obtain the required infromation for the user
                String applicationID = rs.getString("applicationID");
                String userCandidate = rs.getString("userCandidate");
                String category = rs.getString("category");
                String linkToCV = rs.getString("linkToCV");
                String personalStatement = rs.getString("personalStatement");
                String references = rs.getString("candidateReferences");
                String status = rs.getString("status");
                a = new Application(applicationID, userCandidate, category, linkToCV, personalStatement, references, status);    //Create Application object with information
                a.setVotes(getVotes(applicationID));
            }
            return a;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null; // should never get to here, unless there's no applications.

    }
    
    @Override
    public void updateApplication(Application application) {
        String sql = "UPDATE Application SET applicationID=?, userCandidate=?, category=?, linkToCV=?, personalStatement=?, candidateReferences=?, status=? WHERE applicationID = ?";
        
        try (Connection dbCon = this.connect();
                PreparedStatement stmt = dbCon.prepareStatement(sql);) {    // copy the data from the student domain object into the SQL parameters
            stmt.setString(1, application.getApplicationID());
            stmt.setString(2, application.getUserCandidate());
            stmt.setString(3, application.getCategory());
            stmt.setString(4, application.getLinkToCV());
            stmt.setString(5, application.getPersonalStatement());
            stmt.setString(6, application.getReferences());
            stmt.setString(7, application.getStatus());
            
            stmt.setString(8, application.getApplicationID());
            System.out.println(" ----> " + stmt.toString());
            stmt.executeUpdate();  // execute the statement

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        //Using the other method in here to add the votes if we're re-adding an application.
        for (Vote vote : application.getVoteList()) {
            addVote(vote);
        }
    }
}
