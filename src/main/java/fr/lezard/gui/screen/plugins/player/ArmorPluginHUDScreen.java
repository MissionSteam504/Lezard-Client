package fr.lezard.gui.screen.plugins.player;

import java.util.Arrays;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.plugins.Plugin;
import fr.lezard.plugins.player.ArmorPluginHUD;
import fr.lezard.utils.CommonLezardVariables;
import fr.lezard.utils.Utils;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class ArmorPluginHUDScreen extends Screen {

	public ArmorPluginHUDScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".plugin.armor"));
	}
	
	public boolean isPauseScreen()
    {
        return false;
    }
	
	protected void init() {
		Plugin plugin = Lezard.plugins.get(0);
		Utils.normalPluginHudScreenInit(0, this, width, height);
		this.addRenderableWidget(CycleButton.builder(ArmorPluginHUD.Modes::getName).withValues(Arrays.asList(ArmorPluginHUD.Modes.values())).withInitialValue(ArmorPluginHUD.currentMode).create(this.width / 2 - 48, this.height / 6 + 110, 96, 20, CommonLezardVariables.MODE, (p_167441_, p_167442_) -> {
			ArmorPluginHUD.currentMode = p_167442_;
			Utils.getPlugin(plugin.getNamespace()).setMode(ArmorPluginHUD.currentMode.getLiteralName());
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
