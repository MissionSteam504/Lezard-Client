package fr.lezard.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

public class HTTPUtils {

	private static final Logger LOGGER = LogUtils.getLogger();

	public static HTTPReply sendGet(String endpoint) {
		return sendGet(endpoint, null);
	}
	
	public static HTTPReply sendGet(String endpoint, List<NameValuePair> params) {
		try {
			if(params != null) {
				endpoint += "?" + URLEncodedUtils.format(params, "UTF-8");
			}
			HttpGet httpGet = new HttpGet(endpoint);
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			return new HTTPReply(httpResponse);
		}catch(Exception e) {
			LOGGER.error("An error occured when getting httpResponse");
			e.printStackTrace();
			return new HTTPReply(null);
		}
	}
	
	public HTTPReply sendPost(String endpoint) {
		return sendPost(endpoint, null);
	}
	
	public static HTTPReply sendPost(String endpoint, List<NameValuePair> params) {
		try {
			HttpPost httpPost = new HttpPost(endpoint);
			if(params != null) {
				httpPost.setEntity(new UrlEncodedFormEntity(params));
			}
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			return new HTTPReply(httpResponse);
			
		}catch(Exception e) {
			LOGGER.error("An error occured when getting httpResponse");
			e.printStackTrace();
			return new HTTPReply(null);
		}
	}
	
	public static void sendPostAsync(String endpoint, List<NameValuePair> params) {
		 new Thread() {
			 @Override
			 public void run() {
				 sendPost(endpoint, params);
			 }
		 }.start();
	}
	
	public static boolean downloadFile(String endpoint, File path) {
		try {
			InputStream stream = new URL(endpoint).openStream();
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			IOUtils.copy(stream, fileOutputStream);
			IOUtils.closeQuietly(fileOutputStream);
			IOUtils.closeQuietly(stream);
			return true;
		}catch(Exception e) {
			LOGGER.error("An error occured when downloading file");
			e.printStackTrace();
			return false;
		}
	}
}
