package apiSuites;

import models.ApiBase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import testClasses.ApiTestClass;

import java.util.ArrayList;

public class ApiSuite {




    @BeforeSuite
    public void SetBaseURI()
    {
        ApiBase.setRestAssuredBaseURI("https://reqres.in/");
    }

    @Test
    public void GetListOfUsers() throws Exception
    {
        ApiTestClass apiTestClass = new ApiTestClass();
        apiTestClass.GetListOfUser("api/users");
    }

    @Test
    public void GetUser() throws Exception
    {

        ApiTestClass apiTestClass = new ApiTestClass();
        String userId = apiTestClass.GetUserID("api/users","charles.morris@reqres.in");

        ArrayList<String> userDetails =  new ArrayList<>();
        userDetails.add("5");
        userDetails.add("charles.morris@reqres.in");

        if(!userId.isBlank())
        {
            apiTestClass.GetUserDetails("api/users/"+userId,userDetails);
        }
    }
    @Test
    public void CreateUser() throws Exception
    {
        String newUserId="";

        JSONObject userPayload = new JSONObject();
        userPayload.put("name","Neo");
        userPayload.put("job","SQA");


        ApiTestClass apiTestClass = new ApiTestClass();
        newUserId = apiTestClass.CreateUser("api/users",userPayload);

    }
    @Test
    public void UpdateUser() throws Exception
    {
        String newUserId="";

        JSONObject userPayload = new JSONObject();
        userPayload.put("name","Neo");
        userPayload.put("job","SQA");


        ApiTestClass apiTestClass = new ApiTestClass();
        newUserId = apiTestClass.CreateUser("api/users",userPayload);

        //Update user job
        JSONObject updateUserPayload = new JSONObject();
        userPayload.put("job","SQE");
        apiTestClass.UpdateUser("api/users/"+newUserId,updateUserPayload);


    }
    @Test
    public void DeleteUser() throws Exception
    {
        String newUserId="";

        JSONObject userPayload = new JSONObject();
        userPayload.put("name","Neo");
        userPayload.put("job","SQA");


        ApiTestClass apiTestClass = new ApiTestClass();
        newUserId = apiTestClass.CreateUser("api/users",userPayload);

        //Delete newly created user
        apiTestClass.DeleteUser("api/users/"+newUserId);
    }
}