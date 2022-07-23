package fr.lezard.plugins.player;

import java.util.Locale;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.CoordsPluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOptions;
import fr.lezard.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class CoordsPluginHUD extends PluginHUD{
	public CoordsPluginHUD() {
		super("Coords HUD", FileWriterJson.getBoolean("coords", "enabled"), Category.PLAYER, "coords", Minecraft.getInstance().options.keyCoords, new CoordsPluginHUDScreen());
	}
	
	public static Mode mode = Mode.INLINE;
	
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
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - LezardOptions.gap, posY - LezardOptions.gap, getWidth() + posX + LezardOptions.gap, getHeight() + posY + LezardOptions.gap, Lezard.color.getRGB());
	        }
			
			if(mode == Mode.INLINE) {
				String xyz = String.format(Locale.ROOT, "XYZ: %.0f / %.0f / %.0f", minecraft.getCameraEntity().getX(), minecraft.getCameraEntity().getY(), minecraft.getCameraEntity().getZ());
				setWidth(font.width(xyz));
				setHeight(font.lineHeight);
				GuiComponent.drawString(poseStack, font, xyz, posX, posY, isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
			}else if(mode == Mode.THREE_LINES) {
				String x = String.format(Locale.ROOT, "X: %.0f", minecraft.getCameraEntity().getX());
				String y = String.format(Locale.ROOT, "Y: %.0f", minecraft.getCameraEntity().getY());
				String z = String.format(Locale.ROOT, "Z: %.0f", minecraft.getCameraEntity().getZ());
				String[] strings = {x, y, z};
				
				setWidth(font.width(Utils.LongestString(strings)));
				setHeight(font.lineHeight*3);
				for(int i=0; i<strings.length; i++) {
					GuiComponent.drawString(poseStack, font, strings[i], posX, posY+(font.lineHeight*i), isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
				}
			}
		}
		if(e instanceof EventStart) {
			if(!FileWriterJson.getString(getNamespace(), "mode").equalsIgnoreCase("")){
	            mode = Mode.valueOf(FileWriterJson.getString(getNamespace(), "mode"));
	        }
		}
	}
	
	public enum Mode{
		INLINE("INLINE"),
		THREE_LINES("THREE_LINES"),
		;
		
		private String name;
		Mode(String name){
			this.name = name;
		}
		
		public String getLiteralName() {
			return name;
		}
		
		public Component getName() {
			return new TranslatableComponent(Lezard.NAMESPACE + ".coords." + name.toLowerCase(Locale.ROOT));
		}
	}
}
