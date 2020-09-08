package net.sourcewriters.minecraft.versiontools.skin;

import org.bukkit.entity.Player;

public class SkinRequest {
	
	public SkinRequest(String name, String url, SkinModel model, Player requester) {
		this.name = name.toLowerCase();
		this.url = url.toLowerCase();
		this.model = model;
		this.requester = requester;
	}
	
	private String name;
	private String url;
	private SkinModel model;
	private Player requester;
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public SkinModel getModel() {
		return model;
	}
	
	public Player getRequester() {
		return requester;
	}
	
}
