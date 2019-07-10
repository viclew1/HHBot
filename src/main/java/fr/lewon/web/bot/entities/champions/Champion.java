package fr.lewon.web.bot.entities.champions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Champion {
	
	@JsonProperty
	private Integer id;
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private Integer currentTickets;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCurrentTickets() {
		return currentTickets;
	}

	public void setCurrentTickets(Integer currentTickets) {
		this.currentTickets = currentTickets;
	}
	
}
