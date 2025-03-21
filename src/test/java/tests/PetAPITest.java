package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;
import org.awaitility.Awaitility;
import org.json.JSONObject;


public class PetAPITest {
	String baseuri= "https://petstore.swagger.io";
	@Test(priority = 1)
	public void post() {
		RestAssured.baseURI=baseuri;
		JSONObject jsonObject = new JSONObject();
		//insert key value pair to jsonObject
		jsonObject.put("id", "2002002");
		jsonObject.put("name", "Myname");
		jsonObject.put("status", "Available");

		String resp=  given().log().all()
				.accept("application/json")
				.contentType("application/json")
				.and()
				.body(jsonObject.toString())
				.post("/v2/pet")   //hit the post end point
				.thenReturn().asString();

		System.out.println(resp);

	}
	
	@Test(priority = 2)
	public void get() {

		RestAssured.baseURI=baseuri;
		Awaitility.await().atMost(180, TimeUnit.SECONDS).pollInterval(5, TimeUnit.SECONDS).untilAsserted(() -> Assert.assertTrue(true));
		{ 
		String response = given().log().all()
				.when().get("/v2/pet/2002002")
				.then().assertThat().statusCode(200)
				.extract().response().asString();
		System.out.println(response);
	}
	}

	@Test(priority = 3)
	public void delete() {

		RestAssured.baseURI=baseuri;		 
		String response = given().log().all()
				.when().delete("/v2/pet/2002002")
				.then().assertThat().statusCode(200)
				.extract().response().asString();
		System.out.println(response);
	
	}

}
