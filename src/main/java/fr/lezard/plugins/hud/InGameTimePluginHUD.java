package fr.lezard.plugins.hud;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.hud.InGameTimePluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

public class InGameTimePluginHUD extends PluginHUD{
	public InGameTimePluginHUD() {
		super("In Game Time HUD", FileWriterJson.getBoolean("igtime", "enabled"), Category.HUD, "igtime", Minecraft.getInstance().options.keyInGameTime, new InGameTimePluginHUDScreen());
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
			
			String string = "Time: " + getInGameWorldHours() + ":" + getInGameWorldMinutes();
			setWidth(font.width(string)*size);
			setHeight(font.lineHeight*size);
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - LezardOptions.gap, posY - LezardOptions.gap, getWidth() + posX + LezardOptions.gap, getHeight() + posY + LezardOptions.gap, Lezard.color.getRGB());
	        }
			poseStack.pushPose();
			poseStack.scale(size, size, 1);
			GuiComponent.drawString(poseStack, font, string, posX*(1/size), posY*(1/size), isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
			poseStack.popPose();
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
