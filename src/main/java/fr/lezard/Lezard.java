package fr.lezard;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;

import fr.lezard.events.Event;
import fr.lezard.events.listeners.*;
import fr.lezard.gui.screen.*;
import fr.lezard.http.HTTPFunctions;
import fr.lezard.http.gsonobjs.*;
import fr.lezard.plugins.Plugin;
import fr.lezard.plugins.Plugin.Category;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.plugins.hud.*;
import fr.lezard.plugins.movement.*;
import fr.lezard.plugins.player.*;
import fr.lezard.plugins.render.*;
import fr.lezard.plugins.utils.*;
import fr.lezard.utils.*;
import fr.lezard.utils.files.LezardSettings;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

public class Lezard {
	// Main class of the client
	// Remember to set IDE_CLIENT to false when exporting
	
	// Some functions for later (so i can find it easier):
	/*private void noUse() {
		ClientPacketListener.handleChat(null, null);
		CapeUtils
		ServerList
		ServerSelectionList
		JoinMultiplayerScreen
		TextureUtils (line 271)
	}*/
	
	public static final Logger LOGGER = LogUtils.getLogger();
	
	public static final String NAME = "Lezard Client";
	public static final String NAMESPACE = "lezard";
	public static final String VERSION = "2.0.0-beta12";
	public static final String DISCORD_APP_ID = "971435977199464528";
	public static final String USERNAME = "LezardUser";
	public static final String PREFIX = "[" + NAME.replace(" ", "") + "] ";
	public static final String WINDOW_TITLE = NAME + " " + VERSION + " | " + Minecraft.getInstance().getUser().getName();
	
	public static final boolean IDE_CLIENT = true;
	
	public static boolean firstLaunch = true;
	
	public static int oldWidth;
	public static int oldHeight;
	
	public static boolean ban = false;
	public static boolean isWhitelisted = false;
	public static String banReason = "Unknown";
	public static ObjGlobalSettings globalSettings;
	public static ObjUserCosmetics cosmetics;
	public static ObjServer[] servers = {};
	
	public static LezardSettings settings;
	
	public static Color color = new Color(0, 0, 0, LezardOptions.alpha);
	
	public static File settingsFile = new File(Minecraft.getInstance().gameDirectory, NAMESPACE + ".json");
	
	public static CopyOnWriteArrayList<Plugin> plugins = new CopyOnWriteArrayList<Plugin>();
	public static CopyOnWriteArrayList<PluginHUD> pluginsHUD = new CopyOnWriteArrayList<PluginHUD>();
	
	public static Gson gsonBuilder = new GsonBuilder()
			  .setPrettyPrinting()
			  .create();
	
	public static void launch() {
		LOGGER.info(PREFIX + "Starting client...");
		
		firstLaunch=false;
		
		LOGGER.info(PREFIX + "Looking for " + NAMESPACE + ".json...");
		
		if(!settingsFile.exists()) {
			LOGGER.info(PREFIX + NAMESPACE + ".json doesnt exist. Crafting new file...");
			try {
				settingsFile.createNewFile();
				String json = "{\r\n\"alpha\": 90,\r\n\"gap\": 4,\r\n\"showAnchors\": false,\r\n\"rainbowSpeed\": 5,\r\n\"plugins\": []\r\n}";
				FileWriter writer = new FileWriter(settingsFile);
				writer.write(json);
				writer.close();
			}catch (IOException e) {
				LOGGER.warn(PREFIX + "Error when creating the file: ");
                e.printStackTrace();
			}
		}else {
			LOGGER.info(PREFIX + NAMESPACE + ".json already exist");
		}
		
		settings = Utils.getSettings();
		
		LOGGER.info(PREFIX + "Communicating with the server...");
		if(HTTPFunctions.isAPIUp()) {
			LOGGER.info(PREFIX + "Api up");
			HTTPFunctions.sendHWIDMap();
			
			ban = HTTPFunctions.isBanned();
			isWhitelisted = HTTPFunctions.isWhitelisted();
			globalSettings = HTTPFunctions.downloadGlobalSettings();
			banReason = HTTPFunctions.getBanReason();
			cosmetics = HTTPFunctions.downloadCosmetics();
			servers = HTTPFunctions.downloadServers();
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
		plugins.add(new BlockInfoTooltipPlugin()); // 13
		plugins.add(new RealTimePlugin()); // 14
		plugins.add(new CustomTimePlugin()); // 15
		
		for(Plugin p : plugins) {
			Utils.getPlugin(p.getNamespace());
			if(p instanceof PluginHUD pHud) {
				pluginsHUD.add(pHud);
			}
		}
		
		LOGGER.info(PREFIX + "Plugins initialized correctly!");
		LOGGER.info(PREFIX + "Initializing custom options...");
		
		LezardOptions.alpha = settings.getAlpha();
		LezardOptions.gap = settings.getGap();
		LezardOptions.showAnchor = settings.isShowAnchors();
		LezardOptions.rainbowSpeed = settings.getRainbowSpeed();
		
		LOGGER.info(PREFIX + "Custom options initialized correctly!");
		 
		LOGGER.info(PREFIX + "Calling EventStart...");
		onEvent(new EventStart());
		
		LOGGER.info(PREFIX + "Starting communication thread...");
		new Thread(() -> {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					if(HTTPFunctions.isAPIUp()) {
						if(Minecraft.getInstance().screen instanceof MainMenu || Minecraft.getInstance().screen instanceof BannedScreen || Minecraft.getInstance().screen instanceof NotWhitelistedScreen) {
							ban = HTTPFunctions.isBanned();
							banReason = HTTPFunctions.getBanReason();
							isWhitelisted = HTTPFunctions.isWhitelisted();
						}
						
						globalSettings = HTTPFunctions.downloadGlobalSettings();
						cosmetics = HTTPFunctions.downloadCosmetics();
					}
					Thread.sleep(10000L);
				}catch(InterruptedException e) {
					System.out.println(e);
				}
			}
		}, "Communication Thread").start();
		
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
        return Color.HSBtoRGB((float) (System.currentTimeMillis() % (LezardOptions.rainbowSpeed * 1000L)) / (LezardOptions.rainbowSpeed * 1000F), 0.8f, 0.8f);
    }
}
