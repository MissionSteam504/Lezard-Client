package fr.lezard.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class NotWhitelistedScreen extends Screen {

	public NotWhitelistedScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".gui.screen.notWhitelited"));
	}
	
	protected void init() {
		int y = this.height/2 + this.height/8 - 10;
		this.addRenderableWidget(new Button(this.width/4, y, 96, 20, new TranslatableComponent("menu.quit"), (p_96257_) -> {
	         minecraft.stop();
	    }));
		this.addRenderableWidget(new Button((this.width/4)*3 - 96, y, 96, 20, new TranslatableComponent(Lezard.NAMESPACE + ".joinDiscord"), (p_96257_) -> {
			Util.getPlatform().openUri("https://discord.gg/uZcq7kU6FG");
		}));
		minecraft.getSoundManager().stop();
	}
	
	public boolean shouldCloseOnEsc() {
        return false;
    }
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		TranslatableComponent[] texts = {new TranslatableComponent(Lezard.NAMESPACE + ".whitelist1"), new TranslatableComponent(Lezard.NAMESPACE + ".whitelist2"), new TranslatableComponent(Lezard.NAMESPACE + ".whitelist3"), new TranslatableComponent(Lezard.NAMESPACE + ".whitelist4")};
		Font font = Minecraft.getInstance().font;
		int line=0;
		this.renderBackground(poseStack);
		for(TranslatableComponent s : texts) {
			drawCenteredString(poseStack, font, s, width/2, this.height/2-this.height/6-((font.lineHeight*texts.length)/2)+line*12, -1);
			line++;
		}
		if(Lezard.isWhitelisted) {
			minecraft.setScreen(new MainMenu());
		}
		super.render(poseStack, mouseX, mouseY, p_96565_);
	}
}
