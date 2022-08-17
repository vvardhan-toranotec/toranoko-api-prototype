package endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.LoginAuthenticateMSUser;


public class RegisterDeviceEndpoint extends Base {

	private final String REGISTER_ENDPOINT_PATH = "api/auth/register";

	public RegisterDeviceEndpoint() {
		super();
	}

	public String getPath() {
		return this.REGISTER_ENDPOINT_PATH;
	}

	public Response registerDevice(RequestSpecification request, Object body) {
		String url = getBaseUrl() + this.getPath();
		return sendRequest(request, Base.POST_REQUEST, url, body);
	}
}
