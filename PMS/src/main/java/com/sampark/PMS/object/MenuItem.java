package com.sampark.PMS.object;

public class MenuItem {
	private String id;
	private String link;
	private String icon;
	private String title;
	private MenuItem[] chidItems;
	public MenuItem(String id,String link, String icon,String title,MenuItem[] chidItems) {
		this.title=title;
		this.id=id;
		this.link=link;
		this.icon=icon;
		this.chidItems=chidItems;
	}
	public MenuItem(String id, String link, String icon, String title) {
		super();
		this.id = id;
		this.link = link;
		this.icon = icon;
		this.title = title;
	}
	public MenuItem[] getChidItems() {
		return chidItems;
	}
	public void setChidItems(MenuItem[] chidItems) {
		this.chidItems = chidItems;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", link=" + link + ", icon=" + icon + ", title=" + title + "]";
	}
	
}
