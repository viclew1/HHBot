package fr.lewon.web.bot.properties;

import java.util.Arrays;
import java.util.List;

import fr.lewon.client.preferences.AbstractPropertyStore;

public class UserInfosProperties extends AbstractPropertyStore {

	public static final UserInfosProperties INSTANCE = new UserInfosProperties();

	private static final String LOGIN_KEY = "login";
	private static final String PASSWORD_KEY = "password";

	private UserInfosProperties() {}

	@Override
	protected List<String> getNeededKeys() {
		return Arrays.asList(LOGIN_KEY, PASSWORD_KEY);
	}

	public String getLogin() {
		return getProperties().getProperty(LOGIN_KEY);
	}

	public String getPassword() {
		return getProperties().getProperty(PASSWORD_KEY);
	}

}
