package fr.lewon.web.bot.methods.outputs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoneyOutput {

	@JsonProperty
	private Integer golds;

	@JsonProperty
	private Integer rubies;

	public MoneyOutput(Integer golds, Integer rubies) {
		this.golds = golds;
		this.rubies = rubies;
	}

}
