package fr.lewon.web.bot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import fr.lewon.client.AbstractAppClient;
import fr.lewon.client.exceptions.ActionException;
import fr.lewon.client.exceptions.CliException;
import fr.lewon.client.exceptions.InitializationException;
import fr.lewon.client.menus.Menu;
import fr.lewon.client.util.parameters.Parameter;
import fr.lewon.client.util.parameters.impl.DirParameter;
import fr.lewon.client.util.parameters.impl.FileParameter;
import fr.lewon.client.util.parameters.impl.SimpleParameter;
import fr.lewon.web.bot.properties.FarmProperties;
import fr.lewon.web.bot.properties.GameProperties;
import fr.lewon.web.bot.properties.ProxyProperties;
import fr.lewon.web.bot.properties.UserInfosProperties;
import fr.lewon.web.bot.util.timeline.TimeLineManager;
import fr.lewon.web.bot.util.timeline.operation.impl.InitOperationsOperation;

public class AppBot extends AbstractAppClient {

	private static Logger LOGGER;

	private static final Parameter CONF_PATH_PARAM = new FileParameter("user.infos.path", true, true);
	private static final Parameter PATH_LOGS = new DirParameter("logs.path", true, true);
	private static final Parameter LOGS_LEVEL_PARAM = new SimpleParameter("logs.level", false);
	private static final Parameter GAME_SETTINGS_PATH_PARAM = new FileParameter("game.settings.path", true, true);
	private static final Parameter PROXY_SETTINGS_PATH_PARAM = new FileParameter("proxy.settings.path", false, true);
	private static final Parameter FARM_PREFERENCES_PATH_PARAM = new FileParameter("farm.preferences", false, true);


	public static void main(String[] args) throws CliException {
		new AppBot().launch();
	}

	@Override
	protected List<Parameter> getParamsToInit() {
		List<Parameter> parameters = new ArrayList<>();
		parameters.add(PATH_LOGS);
		parameters.add(LOGS_LEVEL_PARAM);
		parameters.add(CONF_PATH_PARAM);
		parameters.add(PROXY_SETTINGS_PATH_PARAM);
		parameters.add(GAME_SETTINGS_PATH_PARAM);
		parameters.add(FARM_PREFERENCES_PATH_PARAM);
		return parameters;
	}

	@Override
	protected void initUtils() throws InitializationException {
		initLogger();
		UserInfosProperties.INSTANCE.init(CONF_PATH_PARAM.getValue());
		GameProperties.INSTANCE.init(GAME_SETTINGS_PATH_PARAM.getValue());
		String proxyPropsFile = PROXY_SETTINGS_PATH_PARAM.getValue();
		if (proxyPropsFile != null) {
			ProxyProperties.INSTANCE.init(proxyPropsFile);
		}
		String farmPrefFile = FARM_PREFERENCES_PATH_PARAM.getValue();
		if (farmPrefFile != null) {
			FarmProperties.INSTANCE.init(farmPrefFile);
		}
	}

	private void initLogger() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
		System.setProperty("log.name", PATH_LOGS.getValue() + "/" + ldt.format(dtf) + ".txt");
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.valueOf(LOGS_LEVEL_PARAM.getValue()));
		LOGGER = LoggerFactory.getLogger(AppBot.class);
	}

	@Override
	protected Menu getHomeMenu() {
		return null;
	}

	@Override
	protected void run() throws CliException {
		TimeLineManager.INSTANCE.addAction(new InitOperationsOperation(), 0);

		LOGGER.info("Starting bot");
		try {
			TimeLineManager.INSTANCE.run();
		} catch (Exception e) {
			LOGGER.error("Fatal error", e);
			throw new ActionException(e);
		}
	}


}
