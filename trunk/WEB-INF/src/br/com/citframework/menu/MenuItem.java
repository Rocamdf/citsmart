package br.com.citframework.menu;

import java.util.Collection;

public class MenuItem {
	private String description;
	private String path;
	private boolean rootLevel;
	private Collection menuItens;
	public MenuItem(){
		this.rootLevel = false;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Collection getMenuItens() {
		return menuItens;
	}
	public void setMenuItens(Collection menuItens) {
		this.menuItens = menuItens;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRootLevel() {
		return rootLevel;
	}
	public void setRootLevel(boolean rootLevel) {
		this.rootLevel = rootLevel;
	}
}
