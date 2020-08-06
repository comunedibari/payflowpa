package it.tasgroup.addon.internal;

public class AddOnInfo {
	
	private String name;
	private String codPlugin;
	private boolean configurable;
	private boolean predeterm;

	public AddOnInfo(String name, String codPlugin, boolean configurable, boolean predeterm) {
		super();
		this.name = name;
		this.codPlugin = codPlugin;
		this.configurable = configurable;
		this.predeterm = predeterm;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCodPlugin() {
		return codPlugin;
	}
	public void setCodPlugin(String codPlugin) {
		this.codPlugin = codPlugin;
	}
	public boolean isConfigurable() {
		return configurable;
	}
	public void setConfigurable(boolean configurable) {
		this.configurable = configurable;
	}
	public boolean isPredeterm() {
		return predeterm;
	}
	public void setPredeterm(boolean predeterm) {
		this.predeterm = predeterm;
	}
	
}
