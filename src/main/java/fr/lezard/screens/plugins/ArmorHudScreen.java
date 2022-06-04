package fr.lezard.screens.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.LezardCore;
import fr.lezard.PluginFileManager;
import fr.lezard.plugins.ArmorHudPlugin;
import fr.lezard.plugins.utils.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

import java.awt.*;

public class ArmorHudScreen extends Screen {
    public ArmorHudScreen() {
        super(new TranslatableComponent(LezardCore.NAMESPACE + ".plugin.armorHud"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(CycleButton.onOffBuilder(ArmorHudPlugin.enabled).create(this.width / 2 - 49, this.height / 4 + 12 - 16, 98, 20, new TranslatableComponent(LezardCore.NAMESPACE + ".enabled"), (p_170168_, p_170169_) -> {
            ArmorHudPlugin.enabled = !ArmorHudPlugin.enabled;
        }));
        this.addRenderableWidget(CycleButton.onOffBuilder(ArmorHudPlugin.filled).create(this.width / 2 - 49, this.height / 4 + 36 - 16, 98, 20, new TranslatableComponent(LezardCore.NAMESPACE + ".filled"), (p_170168_, p_170169_) -> {
            ArmorHudPlugin.filled = !ArmorHudPlugin.filled;
        }));
        this.addRenderableWidget(CycleButton.onOffBuilder(ArmorHudPlugin.rainbow).create(this.width / 2 - 49, this.height / 4 + 60 - 16, 98, 20, new TranslatableComponent(LezardCore.NAMESPACE + ".rainbow"), (p_170168_, p_170169_) -> {
            ArmorHudPlugin.rainbow = !ArmorHudPlugin.rainbow;
        }));
        this.addRenderableWidget(CycleButton.builder(Colors::getName).withValues(Colors.values()).withInitialValue(ArmorHudPlugin.colors).create(this.width / 2 - 49, this.height / 4 + 84 - 16, 98, 20, new TranslatableComponent(LezardCore.NAMESPACE + ".color"), (p_167441_, p_167442_) -> {
            ArmorHudPlugin.colors = p_167442_;
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 156 -16, 98, 20, new TranslatableComponent(LezardCore.NAMESPACE + ".goBack"), (p_96335_) -> {
            PluginFileManager.writeJson(ArmorHudPlugin.name, "enabled", ArmorHudPlugin.enabled);
            PluginFileManager.writeJson(ArmorHudPlugin.name, "filled", ArmorHudPlugin.filled);
            PluginFileManager.writeJson(ArmorHudPlugin.name, "rainbow", ArmorHudPlugin.rainbow);
            PluginFileManager.writeJson(ArmorHudPlugin.name, "color", ArmorHudPlugin.colors.getLiteralName());
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
