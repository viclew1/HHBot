package fr.lewon.web.bot.util;

import java.io.IOException;

import fr.lewon.web.bot.entities.output.SessionResponse;
import fr.lewon.web.bot.exceptions.ServerException;
import fr.lewon.web.bot.properties.UserInfosProperties;

public enum SessionManager {

	INSTANCE;

	private static final int MAX_TRIES = 7;

	private SessionResponse session;
	private Long lastGenerationTime;

	public SessionResponse getSession() throws Exception {

		if (session != null) {
			Long timeMsSinceGeneration = System.currentTimeMillis() - lastGenerationTime;
			if (timeMsSinceGeneration < 12 * 3600 * 1000) {
				return session;
			}
		}

		int timesTried = 0;
		Exception error = null;
		while (timesTried++ < MAX_TRIES) {
			try {
				session = RequestProcessor.INSTANCE.getSession(UserInfosProperties.INSTANCE.getLogin(), UserInfosProperties.INSTANCE.getPassword());
				lastGenerationTime = System.currentTimeMillis();
				return session;
			} catch (IOException | ServerException e) {
				error = e;
			}
		}
		throw error;
	}

}
