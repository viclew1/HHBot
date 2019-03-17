package fr.lewon.web.bot.properties;

import java.util.Arrays;
import java.util.List;

import fr.lewon.client.preferences.AbstractPropertyStore;

public class FarmProperties extends AbstractPropertyStore {

	public static final FarmProperties INSTANCE = new FarmProperties();

	private static final String TROLL_WORLD_KEY = "troll_world";

	private FarmProperties() {}


	@Override
	protected List<String> getNeededKeys() {
		return Arrays.asList(TROLL_WORLD_KEY);
	}


	public String getTrollWorld() {
		return getProperties().getProperty(TROLL_WORLD_KEY);
	}

}
