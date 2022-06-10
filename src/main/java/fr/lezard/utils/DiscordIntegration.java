package fr.lezard.utils;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import fr.lezard.Lezard;
import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsScreen;

public class DiscordIntegration {
	public static DiscordRichPresence presence = new DiscordRichPresence();
	
	public static void initPresence() {
		presence.startTimestamp = System.currentTimeMillis() / 1000;
		presence.largeImageKey = "image";
		presence.largeImageText = "Playing with Lezard";
		presence.details = "Loading...";
		presence.state = "Version: v" + Lezard.VERSION;
	}
	
	public static void init() {
		DiscordRPC discord = DiscordRPC.INSTANCE;
		String appId = Lezard.DISCORD_APP_ID;
		String steamId= "";
		
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		discord.Discord_Initialize(appId, handlers, true, steamId);
		
		if(presence.state!= "Version: v" + Lezard.VERSION)
			initPresence();
		
		discord.Discord_UpdatePresence(presence);
		
		new Thread(() -> {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					if(Minecraft.getInstance().hasSingleplayerServer()) {
						presence.details = "Singleplayer";
						discord.Discord_UpdatePresence(presence);
					} else if(!Minecraft.getInstance().hasSingleplayerServer() && Minecraft.getInstance().getCurrentServer() != null) {
						presence.details = "Multiplayer";
						discord.Discord_UpdatePresence(presence);
					} else if(Minecraft.getInstance().isConnectedToRealms() || Minecraft.getInstance().screen instanceof RealmsScreen){
						presence.details = "In Realms";
						discord.Discord_UpdatePresence(presence);
					} else {
						presence.details = "In the menus";
						discord.Discord_UpdatePresence(presence);
					}
					Thread.sleep(2000L);
				} catch(InterruptedException e) {
					System.out.println(e);
				}
			}
		}, "Discord Integration Thread").start();
	}
}
