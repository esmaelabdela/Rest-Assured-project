package testCase;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.ConfigReader;

public class ReadOneAccount extends GeneretBearerToken{

		 String baseURI;
		 String readOneAccountEndpoint;
		 String firstAccountId;
		 
		 public ReadOneAccount() {
		  baseURI = ConfigReader.getProperty("baseURI");
		  readOneAccountEndpoint= ConfigReader.getProperty("readOneAccountEndpoint");
		  
		 }
		 
		 @Test
		 public void readOneAccounts() {
		
		  Response response = 
		  
		  given()
		   .baseUri(baseURI)
		   .header("Content-Type","application/json")
		   .auth().preemptive().basic("demo1@codefios.com", "abc123")
		   .queryParam("account_id", "419").
	//	   .log().all().
		  when()
		   .get(readOneAccountEndpoint).
		  then()
	//	   .log().all()
		   .extract().response();
		  
		  int statusCode = response.getStatusCode();
		  System.out.println("Status Code:" + statusCode);
		  Assert.assertEquals(statusCode, 200, "Status codes are NOT matching!");
		  
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
		  
		 String  accountId = jp.getString("account_id");
		 System.out.println("Account Id:" + accountId);
		 Assert.assertEquals(accountId, "419");
		 
		 String  accountName = jp.getString("account_name");
		 System.out.println("Account Name:" + accountName);
		 Assert.assertEquals(accountName, "golam sarwar");
		 
		 String  accountNumber = jp.getString("account_number");
		 System.out.println("Account Number:" + accountNumber);
		 Assert.assertEquals(accountNumber, "1000100101");
		 
	
		 
		  
		 } 
		 
		 
		 
	}
    	
    	
    	
   
    
    	
    	
    	
    	
	
	
	

