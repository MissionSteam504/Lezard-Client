package fr.lezard.plugins.hud;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

public class FPSPluginHUD extends PluginHUD{
	public FPSPluginHUD() {
		super("FPS HUD", FileWriterJson.getBoolean("fps", "enabled"), Category.HUD, "fps", Minecraft.getInstance().options.keyFpsHud);
	}
	
	public void onEvent(Event<?> e) {
		if(e instanceof EventInGame) {
			int posX = 0;
			int posY = 0;
			if(isDragged() && DragScreen.plugin == this) {
				posX = DragScreen.posX;
				posY = DragScreen.posY;
			}else {
				posX = getPosX();
				posY = getPosY();
			}
			
			PoseStack poseStack = new PoseStack();
			Minecraft mc = Minecraft.getInstance();
			Font font = mc.font;
			
			if(mc.options.renderDebug){
	            return;
	        }
			
			String[] data = mc.fpsString.split(" ");
			
			String string = "FPS: " + data[0];
			setWidth(font.width(string));
			setHeight(font.lineHeight);
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - Lezard.GAP, posY - Lezard.GAP, getWidth() + posX + Lezard.GAP, getHeight() + posY + Lezard.GAP, Lezard.color.getRGB());
	        }
			
			GuiComponent.drawString(poseStack, font, string, posX, posY, isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
		}
	}
}
