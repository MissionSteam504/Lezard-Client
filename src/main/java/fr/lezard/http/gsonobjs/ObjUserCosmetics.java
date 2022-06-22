package fr.lezard.http.gsonobjs;

import java.util.UUID;

public class ObjUserCosmetics {
	private String uuid;
	private String capeStyle;
	private int googlyEyes;
	private Hat hat;
	
	public class Hat{
		private int enabled;
		private int r;
		private int g;
		private int b;
		
		public boolean isEnabled() {
			return enabled == 1;
		}
		public float[] getColors() {
			return new float[] {convert(r), convert(g), convert(b)};
		}
		
		private float convert(int color) {
			return (color / 255F);
		}
		
	}

	public UUID getUuid() {
		return UUID.fromString(uuid);
	}

	public String getCapeStyle() {
		return capeStyle;
	}
	
	public boolean hasCape() {
		return capeStyle != null;
	}

	public boolean isGooglyEyesEnabled() {
		return googlyEyes == 1;
	}

	public Hat getHat() {
		return hat;
	}
	
	
	
}
