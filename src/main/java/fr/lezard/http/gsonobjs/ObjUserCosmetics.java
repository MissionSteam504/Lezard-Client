package fr.lezard.http.gsonobjs;

import java.util.UUID;

public class ObjUserCosmetics {
	private String uuid;
	private String capeStyle;

	public UUID getUuid() {
		return UUID.fromString(uuid);
	}

	public String getCapeStyle() {
		return capeStyle;
	}
	
	public boolean hasCape() {
		return capeStyle != null;
	}
}
