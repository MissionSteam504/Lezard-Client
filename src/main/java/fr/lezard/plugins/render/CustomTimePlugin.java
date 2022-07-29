package fr.lezard.plugins.render;

import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.plugins.render.CustomTimePluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class CustomTimePlugin extends Plugin {
	public static float time;
	public CustomTimePlugin() {
		super("Custom Time", Utils.getPlugin("customtime").isEnabled(), Category.RENDER, "customtime", Minecraft.getInstance().options.keyDiscord, new CustomTimePluginScreen());
	}
	
	public void onEnable() {
	}
	
	public void onDisable() {
	}
	
	public void onEvent(Event<?> e) {
		if(e instanceof EventInGame) {
			ClientLevel lvl = Minecraft.getInstance().level;
			if(lvl != null) {
				lvl.getLevelData().setDayTime((int) (time*24000));
			}
		}
		if(e instanceof EventStart) {
			time = Utils.getPlugin("customtime").getSize();
		}
	}
}
