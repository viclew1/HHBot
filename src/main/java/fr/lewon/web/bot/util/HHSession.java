package fr.lewon.web.bot.util;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

public class HHSession {

	private Header cookieHeader;
	private List<Cookie> cookies;
	
	
	public HHSession(Header cookieHeader, List<Cookie> cookies) {
		this.cookieHeader = cookieHeader;
		this.cookies = cookies;
	}

	
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
