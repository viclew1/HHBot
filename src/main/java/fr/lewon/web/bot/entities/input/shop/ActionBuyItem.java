package fr.lewon.web.bot.entities.input.shop;

import fr.lewon.bot.http.body.urlencoded.FUEMember;
import fr.lewon.web.bot.entities.input.Action;
import fr.lewon.web.bot.entities.shop.Item;

public class ActionBuyItem extends Action {

	@FUEMember("id_item")
	private String idItem;
	
	@FUEMember
	private String type;
	
	@FUEMember
	private Integer who = 1;
	
	public ActionBuyItem(Item item) {
		super("Item", "buy");
		this.idItem = item.getId();
		this.type = item.getType();
	}

}
