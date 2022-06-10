package fr.lezard.plugins.render;

import com.mojang.blaze3d.platform.InputConstants;

import fr.lezard.plugins.Plugin;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.Minecraft;

public class FullBrightPlugin extends Plugin {
	private double gamma;

	public FullBrightPlugin() {
		super("Full Bright", FileWriterJson.getBoolean("fullbright", "enabled"), Category.RENDER, "fullbright", Minecraft.getInstance().options.keyFullbright);
	}
	
	public void onEnable() {
		gamma = Minecraft.getInstance().options.gamma;
		Minecraft.getInstance().options.gamma = 1000D;
	}
	
	public void onDisable() {
		Minecraft.getInstance().options.gamma = gamma;
	}
}
