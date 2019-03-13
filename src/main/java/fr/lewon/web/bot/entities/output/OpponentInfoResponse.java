package fr.lewon.web.bot.entities.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpponentInfoResponse extends Response {

	@JsonProperty
	private String html;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
