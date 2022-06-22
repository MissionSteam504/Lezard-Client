package fr.lezard.plugins.hud;

import java.awt.Color;
import java.util.Locale;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.CompassPluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public class CompassPluginHUD extends PluginHUD{
	public static SecondColor second = SecondColor.GOLD;
	public CompassPluginHUD() {
		super("Compass HUD", FileWriterJson.getBoolean("compass", "enabled"), Category.HUD, "compass", Minecraft.getInstance().options.keyCompass, new CompassPluginHUDScreen());
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

			
			setWidth(192);
			setHeight(font.lineHeight*2);
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - LezardOption.gap, posY - LezardOption.gap, getWidth() + posX + LezardOption.gap, getHeight() + posY + LezardOption.gap, Lezard.color.getRGB());
	        }
			
			int middle = getWidth() / 2;

	        assert minecraft.player != null;
	        int angle = (int) Mth.wrapDegrees(minecraft.player.getYRot()) * -1 - 360;
	        
	        GuiComponent.drawString(poseStack, font, "V", posX + middle - font.width("V")/2, posY, Color.WHITE.getRGB());
	        
	        for(int i =0; i<=1; i++){
	            for(double d=0.0D; d<=3.5D; d+=0.5D){
	                if(getWidth() + angle > getWidth() - 60 && getWidth() + angle < getWidth() + 60){
	                    String s = second.getCode() + "South";
	                    if(d==0.5D){
	                        s = "South-West";
	                    }
	                    if(d==1.0D){
	                        s = second.getCode() + "West";
	                    }
	                    if(d==1.5D){
	                        s = "North-West";
	                    }
	                    if(d==2.0D){
	                        s = second.getCode() + "North";
	                    }
	                    if(d==2.5D){
	                        s = "North-East";
	                    }
	                    if(d==3.0D){
	                        s = second.getCode() + "East";
	                    }
	                    if(d==3.5D){
	                        s = "South-East";
	                    }
	                    GuiComponent.drawString(poseStack, font, s, posX + middle + angle - (font.width(s)/2) + (font.width("V") /2), posY + 8, isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
	                }
	                angle+=45;
	            }
	        }
		}
		if(e instanceof EventStart) {
			if(!FileWriterJson.getString(getNamespace(), "second").equalsIgnoreCase("")){
	            second = SecondColor.valueOf(FileWriterJson.getString(getNamespace(), "second"));
	        }
		}
	}
	
	public enum SecondColor {
		// Thanks to HtmlColorCodes.com for the colors
		// https://htmlcolorcodes.com/minecraft-color-codes/
		
		BLACK("\u00A70", "BLACK"),
		DARK_BLUE("\u00A71", "DARK_BLUE"),
		DARK_GREEN("\u00A72", "DARK_GREEN"),
		DARK_AQUA("\u00A73", "DARK_AQUA"),
		DARK_RED("\u00A74", "DARK_RED"),
		DARK_PURPLE("\u00A75", "DARK_PURPLE"),
		GOLD("\u00A76", "GOLD"),
	    GRAY("\u00A77", "GRAY"),
	    DARK_GRAY("\u00A78", "DARK_GRAY"),
	    BLUE("\u00A79", "BLUE"),
	    GREEN("\u00A7a", "GREEN"),
	    AQUA("\u00A7b", "AQUA"),
	    RED("\u00A7c", "RED"),
	    LIGHT_PURPLE("\u00A7d", "LIGHT_PURPLE"),
	    YELLOW("\u00A7e", "YELLOW"),
	    WHITE("\u00A7f", "WHITE"),
	    ;
		
		private String code;
		private String name;
		
		SecondColor(String code, String name){
			this.code = code;
			this.name = name;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getLiteralName() {
	        return name;
	    }
	    public Component getName() {
	        return new TranslatableComponent(Lezard.NAMESPACE + ".color." + name.toLowerCase(Locale.ROOT));
	    }
	}
}
