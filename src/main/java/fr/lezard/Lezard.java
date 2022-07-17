package fr.lezard;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import fr.lezard.events.Event;
import fr.lezard.events.listeners.*;
import fr.lezard.gui.screen.BannedScreen;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.MainMenu;
import fr.lezard.gui.screen.NotWhitelistedScreen;
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
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

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
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
	public static final String NAME = "Lezard Client";
	public static final String NAMESPACE = "lezard";
	public static final String VERSION = "2.0.0-beta9";
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
	
	public static Color color = new Color(0, 0, 0, LezardOption.alpha);
	
	public static File pluginFile = new File(Minecraft.getInstance().gameDirectory, NAMESPACE + "-settings.json");
	
	public static CopyOnWriteArrayList<Plugin> plugins = new CopyOnWriteArrayList<Plugin>();
	public static CopyOnWriteArrayList<PluginHUD> pluginsHUD = new CopyOnWriteArrayList<PluginHUD>();
	
	public static void launch() {
		LOGGER.info(PREFIX + "Starting client...");
		
		firstLaunch=false;
		
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
		if(e instanceof EventInGame) {
			color = new Color(0, 0, 0, LezardOption.alpha);
		}
		/*if(e instanceof EventStart) {
			oldWidth=Minecraft.getInstance().getWindow().getGuiScaledWidth();
			oldHeight=Minecraft.getInstance().getWindow().getGuiScaledHeight();
		}*/
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
	
	public static void drawImg(int x, int y, boolean lower, ResourceLocation img){
		PoseStack ps = new PoseStack();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, img);
		GuiComponent.blit(ps, x-16, lower ? y +16 : y, 0.0f, 0.0f, 16, 16, 16, 16);
	}
}
