/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author sam-7
 */
public class Vote {
    public String voteID;
    private String userID;
    private String applicationID;
    private String comment;

    public Vote(String voteID, String userID, String applicationID, String comment) {
        this.voteID = voteID;
        this.userID = userID;
        this.applicationID = applicationID;
        this.comment = comment;
    }

    
    
    public Vote() {
    }

   

    public String getVoteID() {
        return voteID;
    }

    public void setVoteID(String voteID) {
        this.voteID = voteID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
     @Override
    public String toString() {
        return "Vote{" + "voteID=" + voteID + ", userID=" + userID + ", applicationID=" + applicationID + ", comment=" + comment + '}';
    }
}



