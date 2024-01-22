package testCase;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.ConfigReader;

public class GeneretBearerToken{

		 String baseURI;
		 String authEndPoint;
		 String authBodyFilePath;
		 public static String bearerToken;
		 
		 public GeneretBearerToken() {
		  baseURI = ConfigReader.getProperty("baseURI");
		  authEndPoint= ConfigReader.getProperty("authEndpoint");
		  authBodyFilePath="src/main/java/data/authBody.json";
		  generateBearerToken();
		   
		 }
		    
		 public String generateBearerToken() {
		  /*
		  given: all input details -> (baseURI,Header/s,Authorization,Payload/Body,QueryParameters)
		  when:  submit api requests-> HttpMethod(Endpoint/Resource)
		  then:  validate response -> (status code, Headers, responseTime, Payload/Body)
		   
		  baseURI=https://qa.codefios.com/api  
		  https://qa.codefios.com/api /user/login
		  Headers: 
		  "Content-Type" = "application/json"
		  payload/body=
		  {
		      "username": "admin",
		      "password": "123456"
		  }
		  statusCode=201
		  response=
		  {
		      "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyIiwidXNlcm5hbWUiOiJhZG1pbiIsIkFQSV9USU1FIjoxNzA1MjQ1NjYyfQ.qUeFzu-fkB4ZkYhQNqV7cmNCahLYQWLZcQEa3asVMBU",
		      "status": true,
		      "message": "Login success!",
		      "token_expire_time": 86400
		  }
		  */ 
		  
		  Response response = 
		  
		  given()
		   .baseUri(baseURI)
		   .header("Content-Type","application/json")
		   .body(new File(authBodyFilePath)).
	//	   .log().all().
		  when()
		   .post(authEndPoint).
		  then()
	//	   .log().all()
		   .extract().response();
		  
		  int statusCode = response.getStatusCode();
		  System.out.println("Status Code:" + statusCode);
		  Assert.assertEquals(statusCode, 201, "Status codes are NOT matching!");
		  
		  String responseHeaderContentType = response.getHeader("Content-Type");
		  System.out.println("Response Header Content-Type:" + responseHeaderContentType);
		  Assert.assertEquals(responseHeaderContentType, "application/json", "Status Content-Types are NOT matching!");
		  
		  long responseTimeInMilliSecs = response.getTimeIn(TimeUnit.MILLISECONDS);
		  System.out.println("Response Time InMilliSecs:" + responseTimeInMilliSecs);
		  
		  if(responseTimeInMilliSecs <=2000) {
		   System.out.println("Response time is within range.");
		  }else {
		   System.out.println("Response time is out of range!");
		  }   
		  
		  String responseBody = response.getBody().asString();
	//	  System.out.println("Response Body:" + responseBody);
		  
		  JsonPath jp = new JsonPath(responseBody);
		  
		  bearerToken = jp.getString("access_token");
		  System.out.println("Bearer Token:" + bearerToken);
		return bearerToken;   
		  
		 }
		 
		 
		 
	}
    	
    	
    	
   
    
    	
    	
    	
    	
	
	
	

