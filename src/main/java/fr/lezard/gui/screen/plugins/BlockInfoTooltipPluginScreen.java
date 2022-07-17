package fr.lezard.gui.screen.plugins;

import java.util.Arrays;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.gui.screen.MainPluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.plugins.hud.BlockInfoTooltipPlugin;
import fr.lezard.utils.Colors;
import fr.lezard.utils.CommonLezardVariables;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class BlockInfoTooltipPluginScreen extends Screen {

	public BlockInfoTooltipPluginScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".plugin.blocktooltip"));
	}
	
	public boolean isPauseScreen()
    {
        return false;
    }
	
	protected void init() {
		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, (p_96257_) -> {
	         this.minecraft.setScreen(new MainPluginScreen());
	      }));
		Plugin plugin = Lezard.plugins.get(13);
		this.addRenderableWidget(CycleButton.onOffBuilder(plugin.isEnabled()).create(this.width / 2 - 48, this.height / 6, 96, 20, CommonLezardVariables.ENABLED, (p_170168_, p_170169_) -> {
			plugin.toggle();
		}));
		this.addRenderableWidget(CycleButton.builder(Colors::getName).withValues(Arrays.asList(Colors.values())).withInitialValue(BlockInfoTooltipPlugin.firstColor).create(this.width / 2 - 48, this.height / 6+ 22, 96, 20, CommonLezardVariables.COLOR, (p_167441_, p_167442_) -> {
			BlockInfoTooltipPlugin.firstColor = p_167442_;
			FileWriterJson.writeJson(plugin.getNamespace(), "color", BlockInfoTooltipPlugin.firstColor.getLiteralName());
        }));
		this.addRenderableWidget(CycleButton.onOffBuilder(BlockInfoTooltipPlugin.rainbow).create(this.width / 2 - 48, this.height / 6 + 44, 96, 20, CommonLezardVariables.RAINBOW, (p_170168_, p_170169_) -> {
			BlockInfoTooltipPlugin.rainbow = !BlockInfoTooltipPlugin.rainbow;
			FileWriterJson.writeJson(plugin.getNamespace(), "rainbow", BlockInfoTooltipPlugin.rainbow);
		}));
		this.addRenderableWidget(CycleButton.onOffBuilder(BlockInfoTooltipPlugin.filled).create(this.width / 2 - 48, this.height / 6 + 66, 96, 20, CommonLezardVariables.FILLED, (p_170168_, p_170169_) -> {
			BlockInfoTooltipPlugin.filled = !BlockInfoTooltipPlugin.filled;
			FileWriterJson.writeJson(plugin.getNamespace(), "filled", BlockInfoTooltipPlugin.filled);
		}));
		this.addRenderableWidget(CycleButton.builder(Colors::getName).withValues(Arrays.asList(Colors.values())).withInitialValue(BlockInfoTooltipPlugin.secondColor).create(this.width / 2 - 48, this.height / 6+ 88, 96, 20, CommonLezardVariables.SECOND_COLOR, (p_167441_, p_167442_) -> {
			BlockInfoTooltipPlugin.secondColor = p_167442_;
			FileWriterJson.writeJson(plugin.getNamespace(), "second", BlockInfoTooltipPlugin.secondColor.getLiteralName());
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

}
