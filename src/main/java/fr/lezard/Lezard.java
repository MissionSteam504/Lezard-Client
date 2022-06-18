package fr.lezard;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventKey;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.plugins.Plugin.Category;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.plugins.hud.*;
import fr.lezard.plugins.movement.*;
import fr.lezard.plugins.player.*;
import fr.lezard.plugins.render.*;
import fr.lezard.plugins.utils.*;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOption;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

public class Lezard {
	// Main class of the client
	// Remember to set IDE_CLIENT to false
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
	public static final String NAME = "Lezard Client";
	public static final String NAMESPACE = "lezard";
	public static final String VERSION = "2.0.0-beta3";
	public static final String DISCORD_APP_ID = "971435977199464528";
	public static final String USERNAME = "LezardUser";
	public static final String PREFIX = "[" + NAME.replace(" ", "") + "] ";
	public static final String WINDOW_TITLE = NAME + " " + VERSION + " | " + Minecraft.getInstance().getUser().getName();
	
	public static int oldWidth;
	public static int oldHeight;
	
	public static Color color = new Color(0, 0, 0, LezardOption.alpha);
	
	public static File pluginFile = new File(Minecraft.getInstance().gameDirectory, NAMESPACE + "-settings.json");
	
	public static final boolean IDE_CLIENT = false;
	
	public static CopyOnWriteArrayList<Plugin> plugins = new CopyOnWriteArrayList<Plugin>();
	public static CopyOnWriteArrayList<PluginHUD> pluginsHUD = new CopyOnWriteArrayList<PluginHUD>();
	
	public static void launch() {
		LOGGER.info(PREFIX + "Starting client...");
		
		LOGGER.info(PREFIX + "Looking for " + NAMESPACE + "-settings.json...");
		
		if(!pluginFile.exists()){
			LOGGER.info(PREFIX + NAMESPACE + "-settings.json doesnt exist. Crafting new file...");
            try {
                pluginFile.createNewFile();
                PrintWriter printWriter = new PrintWriter(pluginFile);
                printWriter.write("{\"" + NAMESPACE + ".alpha\": 95, \"" + NAMESPACE + ".gap\": 4, \"" + NAMESPACE + ".showAnchor\": false, \"" + NAMESPACE+ ".rainbowSpeed\": 5}");
                printWriter.close();
            }catch (IOException e){
                LOGGER.warn(PREFIX + "Error when creating the file: ");
                e.printStackTrace();
            }
        }else {
        	LOGGER.info(PREFIX + NAMESPACE + "-settings.json exist");
        }
		
		LOGGER.info(PREFIX + "Initializing plugins...");
		plugins.add(new ArmorPluginHUD()); // 0
		plugins.add(new CompassPluginHUD()); // 1
		plugins.add(new CoordsPluginHUD()); // 2
		plugins.add(new CPSPluginHUD()); // 3
		plugins.add(new DiscordPlugin()); // 4
		plugins.add(new FPSPluginHUD()); // 5
		plugins.add(new FullBrightPlugin()); // 6
		plugins.add(new InGameTimePluginHUD()); // 7
		plugins.add(new IRLTimePluginHUD()); // 8
		plugins.add(new ItemPhysicsPlugin()); // 9
		plugins.add(new KeyStrokePluginHUD()); // 10
		plugins.add(new SimplifiedDebugPlugin()); // 11
		plugins.add(new TabHUD()); // 12
		
		
		for(Plugin p : plugins) {
			if(p instanceof PluginHUD pHud) {
				pluginsHUD.add(pHud);
			}
		}
		
		LOGGER.info(PREFIX + "Plugins initialized correctly!");
		LOGGER.info(PREFIX + "Initializing custom options...");
		
		LezardOption.alpha = FileWriterJson.getInt(NAMESPACE, "alpha");
		LezardOption.gap = FileWriterJson.getInt(NAMESPACE, "gap");
		LezardOption.showAnchor = FileWriterJson.getBoolean(NAMESPACE, "showAnchor");
		LezardOption.rainbowSpeed = FileWriterJson.getInt(NAMESPACE, "rainbowSpeed");
		
		LOGGER.info(PREFIX + "Custom options initialized correctly!");
		 
		LOGGER.info(PREFIX + "Calling EventStart...");
		onEvent(new EventStart());
		
		LOGGER.info(PREFIX + "Client setup finished!");
	}
	
	public static void onEvent(Event<?> e) {
		for(Plugin p : plugins) {
			if(e instanceof EventStart) {
				p.onEvent(e);
				continue;
			}
			if(!p.isEnabled())
				continue;
			
			p.onEvent(e);
		}
		if(e instanceof EventInGame) {
			for(PluginHUD p : Lezard.pluginsHUD) {
				if(p.isEnabled()) {
					/*int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
					int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
					
					if(oldWidth != width) {
						System.out.println("Width changed");
						System.out.println("oldWidth: " + oldWidth);
						System.out.println("new width: " + width);
						
						float ratio = (float) width/oldWidth;
						oldWidth=width;
						float newPosX = p.getPosX()*ratio;
						System.out.println("ratio: " + ratio);
						System.out.println("old PosX: " + p.getPosX());
						System.out.println("New PosX (not rounded): " + newPosX);
						System.out.println("New PosX (rounded): " + (int) newPosX);
						p.setPosX((int) newPosX);
					} */
				}
			}
			color = new Color(0, 0, 0, LezardOption.alpha);
		}
		if(e instanceof EventStart) {
			oldWidth=Minecraft.getInstance().getWindow().getGuiScaledWidth();
			oldHeight=Minecraft.getInstance().getWindow().getGuiScaledHeight();
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
        return Color.HSBtoRGB((float) (System.currentTimeMillis() % (LezardOption.rainbowSpeed * 1000L)) / (LezardOption.rainbowSpeed * 1000F), 0.8f, 0.8f);
    }
}
