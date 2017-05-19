package uk.andrewhook.restapi;

import com.google.gson.Gson;

public class ResponseMessage {

	private String message;
	private Integer statusCode;

	public ResponseMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getMessage() {
		return this.message;
	}

	public ResponseMessage setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public Integer getStatusCode() {
		return this.statusCode;
	}

	public String asJson() {
		Gson gson = new Gson();
		String data = gson.toJson(this);

		return data;
	}
}
