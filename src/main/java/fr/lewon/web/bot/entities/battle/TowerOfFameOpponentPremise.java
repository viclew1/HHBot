package fr.lewon.web.bot.entities.battle;

public class TowerOfFameOpponentPremise {

	private String id;
	private String nickName;
	private int lvl;


	public TowerOfFameOpponentPremise(String id, String nickName, int lvl) {
		this.id = id;
		this.nickName = nickName;
		this.lvl = lvl;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

}
