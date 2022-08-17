package stepDefinitions;

import endpoints.LoginAuthenticateMSEndpoint;
import endpoints.RegisterDeviceEndpoint;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import model.RegisterDevice;
import org.testng.Assert;

import java.util.Random;

public class RegisterDeviceStepDefinitions {

	private Util util;

	private static Random random = new Random();
	private static String randomString = String.valueOf(random.nextInt());
	private static final String userAgent = "OS:android OSVERSION:12 APPVERSION:6";
	private static final String deviceId = "0bab83dd-a8bc-4502-8e6d-bb78849b9a" + randomString;
	private static final String password = "password123456";
	private RegisterDeviceEndpoint registerDeviceEndpoint = new RegisterDeviceEndpoint();

	public RegisterDeviceStepDefinitions(Util util) {
		this.util = util;
	}

	@Given("^I have a valid header for device$")
	public void i_have_a_valid_header_for_device() {
		util.setRequest(registerDeviceEndpoint.getRequestWithJSONHeaders(userAgent));
	}

	@When("^I register a device with valid data$")
	public void i_register_a_device_with_valid_data() {
		System.out.println("==========="+deviceId);
		RegisterDevice registerDevice = new RegisterDevice(deviceId, password, "register@toranoko.com");
		util.setRegisterDevice(registerDevice);
		util.setResponse(registerDeviceEndpoint.registerDevice(util.getRequest(), registerDevice));
	}

	@Then("^The device is registered successfully$")
	public void the_device_is_registered_successfully() {
		registerDeviceEndpoint.verifySuccessResponseStatusValue(util.getResponse());
	}

	@And("^Verify the response for registered device$")
	public void verify_the_response_for_registered_device() {
		Assert.assertNotNull(util.getResponse().getBody().toString());
	}

	@When("^I register a device with existing device$")
	public void i_register_a_device_with_existing_device() {
		util.setResponse(registerDeviceEndpoint.registerDevice(util.getRequest(), util.getRegisterDevice()));
	}

	@When("^I register a device with whitespace string for the field (.*) and value (.*)$")
	public void i_register_a_device_with_whitespace_string_for_the_field_and_value(String field, String value) {
		String device = deviceId;
		String passwd = password;
		String email = "register@toranoko.com";
		if (value.equalsIgnoreCase("empty")) {
			value = "";
		} else if (value.equalsIgnoreCase("NL")) {
			value = "\n";
		}
		if(field.equalsIgnoreCase("deviceId")) {
			device = value;
		} else if (field.equalsIgnoreCase("password")) {
			passwd = value;
		} else if (field.equalsIgnoreCase("email")) {
			email = value;
		}
		RegisterDevice registerDevice = new RegisterDevice(device, passwd, email);
		util.setResponse(registerDeviceEndpoint.registerDevice(util.getRequest(), registerDevice));
	}

	@Then("^The register device response code is (.*) with an error message of (.*)$")
	public void the_register_device_response_code_is_with_an_error_message(String code, String message) {
		// Validate Response Code
		Assert.assertEquals(util.getResponse().statusCode(), Integer.parseInt(code));
		System.out.println("=========="+util.getResponse().getBody().prettyPrint());
		registerDeviceEndpoint.verifyErrorMessage(util.getResponse(), message);
	}
}
