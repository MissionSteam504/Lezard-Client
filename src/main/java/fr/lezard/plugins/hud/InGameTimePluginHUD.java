package fr.lezard.plugins.hud;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

public class InGameTimePluginHUD extends PluginHUD{
	public InGameTimePluginHUD() {
		super("In Game Time HUD", FileWriterJson.getBoolean("igtime", "enabled"), Category.HUD, "igtime", Minecraft.getInstance().options.keyInGameTime);
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
			
			String string = "Time: " + getInGameWorldHours() + ":" + getInGameWorldMinutes();
			setWidth(font.width(string));
			setHeight(font.lineHeight);
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - LezardOption.gap, posY - LezardOption.gap, getWidth() + posX + LezardOption.gap, getHeight() + posY + LezardOption.gap, Lezard.color.getRGB());
	        }
			
			GuiComponent.drawString(poseStack, font, string, posX, posY, isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
		}
	}
	
	private static String getInGameWorldMinutes(){
        assert Minecraft.getInstance().player != null;
        int time = (int) Minecraft.getInstance().player.getCommandSenderWorld().getDayTime();
        int finalTime =(time % 1000) * 6 / 100;
        return finalTime < 10 ? "0" + finalTime : String.valueOf(finalTime);
    }

    private static String getInGameWorldHours(){
        assert Minecraft.getInstance().player != null;
        int time = (int) ((Minecraft.getInstance().player.getCommandSenderWorld().getDayTime() + 6000) % 24000);
        int finalTime = (int) (time / 1000F);
        return finalTime < 10 ? "0" + finalTime : String.valueOf(finalTime);
    }
}
