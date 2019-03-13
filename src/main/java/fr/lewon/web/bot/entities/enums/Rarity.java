package fr.lewon.web.bot.entities.enums;

public enum Rarity {

	COMMON("common", 1),
	RARE("rare", 2),
	EPIC("epic", 3),
	LEGENDARY("legendary", 4);

	private final String label;
	private final Integer value;

	private Rarity(String label, Integer value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public Integer getValue() {
		return value;
	}

	public static Rarity getValueByLabel(String label) {
		for (Rarity r : values()) {
			if (r.getLabel().equals(label)) {
				return r;
			}
		}
		return null;
	}
}
