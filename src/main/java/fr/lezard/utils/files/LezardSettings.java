package fr.lezard.utils.files;

import java.io.FileWriter;

import fr.lezard.Lezard;

public class LezardSettings {
	private int alpha;
	private int gap;
	private boolean showAnchors;
	private int rainbowSpeed;
	
	private PluginJson[] plugins;
	
	public int getAlpha() {
		return alpha;
	}
	public int getGap() {
		return gap;
	}
	public boolean isShowAnchors() {
		return showAnchors;
	}
	public int getRainbowSpeed() {
		return rainbowSpeed;
	}
	public PluginJson[] getPlugins() {
		return plugins;
	}
	public void setAlpha(int alpha) {
		this.alpha = alpha;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing alpha to " + gap + ": ");
			e.printStackTrace();
		}
	}
	public void setGap(int gap) {
		this.gap = gap;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing gap to " + gap + ": ");
			e.printStackTrace();
		}
	}
	public void setShowAnchors(boolean showAnchors) {
		this.showAnchors = showAnchors;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing showAnchors to " + showAnchors + ": ");
			e.printStackTrace();
		}
	}
	public void setRainbowSpeed(int rainbowSpeed) {
		this.rainbowSpeed = rainbowSpeed;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing rainbowSpeed to " + rainbowSpeed + ": ");
			e.printStackTrace();
		}
	}
	
	public void setPlugins(PluginJson[] plugins) {
		this.plugins = plugins;
		try {
    		FileWriter file = new FileWriter(Lezard.settingsFile);
			Lezard.gsonBuilder.toJson(Lezard.settings, file);
			file.close();
		} catch (Exception e) {
			Lezard.LOGGER.error(Lezard.PREFIX + "Error when changing plugins to " + plugins.toString() + ": ");
			e.printStackTrace();
		}
	}
}
