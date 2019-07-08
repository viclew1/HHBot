package fr.lewon.web.bot.entities.response;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

public class SessionResponse extends Response {

	private Header cookieHeader;
	private List<Cookie> cookies;
	

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	public Header getCookieHeader() {
		return cookieHeader;
	}

	public void setCookieHeader(Header cookie) {
		this.cookieHeader = cookie;
	}

}
