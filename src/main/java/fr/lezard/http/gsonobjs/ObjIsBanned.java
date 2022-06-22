package fr.lezard.http.gsonobjs;

public class ObjIsBanned {
	private String hwid;
	private int isBanned;
	private String reason;
	
	public String getHwid() {
		return hwid;
	}
	public boolean isBanned() {
		return isBanned == 1;
	}
	public String getReason() {
		return reason;
	}
}
