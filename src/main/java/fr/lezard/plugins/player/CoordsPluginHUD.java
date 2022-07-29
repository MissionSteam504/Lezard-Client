package fr.lezard.plugins.player;

import java.util.Locale;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.player.CoordsPluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.LezardOptions;
import fr.lezard.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class CoordsPluginHUD extends PluginHUD{
	public CoordsPluginHUD() {
		super("Coords HUD", Utils.getPlugin("coords").isEnabled(), Category.PLAYER, "coords", Minecraft.getInstance().options.keyCoords, new CoordsPluginHUDScreen());
	}
	
	public static Mode mode = Mode.INLINE;
	
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
				setWidth(font.width(xyz)*size);
				setHeight(font.lineHeight*size);
				poseStack.pushPose();
				poseStack.scale(size, size, 1);
				GuiComponent.drawString(poseStack, font, xyz, posX*(1/size), posY*(1/size), isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
				poseStack.popPose();
			}else if(mode == Mode.THREE_LINES) {
				String x = String.format(Locale.ROOT, "X: %.0f", minecraft.getCameraEntity().getX());
				String y = String.format(Locale.ROOT, "Y: %.0f", minecraft.getCameraEntity().getY());
				String z = String.format(Locale.ROOT, "Z: %.0f", minecraft.getCameraEntity().getZ());
				String[] strings = {x, y, z};
				
				setWidth(font.width(Utils.longestString(strings))*size);
				setHeight((font.lineHeight*3)*size);
				for(int i=0; i<strings.length; i++) {
					poseStack.pushPose();
					poseStack.scale(size, size, 1);
					GuiComponent.drawString(poseStack, font, strings[i], posX*(1/size), (posY+(font.lineHeight*i*size))*(1/size), isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
					poseStack.popPose();
				}
			}
		}
		if(e instanceof EventStart) {
			if(!Utils.getPlugin(getNamespace()).getMode().equalsIgnoreCase("")){
	            mode = Mode.valueOf(Utils.getPlugin(getNamespace()).getMode());
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
