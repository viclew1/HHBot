package fr.lewon.web.bot.util;

import java.io.IOException;

import fr.lewon.bot.errors.ServerException;
import fr.lewon.web.bot.entities.output.SessionResponse;

public class SessionManager {

	private static final int MAX_TRIES = 7;

	private SessionResponse session;
	private Long lastGenerationTime;
	private String login;
	private String password;

	public SessionManager(String login, String password) {
		this.login = login;
		this.password = password;
	}

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
				session = HHRequestProcessor.INSTANCE.getSession(login, password);
				lastGenerationTime = System.currentTimeMillis();
				return session;
			} catch (IOException | ServerException e) {
				error = e;
			}
		}
		throw error;
	}

}
