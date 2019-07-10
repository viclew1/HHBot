package fr.lewon.web.bot.entities.shop;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

	@JsonProperty("id_item")
	private String id;
	
	@JsonProperty
	private String type;
	
	@JsonProperty
	private Integer price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
}
