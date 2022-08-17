package endpoints;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AuthenticateEndpoint extends Base {
	private final String LOGIN_CHECK_ENDPOINT_PATH = "app/loginCheck.php";

	public AuthenticateEndpoint() {
		super();
	}
	
	public String getPath() {
		return this.LOGIN_CHECK_ENDPOINT_PATH;
	}

	public Response getUserAuthentication(RequestSpecification request, String user, String password, String auto, String platform) {
		String url = getBaseUrl() + this.getPath();
		request.param("user", user);
		request.param("pass", password);
		request.param("auto", auto);
		request.param("platform", platform);
		return sendRequest(request, Base.GET_REQUEST, url, null);
	}


}
