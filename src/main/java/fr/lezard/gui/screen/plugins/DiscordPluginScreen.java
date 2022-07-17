package fr.lezard.gui.screen.plugins;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.gui.screen.MainPluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.CommonLezardVariables;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class DiscordPluginScreen extends Screen {

	public DiscordPluginScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".plugin.discord"));
	}
	
	protected void init() {
		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, (p_96257_) -> {
	         this.minecraft.setScreen(new MainPluginScreen());
	      }));
		Plugin plugin = Lezard.plugins.get(4);
		this.addRenderableWidget(CycleButton.onOffBuilder(plugin.isEnabled()).create(this.width / 2 - 48, this.height / 6, 96, 20, CommonLezardVariables.ENABLED, (p_170168_, p_170169_) -> {
			plugin.toggle();
		}));
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

}
