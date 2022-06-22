package fr.lezard.plugins.utils;

import fr.lezard.events.Event;
import fr.lezard.gui.screen.plugins.DiscordPluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.DiscordIntegration;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.Minecraft;

public class DiscordPlugin extends Plugin {
	public static DiscordIntegration discord = new DiscordIntegration();

	public DiscordPlugin() {
		super("Discord RPC", FileWriterJson.getBoolean("discord", "enabled"), Category.UTILS, "discord", Minecraft.getInstance().options.keyDiscord, new DiscordPluginScreen());
	}
	
	public void onEnable() {
		/* discord.start();
		DiscordRPC.discordUpdatePresence(discord.b.build()); */
	}
	
	public void onDisable() {
		// discord.shutdown();
	}
	
	public void onEvent(Event<?> e) {
		/*if(e instanceof EventStart) {
			if(this.isEnabled()) {
				discord.start();
			}
		}*/
	}
}
