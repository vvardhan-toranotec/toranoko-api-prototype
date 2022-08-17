package stepDefinitions;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RegisterDevice;

public class Util {
	private Response response;
	private RequestSpecification request;

	private RegisterDevice registerDevice;

	private String token;

	public void setResponse(Response response) {
		this.response = response;
	}
	
	public Response getResponse() {
		return this.response;
	}
	
	public void setRequest(RequestSpecification request) {
		this.request = request;
	}

	public RequestSpecification getRequest() {
		return this.request;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public RegisterDevice getRegisterDevice() {
		return registerDevice;
	}

	public void setRegisterDevice(RegisterDevice registerDevice) {
		this.registerDevice = registerDevice;
	}
}
