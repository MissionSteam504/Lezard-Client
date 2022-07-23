package fr.lezard.plugins.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.CPSPluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class CPSPluginHUD extends PluginHUD{
	private static List<Long> clicksLeft = new ArrayList<Long>();
	private static List<Long> clicksRight = new ArrayList<Long>();
	
	public static Mode currentMode = Mode.LEFT;
	
	private boolean wasPressedLeft;
	private boolean wasPressedRight;
	private Long lastPressedButtonLeft;
	private Long lastPressedButtonRight;
	
	public CPSPluginHUD() {
		super("CPS HUD", FileWriterJson.getBoolean("cps", "enabled"), Category.PLAYER, "cps", Minecraft.getInstance().options.keyCps, new CPSPluginHUDScreen());
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
	            GuiComponent.fill(poseStack, posX - LezardOptions.gap, posY - LezardOptions.gap, getWidth() + posX + LezardOptions.gap, getHeight() + posY + LezardOptions.gap, Lezard.color.getRGB());
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
		if(e instanceof EventStart) {
			if(!FileWriterJson.getString(getNamespace(), "mode").equalsIgnoreCase("")){
	            currentMode = CPSPluginHUD.Mode.valueOf(FileWriterJson.getString(getNamespace(), "mode"));
	        }
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
		LEFT("LEFT"),
		RIGHT("RIGHT"),
		DOUBLE("DOUBLE");
		
		private final String name;
		
		Mode(String name){
			this.name = name;
		}
		
		public Component getName() {
	        return new TranslatableComponent(Lezard.NAMESPACE + ".cpsMode." + name.toLowerCase(Locale.ROOT));
	    }

	    public String getLiteralName(){
	        return name;
	    }
	}
}
