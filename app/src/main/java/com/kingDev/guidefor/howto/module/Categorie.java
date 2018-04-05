package com.kingDev.guidefor.howto.module;

public class Categorie {

	private String name, icon;
	private int id;

	public Categorie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categorie(int id, String name, String icon) {
		super();
		this.id=id;
		this.name = name;
		this.icon = icon;
	}
	
	public void setId(int id) {
        this.id = id;
    }
	
	public int getId() {
        return id;
    }
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String name) {
		this.icon = icon;
	}
	
}
