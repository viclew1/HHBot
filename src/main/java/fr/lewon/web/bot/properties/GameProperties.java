package fr.lewon.web.bot.properties;

import java.util.Arrays;
import java.util.List;

import fr.lewon.client.preferences.AbstractPropertyStore;

public class GameProperties extends AbstractPropertyStore {

	private static final String URL_KEY  = "url";

	public static final GameProperties INSTANCE = new GameProperties();

	private GameProperties() {}

	@Override
	protected List<String> getNeededKeys() {
		return Arrays.asList(URL_KEY);
	}

	public String getUrl() {
		return getProperties().getProperty(URL_KEY);
	}


}
