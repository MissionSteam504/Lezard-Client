package fr.lezard.http;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import fr.lezard.Lezard;

public class HWID {
	public static String get(String username) {
		if(username.equalsIgnoreCase(Lezard.USERNAME)) {
			return "dev-hwid";
		}
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "error";
		}
		
        md.update(username.getBytes());

        byte byteData[] = md.digest();
        
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
	}
}
