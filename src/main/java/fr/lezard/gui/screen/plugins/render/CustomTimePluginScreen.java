package fr.lezard.gui.screen.plugins.render;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.plugins.render.CustomTimePlugin;
import fr.lezard.utils.Utils;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class CustomTimePluginScreen extends Screen {

	public CustomTimePluginScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".plugin.customtime"));
	}
	
	protected void init() {
		Utils.normalPluginScreenInit(15, this, this.width, this.height);
		
		this.addRenderableWidget(new AbstractSliderButton(width / 2 - 48, height / 6 + 22, 96, 20, new TextComponent(String.valueOf((int) (CustomTimePlugin.time*24000))), CustomTimePlugin.time) {

			@Override
			protected void updateMessage() {
				this.setMessage(new TextComponent(String.valueOf((int) (CustomTimePlugin.time*24000))));
			}

			@Override
			protected void applyValue() {
				CustomTimePlugin.time = (float) this.value;
				Utils.getPlugin("customtime").setSize(CustomTimePlugin.time); // Storing time in size lmao
			}
		});
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		renderBackground(poseStack);
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
        super.render(poseStack, mouseX, mouseY, p_96565_);
    }
	
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	public boolean mouseDragged(double mouseX, double mouseY, int button, double d1, double d2) {
		return super.mouseDragged(mouseX, mouseY, button, d1, d2);
	}
	
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return super.mouseReleased(mouseX, mouseY, button);
	}
	
	public boolean isPauseScreen()
    {
        return false;
    }

}
