package fr.lezard.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import fr.lezard.http.gsonobjs.ObjGlobalSettings;
import fr.lezard.http.gsonobjs.ObjIsBanned;
import fr.lezard.http.gsonobjs.ObjIsWhitelisted;
import net.minecraft.client.Minecraft;

public class HTTPFunctions {
	private static Gson gson = new Gson();
	
	public static void sendHWIDMap() {
		// Minecraft minecraft = Minecraft.getInstance();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uuid", Minecraft.getInstance().getUser().getUuid()));
		params.add(new BasicNameValuePair("username", Minecraft.getInstance().getUser().getName()));
		params.add(new BasicNameValuePair("hwid", HWID.get(Minecraft.getInstance().getUser().getName())));
		HTTPUtils.sendPostAsync(HTTPEndPoints.MAP_UUID, params);
	}
	
	public static boolean isAPIUp() {
		HTTPReply reply = HTTPUtils.sendGet(HTTPEndPoints.BASE_URL);
		return reply.getStatusCode() == 200;
	}
	
	public static boolean isBanned() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("hwid", HWID.get(Minecraft.getInstance().getUser().getName())));
		HTTPReply reply = HTTPUtils.sendGet(HTTPEndPoints.IS_BANNED, params);
		
		if(reply.getStatusCode() == 200) {
			ObjIsBanned obj = gson.fromJson(reply.getBody(), ObjIsBanned.class);
			return obj.isBanned();
		}
		return false;
	}
	
	public static String getBanReason() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("hwid", HWID.get(Minecraft.getInstance().getUser().getName())));
		HTTPReply reply = HTTPUtils.sendGet(HTTPEndPoints.IS_BANNED, params);
		
		if(reply.getStatusCode() == 200) {
			ObjIsBanned obj = gson.fromJson(reply.getBody(), ObjIsBanned.class);
			return obj.getReason();
		}
		return "Uknown";
	}
	
	public static boolean isWhitelisted() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("hwid", HWID.get(Minecraft.getInstance().getUser().getName())));
		HTTPReply reply = HTTPUtils.sendGet(HTTPEndPoints.IS_WHITELISTED, params);
		
		if(reply.getStatusCode() == 200) {
			ObjIsWhitelisted obj = gson.fromJson(reply.getBody(), ObjIsWhitelisted.class);
			return obj.isWhitelisted();
		}
		return false;
	}
	
	public static ObjGlobalSettings downloadGlobalSettings() {
		return gson.fromJson(HTTPUtils.sendGet(HTTPEndPoints.GLOBAL_SETIINGS).getBody(), ObjGlobalSettings.class);
	}
}
