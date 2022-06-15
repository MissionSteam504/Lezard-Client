package fr.lezard.utils.keystroke;

import fr.lezard.Lezard;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Locale;

public enum KeyStrokeModule {
    WASD("WASD", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT),
    WASD_MOUSE("WASD_MOUSE", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT, KeyStroke.LMB_WITHOUT_BAR, KeyStroke.RMB_WITHOUT_BAR),

    WASD_BAR("WASD_BAR", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT, KeyStroke.SPACE_BAR),
    WASD_DOUBLE_BAR("WASD_DOUBLE_BAR", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT, KeyStroke.SPACE_BAR_WITH_SHIFT, KeyStroke.SHIFT),

    WASD_JUMP_MOUSE("WASD_JUMP_MOUSE", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT, KeyStroke.SPACE_BAR, KeyStroke.LMB_WITH_BAR, KeyStroke.RMB_WITH_BAR),
    FULL("FULL", KeyStroke.FORWARD, KeyStroke.BACK, KeyStroke.LEFT, KeyStroke.RIGHT, KeyStroke.SPACE_BAR_WITH_SHIFT, KeyStroke.SHIFT, KeyStroke.LMB_WITH_BAR, KeyStroke.RMB_WITH_BAR),
    ;

    private final KeyStroke[] keyStrokes;
    private int width, height;
    private final String name;

    KeyStrokeModule(String name, KeyStroke... keyStrokes){
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
        return new TranslatableComponent(Lezard.NAMESPACE + ".keyMode." + name.toLowerCase(Locale.ROOT));
    }

    public String getLiteralName(){
        return name;
    }
}
