package fr.lezard.plugins.movement;

import java.awt.Color;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.movement.KeyStrokePluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOptions;
import fr.lezard.utils.keystroke.KeyStroke;
import fr.lezard.utils.keystroke.KeyStrokeModule;
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
			float size = getSize();
			if(isDragged() && DragScreen.plugin == this) {
				posX = (int) DragScreen.posX;
				posY = (int) DragScreen.posY;
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
			
			setWidth(keyMode.getWidth()*size);
			setHeight(keyMode.getHeight()*size);
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - LezardOptions.gap, posY - LezardOptions.gap, getWidth() + posX + LezardOptions.gap, getHeight() + posY + LezardOptions.gap, Lezard.color.getRGB());
	        }
			
			poseStack.pushPose();
			poseStack.scale(size, size, 1);
			
			for(KeyStroke k : keyMode.getKeyStrokes()){
	            GuiComponent.fill(poseStack,
	                    (posX + k.getX()*size)*(1/size),
	                    (posY + k.getY()*size)*(1/size),
	                    (posX + k.getX()*size + k.getWidth()*size)*(1/size),
	                    (posY + k.getY()*size + k.getHeight()*size)*(1/size),
	                    k.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 102).getRGB()
	            );

	            int textWidth = font.width(k.getName());
	            GuiComponent.drawString(poseStack,
	                    font,
	                    k.getName(),
	                    (posX + k.getX()*size + (k.getWidth()*size)/2 - (textWidth*size)/2)*(1/size),
	                    (posY + k.getY()*size + (k.getHeight()*size)/4 + (!k.isBar() ? 2 : -1))*(1/size),
	                    isRainbow() ? Lezard.rainbowText() : getColors().getRgb()
	            );
	        }
			poseStack.popPose();
		}
		if(e instanceof EventStart) {
			if(!FileWriterJson.getString(getNamespace(), "mode").equalsIgnoreCase("")){
	            keyMode = KeyStrokeModule.valueOf(FileWriterJson.getString(getNamespace(), "mode"));
	        }
		}
	}
}
