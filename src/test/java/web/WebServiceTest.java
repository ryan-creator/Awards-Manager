/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.*;
import com.squareup.okhttp.*;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the web (webservice) package by making http requests to the api.
 * Contains 6 unit tests in total.
 * These unit tests use mock data from mock-data.sql in the resource folder.
 * @author Ubaada
 */
public class WebServiceTest {
    // store logged in session token of a committee member account and a
    // department account respectively.
    static String commToken;
    static String depToken;
    public WebServiceTest() {

    }
    
    /*
    Logs in with a committee member's and a department accounts and stores the 
    token for use in the unit tests
    */
    @BeforeClass
    public static void setUpClass() throws IOException {
        // get committee member level access
       OkHttpClient client = new OkHttpClient();
       MediaType mediaType = MediaType.parse("application/json");
       //userid and password stored in the database.
       RequestBody body = RequestBody.create(mediaType, "{\"user_id\": \"bob64\", \"password\": \"bob123\"}");
       Request request = new Request.Builder()
         .url("http://localhost:8081/api/user/login")
         .post(body)
         .addHeader("content-type", "application/json")
         .build();
       Response response = client.newCall(request).execute();
       commToken = response.body().string().replace("\"", "");
       response.body().close();
        
       // get department level access
       body = RequestBody.create(mediaType, "{\"user_id\": \"john98\", \"password\": \"dep123\"}");
       request = new Request.Builder()
         .url("http://localhost:8081/api/user/login")
         .post(body)
         .addHeader("content-type", "application/json")
         .build();
       response = client.newCall(request).execute();
       depToken = response.body().string().replace("\"", "");
       response.body().close();
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        
    }


    /*
    * Tries to get a session token with a wrong user/password combination.
    * checks if access is denied 400 UNAUTHORIZED.
    */
    @Test
    public void wrongLoginTest() throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"user_id\": \"bob64\", \"password\": \"wrongpw\"}");
        Request request = new Request.Builder()
          .url("http://localhost:8081/api/user/login")
          .post(body)
          .addHeader("content-type", "application/json")
          .build();

        Response response = client.newCall(request).execute();
        assertEquals(400, response.code());
        response.body().close();
    }
    
    /*
    * Tries to get the session token with a correct username/password.
    * checks for return code 200 OK.
    */
    @Test
     public void rightLoginTest() throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"user_id\": \"john98\", \"password\": \"dep123\"}");
        Request request = new Request.Builder()
          .url("http://localhost:8081/api/user/login")
          .post(body)
          .addHeader("content-type", "application/json")
          .build();

        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
        response.body().close();
    }
     
     /*
     * Retries applications of a single category using a Committee account.
     * Checks if every application returned has the same category.
     */
    @Test
    public void applicationsByCategoryCommitteeTest() throws IOException {
        String cate = "Best-Researcher";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
          .url("http://localhost:8081/api/categories/" + cate)
          .get()
          .addHeader("cookie", "session-token="+commToken)
          .build();

        Response response = client.newCall(request).execute();
        String jsonResp = response.body().string();
        response.body().close();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Application> apps = objectMapper.readValue(jsonResp, new TypeReference<List<Application>>(){});
        for(Application a: apps) {
                assertEquals(cate, a.getCategory());
        }
    }
    
    /*
    * Retries the applications by a category but with a department account.
    * API should only return nominees of that department. 
    * For now checks with the mock string placed in the right place in the API.
    * Since DbDAO doesn't support this operation currently.
    */
    @Test
    public void applicationsByCategoryDepartmentTest() throws IOException {
        String cate = "Best-Researcher";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
          .url("http://localhost:8081/api/categories/" + cate)
          .get()
          .addHeader("cookie", "session-token="+depToken)
          .build();

        Response response = client.newCall(request).execute();
        String jsonResp = response.body().string();
        response.body().close();
        //temperory reposnse
        assertEquals("\"get application of this category only for this department\"", jsonResp);
    }
    
    /*
    * Gets all the categories of the applications.
    * For now checks with the mock categories placed in the API. Since DbDAO
    * doesn't support this operation currently.
    */
   @Test
    public void getAllCategoriesTest() throws IOException {
        //temperory reposnse
        String[] expected = {"Best Teacher","Best Researcher"};
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
          .url("http://localhost:8081/api/categories/")
          .get()
          .addHeader("cookie", "session-token="+depToken)
          .build();
        
        Response response = client.newCall(request).execute();
        String jsonResp = response.body().string();
        response.body().close();
        ObjectMapper objectMapper = new ObjectMapper();
        String[] categories = objectMapper.readValue(jsonResp, new TypeReference<String[]>(){}); 
        
        
        assertEquals(expected[0], categories[0]);
        assertEquals(expected[1], categories[1]);
    }
    
    /*
    * Retries a single application by ID.
    * checks if the result application ID is the same as requested.
    */
    @Test
    public void getASingleApplicatio() throws IOException {
        String appID = "app99";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
          .url("http://localhost:8081/api/application/" + appID)
          .get()
          .addHeader("cookie", "session-token="+depToken)
          .build();
        
        Response response = client.newCall(request).execute();
        String jsonResp = response.body().string();
        response.body().close();
        ObjectMapper objectMapper = new ObjectMapper();
        Application app = objectMapper.readValue(jsonResp, Application.class); 
        
        assertEquals(app.getApplicationID(),appID);
    }
}
