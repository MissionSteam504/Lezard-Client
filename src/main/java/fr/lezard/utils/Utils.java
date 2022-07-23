package fr.lezard.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class Utils {
	public static void drawImg(int x, int y, boolean lower, ResourceLocation img){
		PoseStack ps = new PoseStack();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, img);
		GuiComponent.blit(ps, x-16, lower ? y +16 : y, 0.0f, 0.0f, 16, 16, 16, 16);
	}
	
	public static String LongestString(String[] strings) {
		String longest = "";
		for(String s : strings) {
			if(s.length() > longest.length()) {
				longest = s;
			}
		}
		return longest;
	}
}
