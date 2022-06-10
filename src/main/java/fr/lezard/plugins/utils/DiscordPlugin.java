package fr.lezard.plugins.utils;

import club.minnced.discord.rpc.DiscordRPC;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.DiscordIntegration;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class DiscordPlugin extends Plugin {

	public DiscordPlugin() {
		super("Discord RPC", FileWriterJson.getBoolean("discord", "enabled"), Category.UTILS, "discord", Minecraft.getInstance().options.keyDiscord);
	}
	
	public void onEnable() {
		DiscordIntegration.init();
		DiscordRPC.INSTANCE.Discord_UpdatePresence(DiscordIntegration.presence);
	}
	
	public void onDisable() {
		DiscordRPC.INSTANCE.Discord_Shutdown();
	}
	
	public void onEvent(Event<?> e) {
		if(e instanceof EventStart) {
			DiscordIntegration.init();
		}
	}
}
