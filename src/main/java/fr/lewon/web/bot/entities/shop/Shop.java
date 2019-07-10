package fr.lewon.web.bot.entities.shop;

import java.util.List;

public class Shop {

	private List<Item> books;
	private List<Item> gifts;
	
	
	public Shop(List<Item> books, List<Item> gifts) {
		this.books = books;
		this.gifts = gifts;
	}
	
	
	public List<Item> getBooks() {
		return books;
	}
	public void setBooks(List<Item> books) {
		this.books = books;
	}
	public List<Item> getGifts() {
		return gifts;
	}
	public void setGifts(List<Item> gifts) {
		this.gifts = gifts;
	}
	
}
