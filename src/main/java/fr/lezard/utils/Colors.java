package fr.lezard.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.awt.*;
import java.util.Locale;

import fr.lezard.Lezard;

public enum Colors {
    WHITE(Color.WHITE.getRGB(), "WHITE"),
    LIGHT_GRAY(Color.LIGHT_GRAY.getRGB(), "LIGHT_GRAY"),
    GRAY(Color.GRAY.getRGB(), "GRAY"),
    DARK_GRAY(Color.DARK_GRAY.getRGB(), "DARK_GRAY"),
    BLACK(Color.BLACK.getRGB(), "BLACK"),
    RED(Color.RED.getRGB(), "RED"),
    PINK(Color.PINK.getRGB(), "PINK"),
    ORANGE(Color.ORANGE.getRGB(), "ORANGE"),
    YELLOW(Color.YELLOW.getRGB(), "YELLOW"),
    GREEN(Color.GREEN.getRGB(), "GREEN"),
    MAGENTA(Color.MAGENTA.getRGB(), "MAGENTA"),
    CYAN(Color.CYAN.getRGB(), "CYAN"),
    BLUE(Color.BLUE.getRGB(), "BLUE");

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
        return new TranslatableComponent(Lezard.NAMESPACE + ".color." + name.toLowerCase(Locale.ROOT));
    }
    public int getRgb() {
        return rgb;
    }
}