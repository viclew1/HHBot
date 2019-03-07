package fr.lewon.web.bot.properties;

import java.util.Arrays;
import java.util.List;

import fr.lewon.client.preferences.AbstractPropertyStore;

public class ProxyProperties extends AbstractPropertyStore {
	
	private static final String PROXY_URL_KEY  = "proxy_url";
	private static final String PROXY_PORT_KEY = "proxy_port";

	public static final ProxyProperties INSTANCE = new ProxyProperties();
	
	private ProxyProperties() {}
	
	@Override
	protected List<String> getNeededKeys() {
		return Arrays.asList(PROXY_URL_KEY, PROXY_PORT_KEY);
	}
	
	public String getUrl() {
		return getProperties().getProperty(PROXY_URL_KEY);
	}
	
	public int getPort() {
		return Integer.parseInt(getProperties().getProperty(PROXY_PORT_KEY));
	}

}
