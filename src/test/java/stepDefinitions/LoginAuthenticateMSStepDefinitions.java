package stepDefinitions;

import endpoints.LoginAuthenticateMSEndpoint;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class LoginAuthenticateMSStepDefinitions {

	private Util util;
	private LoginAuthenticateMSEndpoint loginAuthenticateMSEndpoint = new LoginAuthenticateMSEndpoint();

	public LoginAuthenticateMSStepDefinitions(Util util) {
		this.util = util;
	}

	@Given("^Provide valid url for generating the token$")
	public void provide_valid_url() {
		util.setRequest(loginAuthenticateMSEndpoint.getRequestWithJSONHeaders("OS:android OSVERSION:12 APPVERSION:6"));
	}
	
	@When("^Generate the token for specific Device Id$")
	public void generate_token() {
		util.setResponse(loginAuthenticateMSEndpoint.getUserAuthentication(util.getRequest(), "0bab83dd-a8bc-4502-8e6d-bb78849b9aff", "password123456"));
	}
	
    @Then("^Verify status code in Login API$")
    public void verify_status_code() {
		loginAuthenticateMSEndpoint.verifyResponseStatusValue(util.getResponse(), 200);
    }

	@And("^Get the Token from response$")
	public void verify_token_is_generated() {
		Assert.assertNotNull(util.getResponse().body().jsonPath().get("token"));
		System.out.println("Token Value is: " + util.getResponse().body().jsonPath().get("token"));
	}

	@Then("^Verify status code in Register Device API$")
	public void verify_status_code_RegisterAPI() {
		loginAuthenticateMSEndpoint.verifyResponseStatusValue(util.getResponse(), 200);

	}
	@And("^Get the response code$")
	public void verify_responseCode() {
		Assert.assertNotNull(util.getResponse().getBody().toString());
		System.out.println("Response is: " + util.getResponse().getBody().toString());

	}


	@Given("^Need to generate Token prior to start any API$")
	public void generateToken() {
		Response response = loginAuthenticateMSEndpoint.generateToken("0bab83dd-a8bc-4502-8e6d-bb78849b9aff", "password123456");
		Assert.assertEquals(response.statusCode(), 200);
		util.setToken(response.getBody().jsonPath().get("token"));
	}

}
