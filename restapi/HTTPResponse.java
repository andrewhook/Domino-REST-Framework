package uk.andrewhook.restapi;


public class HTTPResponse {

	private int statusCode;
	private String contentType;
	private String body;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public HTTPResponse setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public HTTPResponse setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public String getContentType() {
		return this.contentType;
	}

	public HTTPResponse setBody(String body) {
		this.body = body;
		return this;
	}

	public String getBody() {
		return this.body;
	}
}
