package fr.lezard.screens.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.PluginFileManager;
import fr.lezard.plugins.ArmorHudPlugin;
import fr.lezard.plugins.InGameTimeHudPlugin;
import fr.lezard.plugins.KeyStrokePlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class KeyStrokeScreen extends Screen {
    public KeyStrokeScreen() {
        super(new TranslatableComponent("lezard.plugin.armorHud"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(CycleButton.onOffBuilder(KeyStrokePlugin.enabled).create(this.width / 2 - 49, this.height / 4 + 12 - 16, 98, 20, new TranslatableComponent("lezard.enabled"), (p_170168_, p_170169_) -> {
            KeyStrokePlugin.enabled = !KeyStrokePlugin.enabled;
        }));
        this.addRenderableWidget(CycleButton.onOffBuilder(KeyStrokePlugin.filled).create(this.width / 2 - 49, this.height / 4 + 36 - 16, 98, 20, new TranslatableComponent("lezard.filled"), (p_170168_, p_170169_) -> {
            KeyStrokePlugin.filled = !KeyStrokePlugin.filled;
        }));
        this.addRenderableWidget(CycleButton.onOffBuilder(KeyStrokePlugin.rainbow).create(this.width / 2 - 49, this.height / 4 + 60 - 16, 98, 20, new TranslatableComponent("lezard.rainbow"), (p_170168_, p_170169_) -> {
            KeyStrokePlugin.rainbow = !KeyStrokePlugin.rainbow;
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 156 -16, 98, 20, new TranslatableComponent("lezard.goBack"), (p_96335_) -> {
            PluginFileManager.writeJson(KeyStrokePlugin.name, "enabled", KeyStrokePlugin.enabled);
            PluginFileManager.writeJson(KeyStrokePlugin.name, "filled", KeyStrokePlugin.filled);
            PluginFileManager.writeJson(KeyStrokePlugin.name, "rainbow", KeyStrokePlugin.rainbow);
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