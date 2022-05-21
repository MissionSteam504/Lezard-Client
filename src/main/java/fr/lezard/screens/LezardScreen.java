package fr.lezard.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sun.jna.platform.win32.ShTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class LezardScreen extends Screen {
    public LezardScreen() {
        super(new TranslatableComponent("lezard.gui.settings.title"));
    }


    protected void init() {
        this.createSettingsMenu();
    }

    public void createSettingsMenu() {
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 12 -16, 98, 20, new TranslatableComponent("lezard.plugins"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new PluginsLocationScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 36 -16, 98, 20, new TranslatableComponent("lezard.lezardSettings"), (p_96335_) -> {
            // Minecraft.getInstance().setScreen(new PluginsLocationScreen());
        }));

        this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 156 -16, 204, 20, new TranslatableComponent("menu.returnToGame"), (p_96335_) -> {
            Minecraft.getInstance().setScreen((Screen)null);
            Minecraft.getInstance().mouseHandler.grabMouse();
        }));
    }

    public void tick() {
        super.tick();
    }

    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        this.renderBackground(p_96562_);
        // this.renderDirtBackground(0);
        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
        drawCenteredString(p_96562_, this.font, this.title, this.width / 2, 15, 16777215);
    }
}
