package fr.lezard.utils.keystroke;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class KeyStroke {
    public static final KeyStroke FORWARD = new KeyStroke("W", false, Minecraft.getInstance().options.keyUp, 21, 1, 18, 18);
    public static final KeyStroke LEFT = new KeyStroke("Q", false, Minecraft.getInstance().options.keyLeft, 1, 21, 18, 18);
    public static final KeyStroke BACK = new KeyStroke("S", false, Minecraft.getInstance().options.keyDown, 21, 21, 18, 18);
    public static final KeyStroke RIGHT = new KeyStroke("D", false, Minecraft.getInstance().options.keyRight, 41, 21, 18, 18);


    public static final KeyStroke LMB_WITHOUT_BAR = new KeyStroke("LMB", false, Minecraft.getInstance().options.keyAttack, 1, 41, 28, 18);
    public static final KeyStroke RMB_WITHOUT_BAR = new KeyStroke("RMB", false, Minecraft.getInstance().options.keyUse, 31, 41, 28, 18);

    public static final KeyStroke LMB_WITH_BAR = new KeyStroke("LMB", false, Minecraft.getInstance().options.keyAttack, 1, 49, 28, 18);
    public static final KeyStroke RMB_WITH_BAR = new KeyStroke("RMB", false, Minecraft.getInstance().options.keyUse, 31, 49, 28, 18);

    public static final KeyStroke SPACE_BAR = new KeyStroke("-", true, Minecraft.getInstance().options.keyJump, 1, 41, 58, 6);
    public static final KeyStroke SPACE_BAR_WITH_SHIFT = new KeyStroke("-", true, Minecraft.getInstance().options.keyJump, 1, 41, 28, 6);

    public static final KeyStroke SHIFT = new KeyStroke("-", true, Minecraft.getInstance().options.keyShift, 31, 41, 28, 6);

    private final String name;
    private final KeyMapping keyMapping;
    private final int x, y, width, height;
    private final boolean bar;

    public KeyStroke(String name, Boolean bar, KeyMapping keyMapping, int x, int y, int width, int height){
        this.name = name;
        this.keyMapping = keyMapping;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bar = bar;
    }

    public String getName() {
        return this.name;
    }
    public boolean isPressed(){
        return this.keyMapping.isDown();
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public boolean isBar(){
        return bar;
    }
}
