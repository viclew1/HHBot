package fr.lewon.web.bot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

import fr.lewon.bot.http.AbstractSessionManager;

public class HHSessionManager extends AbstractSessionManager<HHRequestProcessor, HHSession> {

	public HHSessionManager(String login, String password, Long sessionDurability) {
		super(login, password, sessionDurability);
	}

	@Override
	protected HHSession generateSessionObject(HHRequestProcessor requestProcessor, String login, String password) throws Exception {
		HttpResponse response = requestProcessor.getSession(login, password);

		List<HeaderElement> cookieHeaderElements = Stream.of(response.getAllHeaders())
				.filter(h -> h.getName().equalsIgnoreCase("Set-Cookie"))
				.map(Header::getElements)
				.flatMap(hes -> Stream.of(hes))
				.collect(Collectors.toList());

		HeaderElement hhSessHE = findHeaderElementByName(cookieHeaderElements, "HH_SESS_13");
		String hhSess = hhSessHE == null ? "" : hhSessHE.getValue();

		HeaderElement stayOnlineHE = findHeaderElementByName(cookieHeaderElements, "stay_online");
		String stayOnline = stayOnlineHE == null ? "" : stayOnlineHE.getValue();

		System.out.println(hhSess);
		
		Map<String, String> cookiePremises = new HashMap<>();
		cookiePremises.put("HAPBK", "web5");
		cookiePremises.put("age_verification", "1");
		cookiePremises.put("_pk_ses.2.6e07", "1");
		cookiePremises.put("lang", "fr");
		cookiePremises.put("member_guid", "A55C4849-F42D-4A1A-A6C6-11556C261A9C");
		cookiePremises.put("HH_SESS_13", hhSess);
		cookiePremises.put("stay_online", stayOnline);
		cookiePremises.put("_pk_id.2.6e07", "cda6c741be7d8090.1562523528.2.1562529541.1562528524.");

		String cookieHeaderValue = "";
		List<Cookie> cookies = new ArrayList<>();
		for (Entry<String, String> entry : cookiePremises.entrySet()) {
			cookies.add(new BasicClientCookie(entry.getKey(), entry.getValue()));
			cookieHeaderValue += entry.getKey() + "=" + entry.getValue() + "; ";
		}

		Header cookieHeader = new BasicHeader("Cookie", cookieHeaderValue);

		return new HHSession(cookieHeader, cookies);
	}

	private HeaderElement findHeaderElementByName(List<HeaderElement> headerElements, String headerElementName) {
		return headerElements.stream()
				.filter(he -> he.getName().equals(headerElementName))
				.findFirst()
				.orElse(null);
	}

}
