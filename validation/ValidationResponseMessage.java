package uk.andrewhook.validation;

import java.util.ArrayList;

import com.google.gson.Gson;

public class ValidationResponseMessage {

	private String message;
	private Integer statusCode;
	private ArrayList<String> errors;

	public ValidationResponseMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getMessage() {
		return this.message;
	}

	public ValidationResponseMessage setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
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
