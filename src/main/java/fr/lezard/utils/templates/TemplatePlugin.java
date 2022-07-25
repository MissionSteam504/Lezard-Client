package fr.lezard.utils.templates;

import fr.lezard.events.Event;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.DiscordIntegration;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.Minecraft;

public class TemplatePlugin extends Plugin {
	public static DiscordIntegration discord = new DiscordIntegration();

	public TemplatePlugin() {
		super("Template Plugin", FileWriterJson.getBoolean("template", "enabled"), Category.UTILS, "template", Minecraft.getInstance().options.unknown, new TemplateScreen());
	}
	
	public void onEnable() {
	}
	
	public void onDisable() {
	}
	
	public void onEvent(Event<?> e) {
		
	}
}
