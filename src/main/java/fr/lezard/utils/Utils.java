package fr.lezard.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.gui.screen.MainPluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.files.LezardSettings;
import fr.lezard.utils.files.PluginJson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class Utils {
	public static void drawImg(int x, int y, boolean lower, ResourceLocation img){
		PoseStack ps = new PoseStack();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, img);
		GuiComponent.blit(ps, x-16, lower ? y +16 : y, 0.0f, 0.0f, 16, 16, 16, 16);
	}
	
	public static String longestString(String[] strings) {
		String longest = "";
		for(String s : strings) {
			if(s.length() > longest.length()) {
				longest = s;
			}
		}
		return longest;
	}
	
	public static void normalPluginScreenInit(int pIndex, Screen screen, int width, int height) {
		screen.addRenderableWidget(new Button(width / 2 - 100, height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, (p_96257_) -> {
	         Minecraft.getInstance().setScreen(new MainPluginScreen());
	      }));
		Plugin plugin = Lezard.plugins.get(pIndex);
		screen.addRenderableWidget(CycleButton.onOffBuilder(plugin.isEnabled()).create(width / 2 - 48, height / 6, 96, 20, CommonLezardVariables.ENABLED, (p_170168_, p_170169_) -> {
			plugin.toggle();
		}));
	}
	
	public static void normalPluginHudScreenInit(int pIndex, Screen screen, int width, int height) {
		screen.addRenderableWidget(new Button(width / 2 - 100, height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, (p_96257_) -> {
			Minecraft.getInstance().setScreen(new MainPluginScreen());
	      }));
		Plugin plugin = Lezard.plugins.get(pIndex);
		PluginHUD p = (PluginHUD) plugin;
		screen.addRenderableWidget(CycleButton.onOffBuilder(plugin.isEnabled()).create(width / 2 - 48, height / 6, 96, 20, CommonLezardVariables.ENABLED, (p_170168_, p_170169_) -> {
			plugin.toggle();
		}));
		screen.addRenderableWidget(CycleButton.builder(Colors::getName).withValues(Arrays.asList(Colors.values())).withInitialValue(p.getColors()).create(width / 2 - 48, height / 6+ 22, 96, 20, CommonLezardVariables.COLOR, (p_167441_, p_167442_) -> {
           p.setColors(p_167442_);
       }));
		screen.addRenderableWidget(CycleButton.onOffBuilder(p.isRainbow()).create(width / 2 - 48, height / 6 + 44, 96, 20, CommonLezardVariables.RAINBOW, (p_170168_, p_170169_) -> {
			p.setRainbow(!p.isRainbow());
		}));
		screen.addRenderableWidget(CycleButton.onOffBuilder(p.isFilled()).create(width / 2 - 48, height / 6 + 66, 96, 20, CommonLezardVariables.FILLED, (p_170168_, p_170169_) -> {
			p.setFilled(!p.isFilled());
		}));
		
		String sliderTitle = "Size: " + String.format("%.2f%%", p.getSize()).replace("%", "").replace(",00", "");
		
		screen.addRenderableWidget(new AbstractSliderButton(width / 2 - 48, height / 6 + 88, 96, 20, new TextComponent(sliderTitle), p.getSize()) {

			@Override
			protected void updateMessage() {
				this.setMessage(new TextComponent("Size: " + String.format("%.2f%%", p.getSize()).replace("%", "").replace(",00", "")));
			}

			@Override
			protected void applyValue() {
				p.setSize((float) this.value);
			}
		});
	}

	public static PluginJson getPlugin(String namespace) {
		for(PluginJson p : Lezard.settings.getPlugins()) {
			if(p.getNamespace().equalsIgnoreCase(namespace)) {
				return p;
			}
		}
		
		PluginJson p = new PluginJson(namespace);
		List<PluginJson> pluginsjson = new ArrayList<>(Arrays.asList(Lezard.settings.getPlugins()));
		pluginsjson.add(p);
		Lezard.settings.setPlugins((PluginJson[]) pluginsjson.toArray(new PluginJson[pluginsjson.size()]));
		return p;
	} 
	
	public static LezardSettings getSettings() {
		Gson gson = new Gson();
    	try {
			return gson.fromJson(Files.readString(Paths.get(Lezard.settingsFile.getPath())), LezardSettings.class);
		} catch (Exception e) {
			e.printStackTrace();
			return getSettings();
		}
    }
}
