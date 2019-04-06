package fr.lewon.web.bot.util;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.message.BasicHeader;

import fr.lewon.bot.http.AbstractSessionManager;
import fr.lewon.bot.http.DefaultResponse;
import fr.lewon.web.bot.entities.SessionResponse;

public class HHSessionManager extends AbstractSessionManager<HHRequestProcessor, SessionResponse> {

	public HHSessionManager(HHRequestProcessor requestProcessor, String login, String password, Long sessionDurability) {
		super(requestProcessor, login, password, sessionDurability);
	}

	@Override
	protected SessionResponse generateSessionObject(HHRequestProcessor requestProcessor, String login, String password) throws Exception {
		DefaultResponse<SessionResponse> response = requestProcessor.getSession(login, password);
		String hhSess = "";
		String stayOnline = "";
		for (Header h : response.getHttpResponse().getHeaders("Set-Cookie")) {
			for (HeaderElement he : h.getElements()) {
				if (he.getName().equals("HH_SESS_13")) {
					hhSess = he.getValue();
				} else if (he.getName().equals("stay_online")) {
					stayOnline = he.getValue();
				}
			}
		}
		String value = "HAPBK=web5; age_verification=1; _pk_ses.2.6e07=1; lang=fr; member_guid=A55C4849-F42D-4A1A-A6C6-11556C261A9C; HH_SESS_13=" + hhSess + "; stay_online=" + stayOnline + "; _pk_id.2.6e07=5ab4aa907c7c5919.1551984183.1.1551995205.1551984183.";
		Header cookie = new BasicHeader("Cookie", value);

		SessionResponse session = response.getEntity();
		session.setCookies(cookie);
		return session;
	}

}
