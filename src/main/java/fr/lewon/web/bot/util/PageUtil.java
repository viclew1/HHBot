package fr.lewon.web.bot.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import fr.lewon.web.bot.elements.Button;
import fr.lewon.web.bot.properties.ProxyProperties;

public enum PageUtil {

	INSTANCE;

	private int bufferSize = 1024*1024;
	private byte[] dataBuffer = new byte[bufferSize];

	public List<Button> getPageButtons(String url) throws IOException {
		return getPageButtons(new URL(url));
	}

	public List<Button> getPageButtons(URL url) throws IOException {
		String pageContent = readPage(url);
		return null;
	}

	public String readPage(String url) throws IOException {
		return readPage(new URL(url));
	}

	public String readPage(URL url) throws IOException {
		URLConnection connection = getUrlConnection(url);
		try (InputStream is = connection.getInputStream()) {

			final StringBuilder sb = new StringBuilder();

			int bytesRead = 0;
			while ((bytesRead = is.read(dataBuffer)) >= 0)
			{
				sb.append(new String(dataBuffer, 0, bytesRead));
			}

			return sb.toString();
		}
	}

	public URLConnection getUrlConnection(URL url) throws IOException {
		if (ProxyProperties.INSTANCE.getUrl() != null) {
			Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(ProxyProperties.INSTANCE.getUrl(), ProxyProperties.INSTANCE.getPort()));
			return url.openConnection(proxy);
		}
		return url.openConnection();
	}

}
