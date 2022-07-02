package fr.lezard.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class BannedScreen extends Screen{

	public BannedScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".gui.screen.banned"));
	}
	
	protected void init() {
		minecraft.getSoundManager().stop();
	}
	
	public boolean shouldCloseOnEsc() {
        return false;
    }
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		this.renderBackground(poseStack);
		fill(poseStack, 0, 0, this.width,this.height, 0xff000000);
		Font font = Minecraft.getInstance().font;
		
		poseStack.pushPose();
		poseStack.translate(width/2f, height/2f, 0);
		poseStack.scale(3, 3, 1);
		poseStack.translate(-(width/2f), -(height/2), 0);
		drawCenteredString(poseStack, font, "You are banned", width/2, height/2 - font.lineHeight/2, -1);
		poseStack.popPose();
		drawCenteredString(poseStack, font, "Reason: \u00A74" + Lezard.banReason, width/2, height/4*3 - font.lineHeight/2, -1);
		
		if(!Lezard.ban) {
			minecraft.setScreen(new MainMenu());
		}
		
		super.render(poseStack, mouseX, mouseY, p_96565_);
	}
}
