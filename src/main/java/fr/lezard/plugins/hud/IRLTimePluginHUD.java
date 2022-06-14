package fr.lezard.plugins.hud;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.IRLTimePluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

public class IRLTimePluginHUD extends PluginHUD {

	public IRLTimePluginHUD() {
		super("IRL Time", FileWriterJson.getBoolean("irl", "enabled"), Category.HUD, "irl", Minecraft.getInstance().options.keyIrlTime, new IRLTimePluginHUDScreen());
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
			
			Calendar calendar = new GregorianCalendar();

	        int sec = calendar.get(Calendar.SECOND);
	        int minutes = calendar.get(Calendar.MINUTE);
	        int hours = calendar.get(Calendar.HOUR_OF_DAY);

	        String string = "IRL Time: " + (hours<10 ? "0" + hours : hours) + ":" + (minutes<10 ? "0" + minutes : minutes) + ":" + (sec<10 ? "0" + sec : sec);
	        
	        setWidth(font.width(string));
			setHeight(font.lineHeight);
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - LezardOption.gap, posY - LezardOption.gap, getWidth() + posX + LezardOption.gap, getHeight() + posY + LezardOption.gap, Lezard.color.getRGB());
	        }
	        
	        GuiComponent.drawString(poseStack, font, string, posX, posY, isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
		}
	}
}
