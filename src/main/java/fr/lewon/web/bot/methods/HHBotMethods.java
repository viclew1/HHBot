package fr.lewon.web.bot.methods;

import fr.lewon.bot.methods.AbstractBotMethodProcessor;
import fr.lewon.bot.methods.IBotMethodEnum;
import fr.lewon.web.bot.HHBot;

public enum HHBotMethods implements IBotMethodEnum<HHBot> {

    GET_USER_INFO(new GetUserInfosMethod("GET_USER_INFO", "Get user information")),
    UPGRADE_STATS(new UpgradeStatMethod("UPGRADE_STAT", "Upgrade hero stats")),
    PROCESS_TROLL_FIGHTS(new ProcessTrollFightsMethod("PROCESS_TROLL_FIGHTS", "Fight a troll")),
    FIGHT_CHAMPION(new FightChampionMethod("FIGHT_CHAMPION", "Fight a champion until he's defeated"));

    private final HHBotProcessor botMethod;

    private HHBotMethods(HHBotProcessor botMethod) {
        this.botMethod = botMethod;
    }

    @Override
    public AbstractBotMethodProcessor<HHBot> getProcessor() {
        return this.botMethod;
    }

}
