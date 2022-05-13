package fr.lezard.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.LezardCore;
import fr.lezard.PluginsManager;
import fr.lezard.plugins.ArmorHudPlugin;
import fr.lezard.plugins.HudPlugin;
import fr.lezard.plugins.Plugin;
import fr.lezard.screens.plugins.MainPluginsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import org.checkerframework.checker.units.qual.A;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class PluginsLocationScreen extends Screen {
    public static boolean dragMenu;

    public PluginsLocationScreen() {
        super(new TranslatableComponent("lezard.gui.plugins.title"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 24 -16, 98, 20, new TranslatableComponent("lezard.plugins.edit"), (p_96335_) -> {
            dragMenu = false;
            Minecraft.getInstance().setScreen(new MainPluginsScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 144 -16, 98, 20, new TranslatableComponent("lezard.goBack"), (p_96335_) -> {
            dragMenu = false;
            HudPlugin.writePos(LezardCore.getRandomNumberInRange(0, 200), ArmorHudPlugin.posY, new ArmorHudPlugin("Armor HUD"));
            Minecraft.getInstance().setScreen(new LezardScreen());
        }));



    }

    public void tick() {
        super.tick();
    }
    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        // this.renderBackground(p_96562_);

        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
    }
}
