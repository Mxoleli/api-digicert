package testClasses;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.ApiBase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import java.util.ArrayList;

import static java.lang.System.out;

public class ApiTestClass extends ApiBase {



    public void GetListOfUser(String uri) throws Exception
    {
        Response resp = ExecuteGetRequest(uri);
        ValidateStatusCode("200",resp);

        //out.println("Response : "+resp.getBody().prettyPrint());

        JSONParser jsonParser = new JSONParser();
        JSONObject mainJsonObject =  (JSONObject) jsonParser.parse(resp.getBody().asString());
        JSONArray usersJsonArray = (JSONArray) mainJsonObject.get("data");

        if(usersJsonArray.isEmpty())
        { throw new Error("No User returned in response");}

        out.println("Number of users returned = "+usersJsonArray.size());
    }
    public String GetUserID(String uri,String userEmailAdd) throws Exception
    {
        String userID = "";
        Response resp = ExecuteGetRequest(uri);
        ValidateStatusCode("200",resp);

        //out.println("Response : "+resp.getBody().prettyPrint());

        JSONParser jsonParser = new JSONParser();
        JSONObject mainJsonObject =  (JSONObject) jsonParser.parse(resp.getBody().asString());
        JSONArray usersJsonArray = (JSONArray) mainJsonObject.get("data");

        for (Object usersJson: usersJsonArray) {
            //Convert array object to JSON
            JSONObject userDetails =  (JSONObject) jsonParser.parse(usersJson.toString());
            //out.println("User email add : "+userDetails.get("email"));
            if(!userEmailAdd.isBlank() && (userEmailAdd.equals(userDetails.get("email").toString())))
            {
                userID = userDetails.get("id").toString();
                out.println("User Id returned = "+userID);
                break;
            }
        }


        if(usersJsonArray.isEmpty())
        { throw new Error("No User returned in response");}

        //out.println("Number of users returned = "+usersJsonArray.size());

        return userID;
    }

    public void GetUserDetails(String uri, ArrayList<String> userDetails) throws Exception
    {
        Response resp = ExecuteGetRequest(uri);
        ValidateStatusCode("200",resp);

        //out.println("Response : "+resp.getBody().prettyPrint());

        JSONParser jsonParser = new JSONParser();
        JSONObject mainJsonObject =  (JSONObject) jsonParser.parse(resp.getBody().asString());
        JSONObject usersJsonObj = (JSONObject) mainJsonObject.get("data");

        for (String userInfo: userDetails) {
            //Verify user details are returned on the response
            out.println("User Info: "+userInfo);
            Assert.assertTrue(usersJsonObj.toString().contains(userInfo));
        }

    }

    public String CreateUser(String uri, JSONObject userPayload) throws Exception
    {
        Response resp = ExecutePostRequest(uri,userPayload);
        ValidateStatusCode("201",resp);

        //out.println("Response : "+resp.getBody().prettyPrint());

        JSONParser jsonParser = new JSONParser();
        JSONObject mainJsonObject =  (JSONObject) jsonParser.parse(resp.getBody().asString());

        Assert.assertEquals(mainJsonObject.get("name").toString(),userPayload.get("name").toString());
        Assert.assertEquals(mainJsonObject.get("job").toString(),userPayload.get("job").toString());

        return  mainJsonObject.get("id").toString();

    }

    public void UpdateUser(String uri, JSONObject userPayload) throws Exception
    {
        Response resp = ExecutePutRequest(uri,userPayload);
        ValidateStatusCode("200",resp);

        JSONParser jsonParser = new JSONParser();
        JSONObject mainJsonObject =  (JSONObject) jsonParser.parse(resp.getBody().asString());

        Assert.assertEquals(mainJsonObject.get("job").toString(),userPayload.get("job").toString());
    }
    public void DeleteUser(String uri) throws Exception
    {
        Response resp = ExecuteDeleteRequest(uri);
        ValidateStatusCode("204",resp);

        out.println("Response : "+resp.getBody().prettyPrint());
    }
}