package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.LezardCore;
import fr.lezard.PluginFileManager;
import fr.lezard.PluginsManager;
import fr.lezard.plugins.utils.Colors;
import fr.lezard.plugins.utils.HudPlugin;
import fr.lezard.plugins.utils.PluginPos;
import fr.lezard.screens.PluginsLocationScreen;
import fr.lezard.plugins.keystroke.EnumKeyStrokeModule;
import fr.lezard.plugins.keystroke.KeyStroke;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

import java.awt.*;

public class KeyStrokePlugin extends HudPlugin {
    public static boolean enabled;
    public static boolean filled;
    public static boolean rainbow;
    public static Colors colors;
    public static String name;

    public static EnumKeyStrokeModule keyMode = EnumKeyStrokeModule.WASD;

    public static int posX;
    public static int posY;

    public KeyStrokePlugin(String name, int posX, int posY){
        super(name, posX, posY);
        KeyStrokePlugin.name = name;
        KeyStrokePlugin.posX = posX;
        KeyStrokePlugin.posY = posY;
    }

    public void init() {
        enabled = isEnabled();
        filled = isFilled();
        rainbow = isRainbow();
        colors = getColors();

        if(!PluginFileManager.getString(name, "mode").equalsIgnoreCase("")){
            keyMode = EnumKeyStrokeModule.valueOf(PluginFileManager.getString(name, "mode"));
        }

        System.out.println(LezardCore.PREFIX + "KeyStroke HUD Enabled");
    }

    public void updatePos(){
        posX = PluginFileManager.getInt(name, "posX");
        posY = PluginFileManager.getInt(name, "posY");
    }

    public static void renderStroke(PoseStack poseStack){
        if(Minecraft.getInstance().options.renderDebug){
            return;
        }
        if(PluginsLocationScreen.fakeDrag && name.equalsIgnoreCase(PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(PluginsLocationScreen.tempPlugin)))){
            posX = PluginsLocationScreen.tempX;
            posY = PluginsLocationScreen.tempY;
        }
        if(filled) {
            GuiComponent.fill(poseStack, posX - PluginsLocationScreen.GAP, posY - PluginsLocationScreen.GAP, PluginPos.getWidth(name) + posX + PluginsLocationScreen.GAP, PluginPos.getHeight(name) + posY + PluginsLocationScreen.GAP, 0x2929292F);
        }
        for(KeyStroke k : keyMode.getKeyStrokes()){
            GuiComponent.fill(poseStack,
                    posX + k.getX(),
                    posY + k.getY(),
                    posX + k.getX() + k.getWidth(),
                    posY + k.getY() + k.getHeight(),
                    k.isPressed() ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 102).getRGB()
            );

            int textWidth = Minecraft.getInstance().font.width(k.getName());
            GuiComponent.drawString(poseStack,
                    Minecraft.getInstance().font,
                    k.getName(),
                    posX + k.getX() + k.getWidth() / 2 - textWidth / 2,
                    posY + k.getY() + k.getHeight() / 4,
                    rainbow ? PluginsManager.rainbowText() : colors.getRgb()
            );
        }

    }
}
