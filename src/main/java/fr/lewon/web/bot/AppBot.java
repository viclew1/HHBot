package fr.lewon.web.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.lewon.client.AbstractAppClient;
import fr.lewon.client.exceptions.ActionException;
import fr.lewon.client.exceptions.CliException;
import fr.lewon.client.exceptions.InitializationException;
import fr.lewon.client.menus.Menu;
import fr.lewon.client.util.parameters.Parameter;
import fr.lewon.client.util.parameters.impl.FileParameter;
import fr.lewon.web.bot.properties.ProxyProperties;
import fr.lewon.web.bot.util.PageUtil;

public class AppBot extends AbstractAppClient {
	
	private static final Parameter CONF_PATH_PARAM = new FileParameter("conf.path", false, true);
	private static final Parameter PROXY_SETTINGS_PATH_PARAM = new FileParameter("proxy.settings.path", false, true);
	
	public static void main(String[] args) throws CliException {
		new AppBot().launch();
	}

	@Override
	protected List<Parameter> getParamsToInit() {
		List<Parameter> parameters = new ArrayList<>();
		parameters.add(CONF_PATH_PARAM);
		parameters.add(PROXY_SETTINGS_PATH_PARAM);
		return parameters;
	}

	@Override
	protected void initUtils() throws InitializationException {
		String confPath = CONF_PATH_PARAM.getValue();
		String proxyPropsFile = PROXY_SETTINGS_PATH_PARAM.getValue();
		if (proxyPropsFile != null) {
			ProxyProperties.INSTANCE.init(proxyPropsFile);
		}
	}

	@Override
	protected Menu getHomeMenu() {
		return null;
	}

	@Override
	protected void run() throws CliException {
		try {
			String pageContent = PageUtil.INSTANCE.readPage("https://stackoverflow.com/questions/5867975/reading-websites-contents-into-string");
			System.out.println(pageContent);
		} catch (IOException e) {
			throw new ActionException(e);
		}
	}

}
