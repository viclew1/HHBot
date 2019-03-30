package fr.lewon.web.bot.util;

import fr.lewon.bot.http.AbstractSessionManager;
import fr.lewon.web.bot.entities.SessionResponse;

public class HHSessionManager extends AbstractSessionManager<SessionResponse> {

	public HHSessionManager(String login, String password, Long sessionDurability) {
		super(login, password, sessionDurability);
	}

	@Override
	protected SessionResponse generateSessionObject(String login, String password) throws Exception {
		return HHRequestProcessor.INSTANCE.getSession(login, password);
	}

}
