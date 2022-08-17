package endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.LoginAuthenticateMSUser;


public class LoginAuthenticateMSEndpoint extends Base {

	private final String LOGIN_ENDPOINT_PATH = "api/auth/login";
	private final String REGISTER_ENDPOINT_PATH = "api/auth/register";

	public LoginAuthenticateMSEndpoint() {
		super();
	}
	
	public String getPath() {
		return this.LOGIN_ENDPOINT_PATH;
	}
	public String getRegisterPath() {
		return this.REGISTER_ENDPOINT_PATH;
	}

	public Response getUserAuthentication(RequestSpecification request, String device, String password) {
		String url = getBaseUrl() + this.getPath();
		LoginAuthenticateMSUser loginAuthenticateMSUser = new LoginAuthenticateMSUser(device,password);
		return sendRequest(request, Base.POST_REQUEST, url, loginAuthenticateMSUser);
	}

	public Response generateToken(String deviceId, String password) {
		RequestSpecification r = RestAssured.given();
		r.header("Content-Type", "application/json");
		r.header("User-Agent","OS:android OSVERSION:12 APPVERSION:6");
		String url = getBaseUrl() + "api/auth/login";
		LoginAuthenticateMSUser loginAuthenticateMSUser = new LoginAuthenticateMSUser(deviceId,password );
		return sendRequest(r, Base.POST_REQUEST, url, loginAuthenticateMSUser);
	}

	/*public Response getDeviceInput(RequestSpecification request, String device, String password, String email) {
		String url = getBaseUrl() + this.getRegisterPath();
		LoginAuthenticateMSUser loginAuthenticateMSUser = new LoginAuthenticateMSUser(device, password, email);
		return sendRequest(request, Base.POST_REQUEST, url, loginAuthenticateMSUser);
	}*/

}
