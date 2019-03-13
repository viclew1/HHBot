package fr.lewon.web.bot.util;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lewon.web.bot.exceptions.ServerException;
import fr.lewon.web.bot.properties.ProxyProperties;

enum RequestHelper implements AutoCloseable {

	INSTANCE;

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHelper.class);

	private DefaultHttpClient client;

	private RequestHelper() {
		client = new DefaultHttpClient();
		if (ProxyProperties.INSTANCE.getUrl() != null) {
			String proxyUrl = ProxyProperties.INSTANCE.getUrl();
			int proxyPort = ProxyProperties.INSTANCE.getPort();
			HttpHost proxy = new HttpHost(proxyUrl, proxyPort);
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);
		}
	}

	private HttpResponse processRequest(HttpUriRequest request, Header... additionalHeaders) throws IOException, ServerException {
		if (additionalHeaders != null) {
			for (Header h : additionalHeaders) {
				request.addHeader(h);
			}
		}
		addNeededHeaders(request);
		LOGGER.debug("Request  : " + request.getURI().toString());
		HttpResponse response = client.execute(request);

		int code = response.getStatusLine().getStatusCode();

		if (code < HttpURLConnection.HTTP_MULT_CHOICE) {
			return response;
		} else {
			String url = request.getURI().toString();
			byte[] errorMessageByte = new byte[1024*1024];
			int length = response.getEntity().getContent().read(errorMessageByte);
			String errorMsgText = new String(errorMessageByte, 0, length);
			throw new ServerException(url, code, errorMsgText);
		}
	}



	private void addNeededHeaders(HttpUriRequest request) {
		request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		request.addHeader("Accept-Language", "fr-FR,fr;q=0.9,en-GB;q=0.8,en;q=0.7,en-US;q=0.6");
		request.addHeader("Cache-Control", "max-age=0");
		request.addHeader("Connection", "keep-alive");
		request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		request.addHeader("Host", "www.hentaiheroes.com");
		request.addHeader("Upgrade-Insecure-Requests", "1");
		request.addHeader("Origin", "https://www.hentaiheroes.com");
		request.addHeader("Referer", "https://www.hentaiheroes.com/harem/1");
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
	}

	public HttpResponse processPostRequest(String url, String input, Header... additionalHeaders) throws IOException, ServerException {
		HttpPost postRequest = new HttpPost(url);
		if (input != null && !"".equals(input)) {
			StringEntity entity = new StringEntity(input);
			entity.setContentType("application/json");
			postRequest.setEntity(entity);
		}
		LOGGER.debug("Body : {}", input);
		return processRequest(postRequest, additionalHeaders);
	}

	public HttpResponse processGetRequest(String url, Header... additionalHeaders) throws IOException, ServerException {
		HttpGet getRequest = new HttpGet(url);
		return processRequest(getRequest, additionalHeaders);
	}

	public HttpResponse processPutRequest(String url, String input, Header... additionalHeaders) throws IOException, ServerException {
		HttpPut putRequest = new HttpPut(url);
		if (input != null && !"".equals(input)) {
			StringEntity entity = new StringEntity(input);
			entity.setContentType("application/json");
			putRequest.setEntity(entity);
		}
		LOGGER.debug("Body : {}", input);
		return processRequest(putRequest, additionalHeaders);
	}

	public HttpResponse processDeleteRequest(String url, Header... additionalHeaders) throws IOException, ServerException {
		HttpDelete deleteRequest = new HttpDelete(url);
		return processRequest(deleteRequest, additionalHeaders);
	}

	@Override
	public void close() throws Exception {
		client.getConnectionManager().shutdown();
	}

}
