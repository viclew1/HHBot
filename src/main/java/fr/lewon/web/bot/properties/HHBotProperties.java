package fr.lewon.web.bot.properties;

import fr.lewon.bot.props.BotPropertyDescriptor;
import fr.lewon.bot.props.BotPropertyType;
import fr.lewon.bot.props.IBotPropertyDescriptorEnum;

public enum HHBotProperties implements IBotPropertyDescriptorEnum {

	TROLL_WORLD("troll_world", BotPropertyType.INTEGER, null, false, true, "World containing the troll to farm. NULL to define based on the current world."),
	FIGHT_ENERGY_TO_KEEP("fight_energy_to_keep", BotPropertyType.INTEGER, 0, true, false, "Energy to keep when fighting trolls."),
	TOWER_ENERGY_TO_KEEP("tower_energy_to_keep", BotPropertyType.INTEGER, 0, true, false, "Energy to keep when fighting in tower of fame."), 
	AUTO_FEED_GIRLS("auto_feed_girls", BotPropertyType.BOOLEAN, true, true, false, "If true, the girls will be automatically fed with presents.", true, false),
	AUTO_UPGRADE_GIRLS("auto_upgrade_girls", BotPropertyType.BOOLEAN, true, true, false, "If true, the girls will be automatically upgraded once you can buy the upgrade.", true, false),
	AUTO_SHOP_BOOKS("auto_shop_books", BotPropertyType.BOOLEAN, true, true, false, "If true, the books in the store will be automatically bought.", true, false),
	AUTO_SHOP_GIFTS("auto_shop_gifts", BotPropertyType.BOOLEAN, true, true, false, "If true, the gifts in the store will be automatically bought.", true, false);

	private final BotPropertyDescriptor descriptor;

	private HHBotProperties(String key, BotPropertyType type, Object defaultValue, boolean needed, boolean nullable, String description, Object... acceptedValues) {
		this.descriptor = new BotPropertyDescriptor(key, type, defaultValue, description, needed, nullable, acceptedValues);
	}
	
	@Override
	public BotPropertyDescriptor getDescriptor() {
		return descriptor;
	}

}
