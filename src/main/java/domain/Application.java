/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author sam-7
 */
public class Application {

    public String applicationID;
    private String userCandidate;
    private String category;
    private String linkToCV;
    private String personalStatement;
    private String references;
    private String status = "Pending";
    private Collection<Vote> voteList = new ArrayList<>();

    public Application(String applicationID, String userCandidate, String category, String linkToCV, String personalStatement, String references, String status) {
        this.applicationID = applicationID;
        this.userCandidate = userCandidate;
        this.category = category;
        this.linkToCV = linkToCV;
        this.personalStatement = personalStatement;
        this.references = references;
        this.status = status;
    }

    public Application() {
    }
    
    
    
    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getUserCandidate() {
        return userCandidate;
    }

    public void setUserCandidate(String userCandidate) {
        this.userCandidate = userCandidate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLinkToCV() {
        return linkToCV;
    }

    public void setLinkToCV(String linkToCV) {
        this.linkToCV = linkToCV;
    }

    public String getPersonalStatement() {
        return personalStatement;
    }

    public void setPersonalStatement(String personalStatement) {
        this.personalStatement = personalStatement;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public int calVote() {
        return voteList.size();
    }

    public void addVote(Vote vote) {
        this.voteList.add(vote);
        
    }
    
    public void setVotes(Collection<Vote> votes){
        this.voteList = votes;
    }

    public Collection<Vote> getVoteList() {
        return voteList;
    }
    
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application{" + "applicationID=" + applicationID + ", userCandidate=" + userCandidate + ", category=" + category + ", linkToCV=" + linkToCV + ", personalStatement=" + personalStatement + ", references=" + references + ", status=" + status + ", voteList=" + voteList + '}';
    }
    
    
}










