package fr.lezard.plugins.render;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.gui.screen.plugins.render.RealTimePluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class RealTimePlugin extends Plugin {
	public RealTimePlugin() {
		super("RealTime", Utils.getPlugin("realtime").isEnabled(), Category.RENDER, "realtime", Minecraft.getInstance().options.keyDiscord, new RealTimePluginScreen());
	}
	
	public void onEnable() {
	}
	
	public void onDisable() {
	}
	
	public void onEvent(Event<?> e) {
		if(e instanceof EventInGame) {
			ClientLevel lvl = Minecraft.getInstance().level;
			if(lvl != null) {
				Calendar calendar = new GregorianCalendar();
				
				int secs = calendar.get(Calendar.SECOND);
		        int minutes = calendar.get(Calendar.MINUTE);
		        int hours = calendar.get(Calendar.HOUR_OF_DAY);
		        
		        int gameHours = (hours*1000)-6000;
		        int gameMinutes = ((100*minutes)/60)*10;
		        int gameSeconds = (10*secs)/60;
		        int time = gameHours+gameMinutes+gameSeconds;
		        
		        //System.out.println((int) Math.ceil(34.2f));
		        
				lvl.getLevelData().setDayTime(time);
			}
		}
	}
}
