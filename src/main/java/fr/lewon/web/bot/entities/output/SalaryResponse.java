package fr.lewon.web.bot.entities.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SalaryResponse extends Response {

	@JsonProperty
	private Integer time;

	@JsonProperty
	private Integer money;


	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

}
