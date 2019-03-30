package fr.lewon.web.bot.entities.girls;

import com.fasterxml.jackson.annotation.JsonProperty;

public class XpInfos {

	@JsonProperty
	private Integer cur;
	@JsonProperty
	private Integer min;
	@JsonProperty
	private Integer max;
	@JsonProperty
	private Integer level;
	@JsonProperty
	private Integer left;


	public Integer getCur() {
		return cur;
	}
	public void setCur(Integer cur) {
		this.cur = cur;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getLeft() {
		return left;
	}
	public void setLeft(Integer left) {
		this.left = left;
	}

}
