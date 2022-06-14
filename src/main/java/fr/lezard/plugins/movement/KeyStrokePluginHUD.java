package fr.lezard.plugins.movement;

import java.awt.Color;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.KeyStrokePluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.KeyStroke;
import fr.lezard.utils.KeyStrokeModule;
import fr.lezard.utils.LezardOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

public class KeyStrokePluginHUD extends PluginHUD{
	
	public static KeyStrokeModule keyMode = KeyStrokeModule.WASD;
	
	public KeyStrokePluginHUD() {
		super("KeyStroke HUD", FileWriterJson.getBoolean("key", "enabled"), Category.MOVEMENT, "key", Minecraft.getInstance().options.keyStroke, new KeyStrokePluginHUDScreen());
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
			
			setWidth(keyMode.getWidth());
			setHeight(keyMode.getHeight());
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - LezardOption.gap, posY - LezardOption.gap, getWidth() + posX + LezardOption.gap, getHeight() + posY + LezardOption.gap, Lezard.color.getRGB());
	        }
			
			for(KeyStroke k : keyMode.getKeyStrokes()){
	            GuiComponent.fill(poseStack,
	                    posX + k.getX(),
	                    posY + k.getY(),
	                    posX + k.getX() + k.getWidth(),
	                    posY + k.getY() + k.getHeight(),
	                    k.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 102).getRGB()
	            );

	            int textWidth = font.width(k.getName());
	            GuiComponent.drawString(poseStack,
	                    font,
	                    k.getName(),
	                    posX + k.getX() + k.getWidth() / 2 - textWidth / 2,
	                    posY + k.getY() +k.getHeight() / 4 + (!k.isBar() ? 2 : -1),
	                    isRainbow() ? Lezard.rainbowText() : getColors().getRgb()
	            );
	        }
		}
		if(e instanceof EventStart) {
			if(!FileWriterJson.getString(getNamespace(), "mode").equalsIgnoreCase("")){
	            keyMode = KeyStrokeModule.valueOf(FileWriterJson.getString(getNamespace(), "mode"));
	        }
		}
	}
}
