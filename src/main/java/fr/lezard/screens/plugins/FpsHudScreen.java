package fr.lezard.screens.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.PluginFileManager;
import fr.lezard.PluginsManager;
import fr.lezard.plugins.ArmorHudPlugin;
import fr.lezard.plugins.CompassHudPlugin;
import fr.lezard.plugins.FpsHudPlugin;
import fr.lezard.plugins.ItemPhysicsPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class FpsHudScreen extends Screen {
    public FpsHudScreen() {
        super(new TranslatableComponent("lezard.plugin.fpsHud"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(CycleButton.onOffBuilder(FpsHudPlugin.enabled).create(this.width / 2 - 49, this.height / 4 + 12 - 16, 98, 20, new TranslatableComponent("lezard.enabled"), (p_170168_, p_170169_) -> {
            FpsHudPlugin.enabled = !FpsHudPlugin.enabled;
        }));
        this.addRenderableWidget(CycleButton.onOffBuilder(FpsHudPlugin.filled).create(this.width / 2 - 49, this.height / 4 + 36 - 16, 98, 20, new TranslatableComponent("lezard.filled"), (p_170168_, p_170169_) -> {
            FpsHudPlugin.filled = !FpsHudPlugin.filled;
        }));
        this.addRenderableWidget(CycleButton.onOffBuilder(FpsHudPlugin.rainbow).create(this.width / 2 - 49, this.height / 4 + 60 - 16, 98, 20, new TranslatableComponent("lezard.rainbow"), (p_170168_, p_170169_) -> {
            FpsHudPlugin.rainbow = !FpsHudPlugin.rainbow;
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 156 -16, 98, 20, new TranslatableComponent("lezard.goBack"), (p_96335_) -> {
            PluginFileManager.writeJson(FpsHudPlugin.name, "enabled", FpsHudPlugin.enabled);
            PluginFileManager.writeJson(FpsHudPlugin.name, "filled", FpsHudPlugin.filled);
            PluginFileManager.writeJson(FpsHudPlugin.name, "rainbow", FpsHudPlugin.rainbow);
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
