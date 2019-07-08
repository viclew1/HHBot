package fr.lewon.web.bot.entities.champions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Winner {
	
	@JsonProperty
	String type;
	@JsonProperty
	Integer id;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}