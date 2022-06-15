package fr.lezard.utils;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import fr.lezard.Lezard;
import fr.lezard.gui.screen.MainMenu;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class DiscordIntegration {
	private static final Logger LOGGER = LogUtils.getLogger();
	
	private boolean running = true;
	private long created = 0;
	
	public DiscordRichPresence.Builder b = new DiscordRichPresence.Builder("Loading");
	
	public void start() {
		this.created = System.currentTimeMillis();
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
		    LOGGER.info("Welcome " + user.username + "#" + user.discriminator + "!");
			update("Booting up...", "");
		}).build();
		
		DiscordRPC.discordInitialize(Lezard.DISCORD_APP_ID, handlers, true);
		
		new Thread(() -> {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					if(running) {
						DiscordRPC.discordRunCallbacks();
						if(Minecraft.getInstance().screen instanceof MainMenu) {
							update("Idle", "In the Title Screen");
						}
						if(Minecraft.getInstance().hasSingleplayerServer()) {
							update("In Game", "Singleplayer");
						}
						if(Minecraft.getInstance().level != null && !Minecraft.getInstance().hasSingleplayerServer()) {
							update("In Game", "Multiplayer");
						}
					}
					Thread.sleep(2000L);
				}catch(InterruptedException e) {
					System.out.println(e);
				}
			}
		}, "Discord Integration Thread").start();
	
	}

	public void shutdown() {
		running=false;
		DiscordRPC.discordShutdown();
	}
	
	public void update(String firstLine, String secondLine) {
		b = new DiscordRichPresence.Builder(secondLine);
		b.setBigImage("image", "Playing with Lezard Client");
		b.setDetails(firstLine);
		b.setStartTimestamps(created);
		DiscordRPC.discordUpdatePresence(b.build());
	}
}
