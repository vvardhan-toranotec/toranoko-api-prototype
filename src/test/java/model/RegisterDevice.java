package model;

public class RegisterDevice {

	private String deviceId;
	private String password;
	private String email;

	public RegisterDevice(String deviceId, String password, String email) {
		this.deviceId=deviceId;
		this.password=password;
		this.email=email;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
