package fr.lezard.plugins.player;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventKey;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOption;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

public class CPSPluginHUD extends PluginHUD{
	private static List<Long> clicksLeft = new ArrayList<Long>();
	private static List<Long> clicksRight = new ArrayList<Long>();
	
	public static Mode currentMode = Mode.LEFT;
	
	private boolean wasPressedLeft;
	private boolean wasPressedRight;
	private Long lastPressedButtonLeft;
	private Long lastPressedButtonRight;
	
	public CPSPluginHUD() {
		super("CPS HUD", FileWriterJson.getBoolean("cps", "enabled"), Category.PLAYER, "cps", Minecraft.getInstance().options.keyCps);
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
			Minecraft minecraft = Minecraft.getInstance();
			Font font = minecraft.font;
			
			if(minecraft.options.renderDebug){
	            return;
	        }
			
			String string = "";
			
			currentMode = Mode.DOUBLE;
			
			if(currentMode == Mode.LEFT) {
				string = "LMB: " + getLeftCPS();
			}else if(currentMode== Mode.RIGHT) {
				string = "RMB: " + getRightCPS();
			}else if(currentMode== Mode.DOUBLE) {
				string = "LMB: " + getLeftCPS() + " | RMB: " + getRightCPS();
			}else {
				string = "LMB: " + getLeftCPS();
			}
			
			setWidth(font.width(string));
			setHeight(font.lineHeight);
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - LezardOption.gap, posY - LezardOption.gap, getWidth() + posX + LezardOption.gap, getHeight() + posY + LezardOption.gap, Lezard.color.getRGB());
	        }
			
			final boolean leftPressed = minecraft.options.keyAttack.isDown();
			
			if(leftPressed != wasPressedLeft) {
				lastPressedButtonLeft = System.currentTimeMillis();
				wasPressedLeft = leftPressed;
				if(leftPressed) {
					clicksLeft.add(lastPressedButtonLeft);
				}
			}
			
			
			final boolean rightPressed = minecraft.options.keyUse.isDown();
			
			if(rightPressed != wasPressedRight) {
				lastPressedButtonRight = System.currentTimeMillis();
				wasPressedRight = rightPressed;
				if(rightPressed) {
					clicksRight.add(lastPressedButtonRight);
				}
			}
			
			GuiComponent.drawString(poseStack, font, string, posX, posY, isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
		}
	}
	
	public static int getLeftCPS() {
		final Long time = System.currentTimeMillis();
		clicksLeft.removeIf(aLong -> aLong + 1000 < time);
		return clicksLeft.size();
	}
	
	public static int getRightCPS() {
		final Long time = System.currentTimeMillis();
		clicksRight.removeIf(aLong -> aLong + 1000 < time);
		return clicksRight.size();
	}
	
	
	public enum Mode{
		LEFT(),
		RIGHT(),
		DOUBLE();
	}
}
