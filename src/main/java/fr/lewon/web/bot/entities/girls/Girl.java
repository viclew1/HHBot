package fr.lewon.web.bot.entities.girls;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Girl {

	@JsonProperty("id_girl")
	private Integer id;

	@JsonProperty("class")
	private Integer fightClass;

	@JsonProperty
	private String rarity;

	@JsonProperty
	private Integer level;

	@JsonProperty
	private Boolean own;

	@JsonProperty("Xp")
	private XpInfos xp;

	@JsonProperty("Affection")
	private AffectionInfos affection;

	@JsonProperty("can_upgrade")
	private boolean canUpgrade;

	@JsonProperty("pay_in")
	private Integer payIn;

	@JsonProperty
	private GirlQuests quests;
	
	@JsonProperty
	private Integer damage;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFightClass() {
		return fightClass;
	}

	public void setFightClass(Integer fightClass) {
		this.fightClass = fightClass;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getOwn() {
		return own;
	}

	public void setOwn(Boolean own) {
		this.own = own;
	}

	public XpInfos getXp() {
		return xp;
	}

	public void setXp(XpInfos xp) {
		this.xp = xp;
	}

	public AffectionInfos getAffection() {
		return affection;
	}

	public void setAffection(AffectionInfos affection) {
		this.affection = affection;
	}

	public boolean isCanUpgrade() {
		return canUpgrade;
	}

	public void setCanUpgrade(boolean canUpgrade) {
		this.canUpgrade = canUpgrade;
	}

	public Integer getPayIn() {
		return payIn;
	}

	public void setPayIn(Integer payIn) {
		this.payIn = payIn;
	}

	public GirlQuests getQuests() {
		return quests;
	}

	public void setQuests(GirlQuests quests) {
		this.quests = quests;
	}

	public Integer getDamage() {
		return damage;
	}

	public void setDamage(Integer damage) {
		this.damage = damage;
	}

}
