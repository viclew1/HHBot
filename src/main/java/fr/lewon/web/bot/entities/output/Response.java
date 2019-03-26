package fr.lewon.web.bot.entities.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

	@JsonProperty
	private Boolean success;

	@JsonProperty
	private String error;

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
