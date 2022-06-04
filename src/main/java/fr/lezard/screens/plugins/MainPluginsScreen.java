package fr.lezard.screens.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.LezardCore;
import fr.lezard.screens.PluginsLocationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class MainPluginsScreen extends Screen {
    public MainPluginsScreen() {
        super(new TranslatableComponent(LezardCore.NAMESPACE + ".gui.plugins.title"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 -24 -16, 98, 20, new TranslatableComponent("lezard.plugin.simplifiedDebug"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new SimplifiedDebugScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 -16, 98, 20, new TranslatableComponent("lezard.plugin.itemPhysics"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new ItemPhysicsScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 24 -16, 98, 20, new TranslatableComponent("lezard.plugin.inGameTime"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new InGameTimeScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 48 -16, 98, 20, new TranslatableComponent("lezard.plugin.realTime"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new RealTimeScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 72 -16, 98, 20, new TranslatableComponent("lezard.plugin.armorHud"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new ArmorHudScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 96 -16, 98, 20, new TranslatableComponent("lezard.plugin.fpsHud"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new FpsHudScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 120 -16, 98, 20, new TranslatableComponent("lezard.plugin.compassHud"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new CompassHudScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 144 -16, 98, 20, new TranslatableComponent("lezard.plugin.keystrokeHud"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new KeyStrokeScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 168 -16, 98, 20, new TranslatableComponent("lezard.goBack"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new PluginsLocationScreen());
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
