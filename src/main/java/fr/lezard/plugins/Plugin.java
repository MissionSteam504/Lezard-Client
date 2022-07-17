package fr.lezard.plugins;

import fr.lezard.events.Event;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;

public class Plugin {
	private String name;
	private boolean enabled;
	private Category category;
	private String namespace;
	private KeyMapping key;
	private Screen screen;
	
	public Plugin(String name, boolean enabled, Category category, String namespace, KeyMapping key, Screen screen) {
		this.name = name;
		this.enabled = enabled;
		this.category = category;
		this.namespace = namespace;
		this.key = key;
		this.screen = screen;
	}
	
	public Screen getScreen() {
		return screen;
	}

	public void onEvent(Event<?> e) {
		
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void toggle() {
		enabled = !enabled;
		if(enabled) {
			onEnable();
		}
		else {
			onDisable();
		}
		FileWriterJson.writeJson(namespace, "enabled", enabled);
	}
	
	public Category getCategory() {
		return category;
	}
	
	public String getName() {
		return name;
	}
	
	public KeyMapping getKey() {
		return key;
	}
	
	public String getNamespace() {
		return namespace;
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	public enum Category{
		HUD("HUD"),
		MOVEMENT("Movement"),
		RENDER("Render"),
		UTILS("Utils"),
		PLAYER("Player");
		
		public String name;
		public int pluginIndex;
		
		Category(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public String getNamespace() {
			return name.toLowerCase();
		}
	}
}
