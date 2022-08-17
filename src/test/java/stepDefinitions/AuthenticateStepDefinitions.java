package stepDefinitions;

import endpoints.AuthenticateEndpoint;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AuthenticateStepDefinitions {

	private Util util;
	private AuthenticateEndpoint authenticateEndpoint = new AuthenticateEndpoint();

	public AuthenticateStepDefinitions(Util util) {
		this.util = util;
	}

	@Given("^Provide valid url$")
	public void provide_valid_url() {
		util.setRequest(authenticateEndpoint.getRequestWithJSONHeaders());
	}
	
	@When("^Generate the token for specific uer$")
	public void generate_token() {
		//util.setResponse(authenticateEndpoint.getUserAuthentication(util.getRequest()));
		util.setResponse(authenticateEndpoint.getUserAuthentication(util.getRequest(), "1000011060", "pass1234", "1", "1"));
	}
	
    @Then("^Verify status code$")
    public void verify_status_code() {
		authenticateEndpoint.verifyResponseStatusValue(util.getResponse(), authenticateEndpoint.SUCCESS_STATUS_CODE);
    }

	@And("^Verify whether Token is generated$")
	public void verify_token_is_generated() {
		Assert.assertNotNull(util.getResponse().body().jsonPath().get("otb_token"));
	}
}
