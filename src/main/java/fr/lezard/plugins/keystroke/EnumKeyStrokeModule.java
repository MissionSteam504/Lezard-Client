package fr.lezard.plugins.keystroke;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Locale;

public enum EnumKeyStrokeModule {
    WASD("WASD", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT),
    WASD_MOUSE("WASD_MOUSE", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT, KeyStroke.LMB_WITHOUT_BAR, KeyStroke.RMB_WITHOUT_BAR),
    WASD_BAR("WASD_BAR", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT, KeyStroke.SPACE_BAR),
    WASD_BAR_MOUSE("WASD_BAR_MOUSE", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT, KeyStroke.SPACE_BAR, KeyStroke.LMB_WITH_BAR, KeyStroke.RMB_WITH_BAR),
    ;

    private final KeyStroke[] keyStrokes;
    private int width, height;
    private final String name;

    private EnumKeyStrokeModule(String name, KeyStroke... keyStrokes){
        this.name = name;
        this.keyStrokes = keyStrokes;

        for(KeyStroke k : keyStrokes){
            this.width = Math.max(this.width, k.getX() + k.getWidth());
            this.height = Math.max(this.height, k.getY() + k.getHeight());
        }
    }

    public KeyStroke[] getKeyStrokes() {
        return keyStrokes;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public Component getName() {
        return new TranslatableComponent("lezard.keyMode." + name.toLowerCase(Locale.ROOT));
    }

    public String getLiteralName(){
        return name;
    }
}
