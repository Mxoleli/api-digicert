package models;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;

public class ApiBase {




    private static String baseURI;
    private static String apiKey = "reqres-free-v1";

    public static void setRestAssuredBaseURI(String baseURI)
    {
        ApiBase.baseURI = baseURI;
    }

    public void ValidateStatusCode(String statusCode, Response apiResponse)
    {
        Assert.assertEquals(statusCode,String.valueOf(apiResponse.getStatusCode()));
    }

    public Response ExecuteGetRequest(String uri)
    {
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("x-api-key",apiKey);
        return  httpRequest.get(uri);
    }
    public Response ExecutePostRequest(String uri, JSONObject reqBody)
    {
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("x-api-key",apiKey);
        httpRequest.contentType(ContentType.JSON);
        httpRequest.body(reqBody.toJSONString());
        return  httpRequest.post(uri);
    }
    public Response ExecutePutRequest(String uri, JSONObject reqBody)
    {
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("x-api-key",apiKey);
        httpRequest.contentType(ContentType.JSON);
        httpRequest.body(reqBody.toJSONString());
        return  httpRequest.put(uri);
    }
    public Response ExecuteDeleteRequest(String uri)
    {
        RestAssured.baseURI = baseURI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("x-api-key",apiKey);
        return  httpRequest.delete(uri);
    }
}