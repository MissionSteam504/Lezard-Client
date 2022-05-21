package fr.lezard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.text.ParseException;

public class PluginFileManager {
    public static Object jsonFileParser(File file) {
        try {
            FileReader reader = new FileReader(file);
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(reader);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void writeJson(String pluginName, String property, String value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        JsonObject plugin = jsonObject.getAsJsonObject(pluginName);
        plugin.addProperty(property, value);

        try (FileWriter file = new FileWriter(PluginsManager.pluginFile)) {
            file.write(plugin.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJson(String pluginName, String property, boolean value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        JsonObject plugin = jsonObject.getAsJsonObject(pluginName);
        plugin.addProperty(property, value);

        try (FileWriter file = new FileWriter(PluginsManager.pluginFile)) {
            file.write(plugin.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJson(String pluginName, String property, int value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        JsonObject plugin = jsonObject.getAsJsonObject(pluginName);
        plugin.addProperty(property, value);

        try (FileWriter file = new FileWriter(PluginsManager.pluginFile)) {
            file.write(plugin.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        JsonObject plugin = jsonObject.getAsJsonObject(pluginName);
        String newValue = plugin.get(property).toString().replaceAll("\"", "");
        return newValue;
    }
    public static boolean getBoolean(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        JsonObject plugin = jsonObject.getAsJsonObject(pluginName);
        String newValue = plugin.get(property).toString().replaceAll("\"", "");
        return Boolean.parseBoolean(newValue);
    }
    public static int getInt(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        JsonObject plugin = jsonObject.getAsJsonObject(pluginName);
        String newValue = plugin.get(property).toString().replaceAll("\"", "");
        return Integer.parseInt(newValue);
    }
}
