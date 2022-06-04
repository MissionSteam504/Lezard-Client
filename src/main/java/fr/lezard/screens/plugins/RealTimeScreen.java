package fr.lezard.screens.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.LezardCore;
import fr.lezard.PluginFileManager;
import fr.lezard.plugins.ArmorHudPlugin;
import fr.lezard.plugins.InGameTimeHudPlugin;
import fr.lezard.plugins.KeyStrokePlugin;
import fr.lezard.plugins.RealTimeHudPlugin;
import fr.lezard.plugins.utils.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class RealTimeScreen extends Screen {
    public RealTimeScreen() {
        super(new TranslatableComponent(LezardCore.NAMESPACE + ".plugin.realTime"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(CycleButton.onOffBuilder(RealTimeHudPlugin.enabled).create(this.width / 2 - 49, this.height / 4 + 12 - 16, 98, 20, new TranslatableComponent(LezardCore.NAMESPACE + ".enabled"), (p_170168_, p_170169_) -> {
            RealTimeHudPlugin.enabled = !RealTimeHudPlugin.enabled;
        }));
        this.addRenderableWidget(CycleButton.onOffBuilder(RealTimeHudPlugin.filled).create(this.width / 2 - 49, this.height / 4 + 36 - 16, 98, 20, new TranslatableComponent("lezard.filled"), (p_170168_, p_170169_) -> {
            RealTimeHudPlugin.filled = !RealTimeHudPlugin.filled;
        }));
        this.addRenderableWidget(CycleButton.onOffBuilder(RealTimeHudPlugin.rainbow).create(this.width / 2 - 49, this.height / 4 + 60 - 16, 98, 20, new TranslatableComponent("lezard.rainbow"), (p_170168_, p_170169_) -> {
            RealTimeHudPlugin.rainbow = !RealTimeHudPlugin.rainbow;
        }));
        this.addRenderableWidget(CycleButton.builder(Colors::getName).withValues(Colors.values()).withInitialValue(RealTimeHudPlugin.colors).create(this.width / 2 - 49, this.height / 4 + 84 - 16, 98, 20, new TranslatableComponent("lezard.color"), (p_167441_, p_167442_) -> {
            RealTimeHudPlugin.colors = p_167442_;
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 156 -16, 98, 20, new TranslatableComponent("lezard.goBack"), (p_96335_) -> {
            PluginFileManager.writeJson(RealTimeHudPlugin.name, "enabled", RealTimeHudPlugin.enabled);
            PluginFileManager.writeJson(RealTimeHudPlugin.name, "filled", RealTimeHudPlugin.filled);
            PluginFileManager.writeJson(RealTimeHudPlugin.name, "rainbow", RealTimeHudPlugin.rainbow);
            PluginFileManager.writeJson(RealTimeHudPlugin.name, "color", RealTimeHudPlugin.colors.getLiteralName());
            Minecraft.getInstance().setScreen(new MainPluginsScreen());
        }));
    }

    public void tick() {
        super.tick();
    }
    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        this.renderBackground(p_96562_);
        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
        drawCenteredString(p_96562_, this.font, this.title, this.width / 2, 15, 16777215);
    }
}
