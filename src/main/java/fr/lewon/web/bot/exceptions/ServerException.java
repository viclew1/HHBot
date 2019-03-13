package fr.lewon.web.bot.exceptions;

import fr.lewon.client.exceptions.CliException;

public class ServerException extends CliException {

	private static final long serialVersionUID = -7970506899840821801L;

	public ServerException(String url, int code, String message) {
		super(generateExceptionMessage(url, code, message));
	}

	private static String generateExceptionMessage(String url, int code, String message) {
		String msg = "";
		msg += "URL     : " + url + "\n";
		msg += "CODE    : " + code + "\n";
		msg += "MESSAGE : " + message;
		return msg;
	}

}
