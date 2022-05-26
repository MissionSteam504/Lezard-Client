package fr.lezard.plugins.utils;

import fr.lezard.PluginsManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.awt.*;

public enum Colors {
    WHITE(Color.WHITE.getRGB(), "white"),
    LIGHT_GRAY(Color.LIGHT_GRAY.getRGB(), "light_gray"),
    GRAY(Color.GRAY.getRGB(), "gray"),
    DARK_GRAY(Color.DARK_GRAY.getRGB(), "light_gray"),
    BLACK(Color.BLACK.getRGB(), "black"),
    RED(Color.RED.getRGB(), "red"),
    PINK(Color.PINK.getRGB(), "pink"),
    ORANGE(Color.ORANGE.getRGB(), "orange"),
    YELLOW(Color.YELLOW.getRGB(), "yellow"),
    GREEN(Color.GREEN.getRGB(), "green"),
    MAGENTA(Color.MAGENTA.getRGB(), "magenta"),
    CYAN(Color.CYAN.getRGB(), "cyan"),
    BLUE(Color.BLUE.getRGB(), "blue");

    private int rgb;
    private String name;
    private Colors(int rgb, String name){
        this.rgb = rgb;
        this.name = name;
    }

    public String getLiteralName() {
        return name;
    }
    public Component getName() {
        return new TranslatableComponent("color." + name);
    }
    public int getRgb() {
        return rgb;
    }
}
