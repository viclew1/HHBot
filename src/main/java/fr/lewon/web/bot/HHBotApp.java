package fr.lewon.web.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.client.AbstractParameterizedApp;
import fr.lewon.client.exceptions.ActionException;
import fr.lewon.client.exceptions.ParameterizedAppException;
import fr.lewon.client.util.parameters.Parameter;
import fr.lewon.client.util.parameters.impl.IntegerParameter;
import fr.lewon.client.util.parameters.impl.SimpleParameter;
import fr.lewon.web.bot.properties.HHBotProperties;

public class HHBotApp extends AbstractParameterizedApp {

	private static final Parameter LOGIN_PARAMETER = new SimpleParameter("login", true);
	private static final Parameter PASSWORD_PARAMETER = new SimpleParameter("password", true);
	private static final Parameter TROLL_WORLD_PARAMETER = new IntegerParameter("troll_world", false);
	private static final Parameter TOWER_ENERGY_TO_KEEP_PARAMETER = new IntegerParameter("tower_energy_to_keep", true);
	private static final Parameter FIGHT_ENERGY_TO_KEEP_PARAMETER = new IntegerParameter("fight_energy_to_keep", true);
	
	@Override
	protected List<Parameter> getParamsToInit() {
		List<Parameter> params = new ArrayList<>();
		params.add(LOGIN_PARAMETER);
		params.add(PASSWORD_PARAMETER);
		params.add(TROLL_WORLD_PARAMETER);
		params.add(TOWER_ENERGY_TO_KEEP_PARAMETER);
		params.add(FIGHT_ENERGY_TO_KEEP_PARAMETER);
		return params;
	}

	@Override
	protected void run() throws ParameterizedAppException {
		try {
			BotRunner runner = new HHBotRunnerBuilder().buildRunner(LOGIN_PARAMETER.getValue(), PASSWORD_PARAMETER.getValue());
			Map<String, Object> params = new HashMap<>();
			params.put(HHBotProperties.TROLL_WORLD.getDescriptor().getKey(), 
					TROLL_WORLD_PARAMETER.getValue() == null ? null : Integer.valueOf(TROLL_WORLD_PARAMETER.getValue()));
			params.put(HHBotProperties.TOWER_ENERGY_TO_KEEP.getDescriptor().getKey(), Integer.valueOf(TOWER_ENERGY_TO_KEEP_PARAMETER.getValue()));
			params.put(HHBotProperties.FIGHT_ENERGY_TO_KEEP.getDescriptor().getKey(), Integer.valueOf(FIGHT_ENERGY_TO_KEEP_PARAMETER.getValue()));
			runner.init(params);
			runner.start();
		} catch (BotRunnerException e) {
			throw new ActionException(e);
		}
	}
	
	public static void main(String[] args) throws ParameterizedAppException {
		new HHBotApp().launch();
	}

	
}
