package fr.lezard;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;

import fr.lezard.events.Event;
import fr.lezard.events.EventType;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventKey;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.plugins.Plugin.Category;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.plugins.hud.CompassPluginHUD;
import fr.lezard.plugins.hud.FPSPluginHUD;
import fr.lezard.plugins.hud.IRLTimePluginHUD;
import fr.lezard.plugins.hud.InGameTimePluginHUD;
import fr.lezard.plugins.hud.TabHUD;
import fr.lezard.plugins.movement.KeyStrokePluginHUD;
import fr.lezard.plugins.player.ArmorPluginHUD;
import fr.lezard.plugins.player.CPSPluginHUD;
import fr.lezard.plugins.player.CoordsPluginHUD;
import fr.lezard.plugins.render.ItemPhysicsPlugin;
import fr.lezard.plugins.utils.DiscordPlugin;
import fr.lezard.plugins.utils.SimplifiedDebugPlugin;
import fr.lezard.plugins.render.FullBrightPlugin;
import fr.lezard.utils.DiscordIntegration;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOption;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

public class Lezard {
	// Main class of the client
	// Remember to set IDE_CLIENT to false
	// And to add the discord rpc lib in the jar
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
	public static final String NAME = "Lezard Client";
	public static final String NAMESPACE = "lezard";
	public static final String VERSION = "2.0.0-alpha2";
	public static final String DISCORD_APP_ID = "971435977199464528";
	public static final String USERNAME = "LezardUser";
	public static final String PREFIX = "[LezardClient] ";
	
	public static Color color = new Color(0, 0, 0, LezardOption.alpha);
	
	public static File pluginFile = new File(Minecraft.getInstance().gameDirectory, NAMESPACE + "-settings.json");
	
	public static final boolean IDE_CLIENT = true;
	
	public static CopyOnWriteArrayList<Plugin> plugins = new CopyOnWriteArrayList<Plugin>();
	public static CopyOnWriteArrayList<PluginHUD> pluginsHUD = new CopyOnWriteArrayList<PluginHUD>();
	
	public static void launch() {
		LOGGER.info(PREFIX + "Starting client...");
		
		Minecraft.getInstance().getWindow().setTitle(NAME + " " + VERSION + " | " + Minecraft.getInstance().getUser().getName());
		
		if(!pluginFile.exists()){
            try {
                pluginFile.createNewFile();
                PrintWriter printWriter = new PrintWriter(pluginFile);
                printWriter.write("{\"" + NAMESPACE + ".alpha\": 95, \"" + NAMESPACE + ".gap\": 4,  \"" + NAMESPACE + ".mainmenu\":true, \"discord.enabled\": true}");
                printWriter.close();
            }catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
		
		plugins.add(new ItemPhysicsPlugin());
		plugins.add(new FPSPluginHUD());
		plugins.add(new FullBrightPlugin());
		plugins.add(new TabHUD());
		plugins.add(new DiscordPlugin());
		plugins.add(new SimplifiedDebugPlugin());
		plugins.add(new InGameTimePluginHUD());
		plugins.add(new IRLTimePluginHUD());
		plugins.add(new CompassPluginHUD());
		plugins.add(new KeyStrokePluginHUD());
		plugins.add(new ArmorPluginHUD());
		plugins.add(new CoordsPluginHUD());
		plugins.add(new CPSPluginHUD());
		
		
		for(Plugin p : plugins) {
			if(p instanceof PluginHUD pHud) {
				pluginsHUD.add(pHud);
			}
		}
		
		LezardOption.alpha = FileWriterJson.getInt(NAMESPACE, "alpha");
		LezardOption.gap = FileWriterJson.getInt(NAMESPACE, "gap");
		LezardOption.customMainMenu = FileWriterJson.getBoolean(NAMESPACE, "mainmenu");
		
		color = new Color(0, 0, 0, LezardOption.alpha);
		 
		onEvent(new EventStart());
		
		LOGGER.info(PREFIX + "Client setup finished!");
	}
	
	public static void onEvent(Event<?> e) {
		for(Plugin p : plugins) {
			if(!p.isEnabled())
				continue;
			
			p.onEvent(e);
		}
		if(e instanceof EventInGame) {
			color = new Color(0, 0, 0, LezardOption.alpha);
		}
	}
	
	public static void checkInputs() {
		Options options = Minecraft.getInstance().options;
		
		KeyMapping[] keys = { options.arrowUp, options.arrowDown, options.arrowLeft, options.arrowRight, options.enter };
		KeyMapping[] customKeys = { options.keyLezardSettings, options.keyFullbright, options.keyFpsHud, options.keyTabHud, options.keyItemPhysics, options.keyDiscord};
		
		for(KeyMapping k : customKeys) {
			for(Plugin p : plugins) {
				if(p.getKey() == k) {
					while(p.getKey().consumeClick()) {
						p.toggle();
					}
				}
			}
			if(k == options.keyLezardSettings) {
				if(k.consumeClick()) {
					Minecraft.getInstance().setScreen(new DragScreen());
				}
			}
		}
		
		for(KeyMapping k : keys) {
			if(k.consumeClick()) {
				onEvent(new EventKey(k));
			}
		}
	}
	
	public static List<Plugin> getPluginsByCategory(Category c){
		List<Plugin> plugins = new ArrayList<Plugin>();
		for(Plugin p : Lezard.plugins) {
			if(p.getCategory() == c) {
				plugins.add(p);
			}
		}
		
		return plugins;
	}
	
	public static int rainbowText(){
        return Color.HSBtoRGB((float) (System.currentTimeMillis() % 5000L) / 5000F, 0.8f, 0.8f);
    }
}
