package fr.lezard.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.awt.*;
import java.util.Locale;

import fr.lezard.Lezard;

public enum Colors {
	// Thanks to HtmlColorCodes.com for the colors
	// https://htmlcolorcodes.com/minecraft-color-codes/
    BLACK(new Color(0, 0, 0).getRGB(), "BLACK"),
	DARK_BLUE(new Color(0, 0, 170).getRGB(), "DARK_BLUE"),
	DARK_GREEN(new Color(0, 170, 0).getRGB(), "DARK_GREEN"),
	DARK_AQUA(new Color(0, 170, 170).getRGB(), "DARK_AQUA"),
	DARK_RED(new Color(170, 0, 0).getRGB(), "DARK_RED"),
	DARK_PURPLE(new Color(170, 0, 170).getRGB(), "DARK_PURPLE"),
	GOLD(new Color(255, 170, 0).getRGB(), "GOLD"),
    GRAY(new Color(170, 170, 170).getRGB(), "GRAY"),
    DARK_GRAY(new Color(85, 85, 85).getRGB(), "DARK_GRAY"),
    BLUE(new Color(85, 85, 255).getRGB(), "BLUE"),
    GREEN(new Color(85, 170, 85).getRGB(), "GREEN"),
    AQUA(new Color(85, 255, 255).getRGB(), "AQUA"),
    RED(new Color(255, 85, 85).getRGB(), "RED"),
    LIGHT_PURPLE(new Color(255, 85, 255).getRGB(), "LIGHT_PURPLE"),
    YELLOW(new Color(255, 255, 85).getRGB(), "YELLOW"),
    WHITE(new Color(255, 255, 255).getRGB(), "WHITE"),
	;

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