package fr.lezard.plugins;

import fr.lezard.utils.Colors;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.KeyMapping;

public class PluginHUD extends Plugin{
	private int posX;
	private int posY;
	private int height;
	private int width;
	
	private boolean dragged;
	
    public boolean isDragged() {
		return dragged;
	}

	public void setDragged(boolean dragged) {
		this.dragged = dragged;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
		FileWriterJson.writeJson(getNamespace(), "posX", posX);
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
		FileWriterJson.writeJson(getNamespace(), "posY", posY);
	}

	private static boolean filled;
    private static boolean rainbow;
    private static Colors colors = null;

	public PluginHUD(String name, boolean enabled, Category category, String namespace, KeyMapping key) {
		super(name, enabled, category, namespace, key);
        filled = true;
        rainbow = false;
        posX = FileWriterJson.getInt(namespace, "posX");
        posY = FileWriterJson.getInt(namespace, "posY");
	}
	
	public static boolean isFilled() {
        return filled;
    }
    public static boolean isRainbow(){
        return rainbow;
    }
    public static Colors getColors(){
        return colors==null ? Colors.WHITE : colors;
    }
}
