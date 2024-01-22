package testCase;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.ConfigReader;

public class CreateOneAccountAsMap extends GeneretBearerToken {

	String baseURI;
	String createOneAccountEndpoint;
	String createAccountBodyFilePath;
	String readAllAccountsEndpoint;
	String firstAccountId;
	String readOneAccountEndpoint;
    Map<String,String> createPayloadMap;
    
   /* 
    {
        "account_name": "ESMAEL Techfios account 111",
        "account_number": "123456789",
        "description": "Test description 1",
        "balance": "100.22",
        "contact_person": "SITRA"
    }*/
    
	public CreateOneAccountAsMap() {
		baseURI = ConfigReader.getProperty("baseURI");
		createOneAccountEndpoint = ConfigReader.getProperty("createOneAccountEndpoint");
	//	createAccountBodyFilePath = "src/main/java/data/createAccountBody.json";
		createPayloadMap = new HashMap<String,String>();
		readAllAccountsEndpoint = ConfigReader.getProperty("readAllAccountsEndpoint");
		readOneAccountEndpoint = ConfigReader.getProperty("readOneAccountEndpoint");
	}

	
	public Map<String,String> createAccountBodyMap(){
		createPayloadMap.put("account_name", "ESMAEL Techfios account 111");
		createPayloadMap.put("account_number", "123456789");
		createPayloadMap.put("description", "Test description 1");
		createPayloadMap.put("balance", "100.22");
		createPayloadMap.put("contact_person" , "SITRA");
		
		
		return createPayloadMap;
		
	}
	

	@Test(priority = 1)
	public void createOneAccount() {

		Response response =

				 given().baseUri(baseURI)
				        .header("Content-Type", "application/json")
						.header("Authorization", "Bearer " + bearerToken)
						.body(createAccountBodyMap())
						.log().all().when().post(createOneAccountEndpoint).

						then()
						// .log().all()
						.extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Status Code:" + statusCode);
		Assert.assertEquals(statusCode, 201, "Status codes are NOT matching!");

		String responseHeaderContentType = response.getHeader("Content-Type");
		System.out.println("Response Header Content-Type:" + responseHeaderContentType);
		Assert.assertEquals(responseHeaderContentType, "application/json", "Status Content-Types are NOT matching!");

		String responseBody = response.getBody().asString();
		// System.out.println("Response Body:" + responseBody);

		JsonPath jp = new JsonPath(responseBody);

		String message = jp.getString("message");
		System.out.println("response message:" + message);
		Assert.assertEquals(message, "Account created successfully.");

	}

	@Test(priority = 2)
	public void readAllAccounts() {

		Response response =

				given().baseUri(baseURI).header("Content-Type", "application/json")
					   .header("Authorization", "Bearer " + bearerToken).
		//			   .log().all().
				 when().get(readAllAccountsEndpoint).then()
		//			   .log().all()
					   .extract().response();

		String responseBody = response.getBody().asString();

		JsonPath jp = new JsonPath(responseBody);

		firstAccountId = jp.getString("records[0].account_id");
		System.out.println("first Account_Id :" + firstAccountId);

	}

	@Test(priority = 3)
	public void readOneAccounts() {

		Response response =

				  given().baseUri(baseURI)
				         .header("Content-Type", "application/json")
				         .auth().preemptive()
						 .basic("demo1@codefios.com", "abc123")
						 .queryParam("account_id", firstAccountId).
		//		         .log().all().
				   when().get(readOneAccountEndpoint).then()
		//				 .log().all()
						.extract().response();

	String actualResponseBody = response.getBody().asString();
		// System.out.println("Response Body:" + actualResponseBody);

	JsonPath jp = new JsonPath(actualResponseBody);

		String actualAccountName = jp.getString("account_name");
		System.out.println("Actual Account Name:" + actualAccountName);

		String actualAccountDescription = jp.getString("description");
		System.out.println("Actual Account Description:" + actualAccountDescription);

		String actualAccountBalance = jp.getString("balance");
		System.out.println("Actual Account Balance:" + actualAccountBalance);

		String actualAccountNumber = jp.getString("account_number");
		System.out.println("Actual Account Number:" + actualAccountNumber);

		String actualAccountContactPerson = jp.getString("contact_person");
		System.out.println("Actual contact person:" + actualAccountContactPerson);

//========================================================================

		String expectedAccountName = createAccountBodyMap().get("account_name");
		System.out.println("expected Account Name:" + expectedAccountName);

		String expectedAccountDescription = createAccountBodyMap().get("description");
		System.out.println("expected Account Description:" + expectedAccountDescription);

		String expectedAccountBalance = createAccountBodyMap().get("balance");
		System.out.println("expected Account Balance:" + expectedAccountBalance);

		String expectedAccountNumber = createAccountBodyMap().get("account_number");
		System.out.println("expected Account Number:" + expectedAccountNumber);

		String expectedAccountContactPerson = createAccountBodyMap().get("contact_person");
		System.out.println("expected contact person:" + expectedAccountContactPerson);

		Assert.assertEquals(actualAccountName, expectedAccountName);
		Assert.assertEquals(actualAccountDescription, expectedAccountDescription);
		Assert.assertEquals(actualAccountBalance, expectedAccountBalance);
		Assert.assertEquals(actualAccountNumber, expectedAccountNumber);
		Assert.assertEquals(actualAccountContactPerson, expectedAccountContactPerson);

	}

}
