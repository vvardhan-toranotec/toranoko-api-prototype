package model;

public class LoginAuthenticateMSUser {

	private String deviceId;
	private String password;

	public LoginAuthenticateMSUser(String deviceId, String password) {
		this.deviceId=deviceId;
		this.password=password;
	}

	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
