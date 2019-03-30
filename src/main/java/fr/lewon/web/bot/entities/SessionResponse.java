package fr.lewon.web.bot.entities;

import org.apache.http.Header;

public class SessionResponse extends Response {

	private Header cookie;

	public Header getCookies() {
		return cookie;
	}

	public void setCookies(Header cookie) {
		this.cookie = cookie;
	}

}
