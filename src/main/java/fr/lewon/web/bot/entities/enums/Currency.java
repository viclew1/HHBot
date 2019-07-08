package fr.lewon.web.bot.entities.enums;

public enum Currency {

	TICKET("ticket");
	
	private final String strValue;
	
	private Currency(String strValue) {
		this.strValue = strValue;
	}

	public String getStrValue() {
		return strValue;
	}
	
}
