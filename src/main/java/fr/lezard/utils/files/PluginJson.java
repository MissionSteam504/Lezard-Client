package fr.lezard.utils.files;

import java.io.FileWriter;

import fr.lezard.Lezard;

public class PluginJson {
	private String namespace;
	private boolean enabled;
	private String color;
	private boolean rainbow;
	private boolean filled;
	private String mode;
	private String secondColor;
	private int posX;
	private int posY;
	private float size;
	
	public PluginJson(String namespace) {
		this(namespace, false, "WHITE", false, false, "", "WHITE", 0, 0, 1f);
	}
	
	public PluginJson(String namespace, boolean enabled, String color, boolean rainbow, boolean filled, int posX, int posY, float size) {
		this(namespace, enabled, color, rainbow, filled, "", "WHITE", posX, posY, size);
	}
	
	public PluginJson(String namespace, boolean enabled, String color, boolean rainbow, boolean filled, String mode, int posX, int posY, float size) {
		this(namespace, enabled, color, rainbow, filled, mode, "WHITE", posX, posY, size);
	}
	
	public PluginJson(String namespace, boolean enabled, String color, boolean rainbow, boolean filled, int posX, String secondColor, int posY, float size) {
		this(namespace, enabled, color, rainbow, filled, "", secondColor, posX, posY, size);
	}
	
	public PluginJson(String namespace, boolean enabled, String color, boolean rainbow, boolean filled, String mode, String secondColor, int posX, int posY, float size) {
		this.namespace = namespace;
		this.enabled = enabled;
		this.color = color;
		this.rainbow = rainbow;
		this.filled = filled;
		this.mode = mode;
		this.secondColor = secondColor;
		this.posX = posX;
		this.posY = posY;
		this.size = size;
	}

	public String getNamespace() {
		return namespace;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public String getColor() {
		return color;
	}
	public boolean isRainbow() {
		return rainbow;
	}
	public boolean isFilled() {
		return filled;
	}
	public String getMode() {
		return mode;
	}
	public String getSecondColor() {
		return secondColor;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}
	public float getSize() {
		return size;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
    	try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
			return;
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing enabled to " + enabled + ": ");
			e.printStackTrace();
		}
	}
	public void setColor(String color) {
		this.color = color;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
			return;
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing color to " + color + ": ");
			e.printStackTrace();
		}
	}
	public void setRainbow(boolean rainbow) {
		this.rainbow = rainbow;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
			return;
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing rainbow to " + rainbow + ": ");
			e.printStackTrace();
		}
	}
	public void setFilled(boolean filled) {
		this.filled = filled;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
			return;
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing filled to " + filled + ": ");
			e.printStackTrace();
		}
	}
	public void setMode(String mode) {
		this.mode = mode;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
			return;
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing mode to " + mode + ": ");
			e.printStackTrace();
		}
	}
	public void setSecondColor(String secondColor) {
		this.secondColor = secondColor;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
			return;
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing secondColor to " + secondColor + ": ");
			e.printStackTrace();
		}
	}
	public void setPosX(int posX) {
		this.posX = posX;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
			return;
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing posX to " + posX + ": ");
			e.printStackTrace();
		}
	}
	public void setPosY(int posY) {
		this.posY = posY;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
			return;
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing posY to " + posY + ": ");
			e.printStackTrace();
		}
	}
	public void setSize(float size) {
		this.size = size;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
			return;
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing size to " + size + ": ");
			e.printStackTrace();
		}
	}
}
