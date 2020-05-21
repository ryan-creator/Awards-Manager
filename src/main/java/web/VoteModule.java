/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.DbDAO;
import dao.StorageInterface;
import domain.User;
import domain.Vote;
import org.jooby.Jooby;
import org.jooby.Response;
import org.jooby.Status;

/**
 *
 * @author Ubaada
 */
public class VoteModule extends Jooby  {
/*
Route                                   Method  Use     Auth.
----------------------------------------------------------------
api/vote/                               [POST]  (H)     Com
api/vote/{category}                     [GET]   (I)     Com 
*/
    public VoteModule(StorageInterface dbDAO, SessionManager sessionManager) {
        post("/api/vote", (req, rsp) -> {
            User loggedUser = sessionManager.getUser(req);
            Vote vote = req.body().to(Vote.class);
            if (loggedUser.getUserType().equals("Committee")) {
              
                dbDAO.addVote(vote);
                rsp.status(Status.CREATED);
            } else {
                rsp.status(Status.UNAUTHORIZED);
            }
        });
        
        get("/api/vote/:category", (req) -> {
            User loggedUser = sessionManager.getUser(req);
            Vote vote = req.body().to(Vote.class);
            if (loggedUser.getUserType().equals("Commitee")) {
                String category =req.param("category").value();
                return "results by category";  //<-- issue
            } else {
                return("UNAUTHORIZED");
            }
        });
    }
}
