package fr.lewon.web.bot.properties;

import fr.lewon.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.props.IBotPropertyBuilder;

public enum HHBotProperties implements IBotPropertyBuilder {

	TROLL_WORLD("troll_world", null, false, true, "Troll to farm. NULL to define based on the current world."),
	FIGHT_ENERGY_TO_KEEP("fight_energy_to_keep", 0, true, false, "Energy to keep when fighting trolls."),
	TOWER_ENERGY_TO_KEEP("tower_energy_to_keep", 0, true, false, "Energy to keep when fighting in tower of fame.");

	private final String key;
	private final Object defaultValue;
	private final BotPropertyDescriptor descriptor;

	private HHBotProperties(String key, Object defaultValue, boolean needed, boolean nullable, String description, Object... acceptedValues) {
		this.key = key;
		this.defaultValue = defaultValue;
		this.descriptor = new BotPropertyDescriptor(description, needed, nullable, acceptedValues);
	}

	@Override
	public BotPropertyDescriptor getDescriptor() {
		return descriptor;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Object getDefaultValue() {
		return defaultValue;
	}

}
