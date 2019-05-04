package fr.lewon.web.bot.properties;

import fr.lewon.bot.props.BotPropertyType;
import fr.lewon.bot.props.IBotProperty;

public enum HHBotProperties implements IBotProperty {

	TROLL_WORLD("troll_world", null, false, "Troll to farm. NULL to define based on the current world.", BotPropertyType.INTEGER);

	private final String key;
	private Object value;
	private boolean needed;
	private final String description;
	private final BotPropertyType type;
	private final Object[] acceptedValues;

	private HHBotProperties(String key, Object value, boolean needed, String description, BotPropertyType type, Object... acceptedValues) {
		this.key = key;
		this.value = value;
		this.needed = needed;
		this.description = description;
		this.type = type;
		this.acceptedValues = acceptedValues;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Object[] getAcceptedValues() {
		return acceptedValues;
	}

	@Override
	public BotPropertyType getType() {
		return type;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public boolean isNeeded() {
		return needed;
	}

}
