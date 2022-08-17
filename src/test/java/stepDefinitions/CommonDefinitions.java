package stepDefinitions;

import endpoints.Base;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CommonDefinitions {
	
	private Util util;
	private Base be = new Base();

	public CommonDefinitions() {

	}

	public CommonDefinitions(Util util) {
		this.util = util;
	}

}
