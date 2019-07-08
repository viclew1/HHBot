package fr.lewon.web.bot.entities.enums;

public enum WinnerType {
	
	HERO("hero"),
	CHAMPION("champion");
	
	private final String strValue;
	
	private WinnerType(String strValue) {
		this.strValue = strValue;
	}

	public String getStrValue() {
		return strValue;
	}
	
	public static WinnerType findByStrValue(String strValue) {
		for (WinnerType wt : values()) {
			if (wt.strValue.equals(strValue)) {
				return wt;
			}
		}
		return null;
	}
	
}