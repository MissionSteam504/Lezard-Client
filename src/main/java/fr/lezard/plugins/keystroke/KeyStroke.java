package fr.lezard.plugins.keystroke;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class KeyStroke {
    public static final KeyStroke FORWARD = new KeyStroke("W", Minecraft.getInstance().options.keyUp, 21, 1, 18, 18);
    public static final KeyStroke LEFT = new KeyStroke("Q", Minecraft.getInstance().options.keyLeft, 1, 21, 18, 18);
    public static final KeyStroke BACK = new KeyStroke("S", Minecraft.getInstance().options.keyDown, 21, 21, 18, 18);
    public static final KeyStroke RIGHT = new KeyStroke("D", Minecraft.getInstance().options.keyRight, 41, 21, 18, 18);


    public static final KeyStroke LMB_WITHOUT_BAR = new KeyStroke("LMB", Minecraft.getInstance().options.keyAttack, 1, 41, 28, 18);
    public static final KeyStroke RMB_WITHOUT_BAR = new KeyStroke("RMB", Minecraft.getInstance().options.keyUse, 31, 41, 28, 18);

    public static final KeyStroke LMB_WITH_BAR = new KeyStroke("LMB", Minecraft.getInstance().options.keyAttack, 1, 49, 28, 18);
    public static final KeyStroke RMB_WITH_BAR = new KeyStroke("RMB", Minecraft.getInstance().options.keyUse, 31, 49, 28, 18);

    public static final KeyStroke SPACE_BAR = new KeyStroke("-", Minecraft.getInstance().options.keyJump, 1, 41, 58, 6);

    private final String name;
    private final KeyMapping keyMapping;
    private final int x, y, width, height;

    public KeyStroke(String name, KeyMapping keyMapping, int x, int y, int width, int height){
        this.name = name;
        this.keyMapping = keyMapping;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
}
