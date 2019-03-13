package fr.lewon.web.bot.properties;

import java.util.Arrays;
import java.util.List;

import fr.lewon.client.preferences.AbstractPropertyStore;

public class FarmProperties extends AbstractPropertyStore {

	public static final FarmProperties INSTANCE = new FarmProperties();

	private static final String WORLD_ID_KEY = "world_id";

	private FarmProperties() {}


	@Override
	protected List<String> getNeededKeys() {
		return Arrays.asList(WORLD_ID_KEY);
	}


	public String getWorldId() {
		return getProperties().getProperty(WORLD_ID_KEY);
	}

}
