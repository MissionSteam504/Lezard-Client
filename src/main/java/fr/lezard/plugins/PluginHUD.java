package fr.lezard.plugins;

import fr.lezard.utils.Colors;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;

public class PluginHUD extends Plugin{
	private int posX;
	private int posY;
	private float height;
	private float width;
	private float size;
	
	private boolean dragged;
	
    public boolean isDragged() {
		return dragged;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
		FileWriterJson.writeJson(getNamespace(), "size", size);
	}

	public void setDragged(boolean dragged) {
		this.dragged = dragged;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
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

	private boolean filled;
    private boolean rainbow;
    private Colors colors = null;

	public PluginHUD(String name, boolean enabled, Category category, String namespace, KeyMapping key, Screen screen) {
		super(name, enabled, category, namespace, key, screen);
        filled = FileWriterJson.getBoolean(namespace, "filled");
        rainbow = FileWriterJson.getBoolean(namespace, "rainbow");
        
        if(!FileWriterJson.getString(namespace, "color").equalsIgnoreCase("")){
            colors = Colors.valueOf(FileWriterJson.getString(namespace, "color"));
        }
        posX = FileWriterJson.getInt(namespace, "posX");
        posY = FileWriterJson.getInt(namespace, "posY");
        size = FileWriterJson.getFloat(namespace, "size");
        if(size==0f) {
        	size=1f;
        }
	}
	
	public boolean isFilled() {
        return this.filled;
    }
    public boolean isRainbow(){
        return this.rainbow;
    }
    public Colors getColors(){
        return this.colors==null ? Colors.WHITE : this.colors;
    }

	public void setFilled(boolean filled) {
		this.filled = filled;
		FileWriterJson.writeJson(getNamespace(), "filled", filled);
	}

	public void setRainbow(boolean rainbow) {
		this.rainbow = rainbow;
		FileWriterJson.writeJson(getNamespace(), "rainbow", rainbow);
	}

	public void setColors(Colors colors) {
		this.colors = colors;
		FileWriterJson.writeJson(getNamespace(), "color", colors.getLiteralName());
	}
}
