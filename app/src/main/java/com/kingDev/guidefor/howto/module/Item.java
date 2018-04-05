package com.kingDev.guidefor.howto.module;

public class Item {

	private String icon, title, text;
	private int id;
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(int id, String icon, String title, String text) {
		super();
		this.id=id;
		this.icon = icon;
		this.title = title;
		this.text = text;
	}
	
	public void setId(int id) {
        this.id = id;
    }
	
	public int getId() {
        return id;
    }
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
}
