package fr.lezard.plugins;

import fr.lezard.utils.Colors;
import fr.lezard.utils.Utils;
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
		String size2 = String.format("%.2f%%", size).replace("%", "").replace(",", ".");
		Utils.getPlugin(getNamespace()).setSize(Float.parseFloat(size2));
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
		Utils.getPlugin(getNamespace()).setPosX(posX);
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
		Utils.getPlugin(getNamespace()).setPosY(posY);
	}

	private boolean filled;
    private boolean rainbow;
    private Colors colors = null;

	public PluginHUD(String name, boolean enabled, Category category, String namespace, KeyMapping key, Screen screen) {
		super(name, enabled, category, namespace, key, screen);
        filled = Utils.getPlugin(namespace).isFilled();
        rainbow = Utils.getPlugin(namespace).isRainbow();
        
        if(!Utils.getPlugin(namespace).getColor().equalsIgnoreCase("")){
            colors = Colors.valueOf(Utils.getPlugin(namespace).getColor());
        }
        posX = Utils.getPlugin(namespace).getPosX();
        posY = Utils.getPlugin(namespace).getPosY();
        size = Utils.getPlugin(namespace).getSize();
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
		Utils.getPlugin(getNamespace()).setFilled(filled);
	}

	public void setRainbow(boolean rainbow) {
		this.rainbow = rainbow;
		Utils.getPlugin(getNamespace()).setRainbow(rainbow);
	}

	public void setColors(Colors colors) {
		this.colors = colors;
		Utils.getPlugin(getNamespace()).setColor(colors.getLiteralName());
	}
}
