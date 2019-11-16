package fr.lewon.web.bot;

import fr.lewon.bot.errors.BotRunnerException;
import fr.lewon.bot.runner.BotRunner;
import fr.lewon.client.AbstractParameterizedApp;
import fr.lewon.client.exceptions.ActionException;
import fr.lewon.client.exceptions.ParameterizedAppException;
import fr.lewon.client.util.parameters.Parameter;
import fr.lewon.client.util.parameters.impl.BooleanParameter;
import fr.lewon.client.util.parameters.impl.IntegerParameter;
import fr.lewon.client.util.parameters.impl.SimpleParameter;
import fr.lewon.web.bot.properties.HHBotProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HHBotApp extends AbstractParameterizedApp {

    private static final SimpleParameter LOGIN_PARAMETER = new SimpleParameter("login", true);
    private static final SimpleParameter PASSWORD_PARAMETER = new SimpleParameter("password", true);
    private static final IntegerParameter TROLL_WORLD_PARAMETER = new IntegerParameter(
            HHBotProperties.TROLL_WORLD.getDescriptor().getKey(), false);
    private static final IntegerParameter TOWER_ENERGY_TO_KEEP_PARAMETER = new IntegerParameter(
            HHBotProperties.TOWER_ENERGY_TO_KEEP.getDescriptor().getKey(), true);
    private static final IntegerParameter FIGHT_ENERGY_TO_KEEP_PARAMETER = new IntegerParameter(
            HHBotProperties.FIGHT_ENERGY_TO_KEEP.getDescriptor().getKey(), true);
    private static final BooleanParameter AUTO_UPGRADE_GIRLS_PARAMETER = new BooleanParameter(
            HHBotProperties.AUTO_UPGRADE_GIRLS.getDescriptor().getKey(), true);
    private static final BooleanParameter AUTO_FEED_GIRLS_PARAMETER = new BooleanParameter(
            HHBotProperties.AUTO_FEED_GIRLS.getDescriptor().getKey(), true);
    private static final BooleanParameter AUTO_SHOP_BOOKS_PARAMETER = new BooleanParameter(
            HHBotProperties.AUTO_SHOP_BOOKS.getDescriptor().getKey(), true);
    private static final BooleanParameter AUTO_SHOP_GIFTS_PARAMETER = new BooleanParameter(
            HHBotProperties.AUTO_SHOP_GIFTS.getDescriptor().getKey(), true);

    public static void main(String[] args) throws ParameterizedAppException {
        new HHBotApp().launch(args);
    }

    @Override
    protected List<Parameter> getParamsToInit() {
        List<Parameter> params = new ArrayList<>();
        params.add(LOGIN_PARAMETER);
        params.add(PASSWORD_PARAMETER);
        params.add(TROLL_WORLD_PARAMETER);
        params.add(TOWER_ENERGY_TO_KEEP_PARAMETER);
        params.add(FIGHT_ENERGY_TO_KEEP_PARAMETER);
        params.add(AUTO_UPGRADE_GIRLS_PARAMETER);
        params.add(AUTO_FEED_GIRLS_PARAMETER);
        params.add(AUTO_SHOP_BOOKS_PARAMETER);
        params.add(AUTO_SHOP_GIFTS_PARAMETER);
        return params;
    }

    @Override
    protected void run(String[] args) throws ParameterizedAppException {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put(HHBotProperties.TROLL_WORLD.getDescriptor().getKey(),
                    TROLL_WORLD_PARAMETER.getValue() == null ? null : Integer.valueOf(TROLL_WORLD_PARAMETER.getValue()));
            params.put(HHBotProperties.TOWER_ENERGY_TO_KEEP.getDescriptor().getKey(), Integer.valueOf(TOWER_ENERGY_TO_KEEP_PARAMETER.getValue()));
            params.put(HHBotProperties.FIGHT_ENERGY_TO_KEEP.getDescriptor().getKey(), Integer.valueOf(FIGHT_ENERGY_TO_KEEP_PARAMETER.getValue()));
            params.put(HHBotProperties.AUTO_UPGRADE_GIRLS.getDescriptor().getKey(), AUTO_UPGRADE_GIRLS_PARAMETER.getBooleanValue());
            params.put(HHBotProperties.AUTO_FEED_GIRLS.getDescriptor().getKey(), AUTO_FEED_GIRLS_PARAMETER.getBooleanValue());
            params.put(HHBotProperties.AUTO_SHOP_BOOKS.getDescriptor().getKey(), AUTO_SHOP_BOOKS_PARAMETER.getBooleanValue());
            params.put(HHBotProperties.AUTO_SHOP_GIFTS.getDescriptor().getKey(), AUTO_SHOP_GIFTS_PARAMETER.getBooleanValue());
            BotRunner runner = new HHBotRunnerBuilder().buildRunner(LOGIN_PARAMETER.getValue(), PASSWORD_PARAMETER.getValue(), params);
            runner.start();
        } catch (BotRunnerException e) {
            throw new ActionException(e);
        }
    }


}
