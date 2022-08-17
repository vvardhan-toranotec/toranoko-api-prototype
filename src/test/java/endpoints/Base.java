package endpoints;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import io.cucumber.java.en.When;
import model.LoginAuthenticateMSUser;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class Base {
	public static final int SUCCESS_STATUS_CODE = 200;

	public static final int GET_REQUEST = 0;
	public static final int POST_REQUEST = 1;
	public static final int DELETE_REQUEST = 2;
	public static final int PUT_REQUEST = 3;
	private final Logger logger = LoggerFactory.getLogger(Base.class);
	//protected final String base_url = "https://api-stg.toranoko.com/";
	protected final String base_url = "https://dev.api2.money-step.com/";


	public Base() {
	}

	public void verifyResponseKeyValues(String key, String val, Response r) {
		String keyValue = r.jsonPath().getString(key);
		assertThat(keyValue, is(val));
	}

	public void verifyTrue(boolean val) {
		assertTrue(val);
	}

	public void verifyFalse(boolean val) {
		assertFalse(val);
	}

	public void verifyResponseStatusValue(Response response, int expectedCode) {
		assertThat(response.getStatusCode(), is(expectedCode));
	}

	public void verifySuccessResponseStatusValue(Response response) {
		assertThat(response.getStatusCode(), is(SUCCESS_STATUS_CODE));
	}

	public void verifyErrorMessage(Response response, String message) {
		// Validate error message
		if (!message.isEmpty()) {
			if(message.equals("null")) {
				message = null;
			}
			// Check if the 'title' or 'cause' values contain the expected message
			boolean messageFound = false;
			if(response.getBody().jsonPath().get("title") != null) {
				String titleMessage = response.getBody().jsonPath().getString("title");
				String causeMessage = response.getBody().jsonPath().getString("cause");
				if(titleMessage.contains(message)) {
					messageFound = true;
				}
				if(causeMessage.contains(message)) {
					messageFound = true;
				}
				Assert.assertTrue(messageFound, "Expected message to contain " + message + ". Instead it contains title: " + titleMessage + ". Cause: " + causeMessage + ".");
			} else {
				String errorMessage = response.getBody().jsonPath().getString("exception");
				Assert.assertTrue(errorMessage.contains(message), "Expected message to contain: " + message +". Instead it contains: " + errorMessage);
			}
		}
	}

	public String getBaseUrl() {
		return this.base_url;
	}

	public void verifyNestedResponseKeyValues(String nestTedCompnent, String key, String val, Response r) {
		Map<String, String> nestedJSON = r.jsonPath().getMap(nestTedCompnent);
		String actual = String.valueOf(nestedJSON.get(key));
		assertThat(actual, is(val));
	}

	public void verifyNestedArrayValueResponseKeyValues(String nestTedCompnent, String[] val, Response r) {

		ArrayList<Object> nestedArray = (ArrayList<Object>) r.jsonPath().getList(nestTedCompnent);

		String actual;

		for (int i = 0; i < nestedArray.size(); i++) {
			actual = (String) nestedArray.get(i);
			assertThat(actual, is(val[i]));
		}
	}

	public void verifyNestedArrayMapResponseKeyValues(String nestTedCompnent, String key, String[] val, Response r) {
		ArrayList<Object> nestedArray = (ArrayList<Object>) r.jsonPath().getList(nestTedCompnent);

		String actual;
		for (int i = 0; i < nestedArray.size(); i++) {
			Map<String, String> map = (Map<String, String>) nestedArray.get(i);
			actual = String.valueOf(map.get(key));
			assertThat(actual, is(val[i]));
		}
	}

	public RequestSpecification getRequestWithJSONHeaders() {
		RequestSpecification r = RestAssured.given();
		r.header("Content-Type", "application/json");
		logger.info("Header values set.");
		return r;
	}
	public RequestSpecification getRequestWithJSONHeaders(String userAgent) {
		RequestSpecification r = RestAssured.given();
		r.header("Content-Type", "application/json");
		r.header("User-Agent",userAgent);
		logger.info("Header values set.");
		return r;
	}

	public RequestSpecification getRequestWithXMLHeaders() {
		RequestSpecification r = RestAssured.given();
		r.header("Content-Type", "application/xml");
		return r;
	}

	protected JSONObject createJSONPayload(Object pojo) {
		return new JSONObject(pojo);
	}

	public Response sendRequest(RequestSpecification request, int requestType, String url, Object body) {
		Response response = null;

		// Add the Json to the body of the request
		if (null != body) {
			String payload = createJSONPayload(body).toString();
			request.body(payload);
		}

		// need to add a switch based on request type
		switch (requestType) {
		case Base.GET_REQUEST:
			logger.info("Request Method :" + "GET");
			logger.info("Url :" + url);
			logger.info("Headers :" + request.get("Content-Type"));
			if (null == request) {
				response = RestAssured.when().get(url);
			} else {
				response = request.get(url);
			}
			break;
		case Base.POST_REQUEST:
			logger.info("Request Method :" + "POST");
			logger.info("Url :" + url);
			if (null == request) {
				response = RestAssured.when().post(url);
			} else {
				response = request.post(url);
			}
			break;
		case Base.DELETE_REQUEST:
			logger.info("Request Method :" + "DELETE");
			logger.info("Url :" + url);
			if (null == request) {
				response = RestAssured.when().delete(url);
			} else {
				response = request.delete(url);
			}
			break;
		case Base.PUT_REQUEST:
			logger.info("Request Method :" + "PUT");
			logger.info("Url :" + url);
			if (null == request) {
				response = RestAssured.when().put(url);
			} else {
				response = request.put(url);
			}
			break;
		default:
			logger.info("Request Method :" + "POST");
			logger.info("Url :" + url);
			if (null == request) {
				response = RestAssured.when().post(url);
			} else {
				response = request.post(url);
			}
			response = request.post(url);
			break;
		}
		return response;
	}



}
