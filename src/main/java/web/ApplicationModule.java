/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.gson.JsonObject;
import dao.StorageInterface;
import domain.Application;
import domain.User;
import java.util.ArrayList;
import java.util.Collection;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author Ubaada
 */
public class ApplicationModule extends Jooby {

    /*
Route                                   Method  Use     Auth.
----------------------------------------------------------------
api/application/                        [POST]  (E)     Dep
api/application/{user_id}               [GET]   (F)     Nom, Dep, Com
api/categories                          [GET]   (B)     Dep, Com
api/categories/{category}               [GET]   (G,C)   Com,Dep(partial)
     */
    public ApplicationModule(StorageInterface dbDAO, SessionManager sessionManager) {

        //create application
        //Right after a department accounts nominates a person. A partially filled
        //application (with only category selected) needs to be added.
        //issue might have been avoided if different account type had different tables <--issue
        post("/api/application", (req, rsp) -> {
            User loggedUser = sessionManager.getUser(req);
            Application appl = req.body().to(Application.class);
            if (loggedUser.getUserType().equals("Department")) {
                dbDAO.addApplication(appl);
                rsp.status(Status.CREATED);
            } else {
                rsp.status(Status.UNAUTHORIZED);
            }
        });

        //for upating application by nominnee
        put("/api/application/:user_id", (req, rsp) -> {
            User loggedUser = sessionManager.getUser(req);
            Application appl = req.body().to(Application.class);
            System.out.println(appl);
            System.out.println(loggedUser);
            if (loggedUser.getUserType().equals("Nominee") &&
                    loggedUser.getUserID().equals(appl.getUserCandidate())) {
                dbDAO.updateApplication(appl);
                rsp.status(Status.ACCEPTED);
            } else {
                rsp.status(Status.UNAUTHORIZED);
            }
        });
        
        //get application by a key, not sure which right now.
        //might not appID since an application is unique to a user.  <-- issue
        get("/api/application/:user_id", (req) -> {
            User loggedUser = sessionManager.getUser(req);
            String userID = req.param("user_id").value();
            if (loggedUser.getUserType().equals("Nominee")
                    || loggedUser.getUserType().equals("Department")
                    || loggedUser.getUserType().equals("Committee")) {
                return dbDAO.getApplicationByUserID(userID);

            } else {
                return ("UNAUTHORIZED");
            }
        });

        //get categories for each a person can be nominated.
        //used to generate tabs in web client for nominating and voting.
        //currently no method in DAO available <-- issue
        get("/api/categories/", (req) -> {
            User loggedUser = sessionManager.getUser(req);
            if (loggedUser.getUserType().equals("Department")
                    || loggedUser.getUserType().equals("Committee")) {
                String[] categories = {"Best Teacher", "Best Researcher"};
                return categories;
            } else {
                return ("UNAUTHORIZED");
            }

        });

        //Get applications by category
        //Different access level for department and commitee accounts
        //Populate tabs for nominating and voting.
        get("/api/categories/:category", (req) -> {
            User loggedUser = sessionManager.getUser(req);
            String category = req.param("category").value();
            if (loggedUser.getUserType().equals("Department")) {
                //get application of this category only for this department
                //no method for this in dbDAO currently <-- issues
                return "get application of this category only for this department";
            } else if (loggedUser.getUserType().equals("Committee")) {
                //get applications of this category across all departments
                return dbDAO.getApplications(category);
            } else {
                return ("UNAUTHORIZED");
            }
        });

        get("/api/applications", (req) -> {
            User loggedUser = sessionManager.getUser(req);
            if (loggedUser.getUserType().equals("Department")) {
                Collection<CategoryApplications> appsByCat = new ArrayList<CategoryApplications>();
                Collection<String> categories = dbDAO.getCategories();
                for (String c : categories) {
                    CategoryApplications singleCatApps = new CategoryApplications();
                    singleCatApps.categoryName = c;
                    singleCatApps.applications = dbDAO.getApplications(c, loggedUser.getDepartment());
                    appsByCat.add(singleCatApps);
                }
                return appsByCat;
            } else if (loggedUser.getUserType().equals("Committee")) {
                Collection<CategoryApplications> appsByCat = new ArrayList<CategoryApplications>();
                Collection<String> categories = dbDAO.getCategories();
                for (String c : categories) {
                    CategoryApplications singleCatApps = new CategoryApplications();
                    singleCatApps.categoryName = c;
                    singleCatApps.applications = dbDAO.getApplications(c);
                    appsByCat.add(singleCatApps);
                }
                return appsByCat;
            } else {
                return ("UNAUTHORIZED");
            }
        });
    }

    private class CategoryApplications {

        String categoryName = "";
        Collection<Application> applications;
    }
}
