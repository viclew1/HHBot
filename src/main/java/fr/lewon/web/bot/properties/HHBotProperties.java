package fr.lewon.web.bot.properties;

import fr.lewon.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.props.BotPropertyType;
import fr.lewon.bot.props.IBotPropertyDescriptorEnum;

public enum HHBotProperties implements IBotPropertyDescriptorEnum {

	TROLL_WORLD("troll_world", BotPropertyType.INTEGER, null, false, true, "Troll to farm. NULL to define based on the current world."),
	FIGHT_ENERGY_TO_KEEP("fight_energy_to_keep", BotPropertyType.INTEGER, 0, true, false, "Energy to keep when fighting trolls."),
	TOWER_ENERGY_TO_KEEP("tower_energy_to_keep", BotPropertyType.INTEGER, 0, true, false, "Energy to keep when fighting in tower of fame.");

	private final BotPropertyDescriptor descriptor;

	private HHBotProperties(String key, BotPropertyType type, Object defaultValue, boolean needed, boolean nullable, String description, Object... acceptedValues) {
		this.descriptor = new BotPropertyDescriptor(key, type, defaultValue, description, needed, nullable, acceptedValues);
	}
	
	@Override
	public BotPropertyDescriptor getDescriptor() {
		return descriptor;
	}

}
