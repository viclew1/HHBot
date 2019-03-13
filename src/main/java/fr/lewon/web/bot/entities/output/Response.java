package fr.lewon.web.bot.entities.output;

import org.apache.http.HttpResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

	private HttpResponse httpResponse;

	@JsonProperty
	private Boolean success;

	@JsonProperty
	private String error;

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
