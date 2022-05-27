package fr.lezard;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

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

    // With PluginName :

    public static void writeJson(String pluginName, String property, String value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(pluginName + "." + property, value);

        try (FileWriter file = new FileWriter(PluginsManager.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJson(String pluginName, String property, boolean value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        // JsonObject plugin = jsonObject.getAsJsonObject(pluginName);
        assert jsonObject != null;
        jsonObject.addProperty(pluginName + "." + property, value);

        try (FileWriter file = new FileWriter(PluginsManager.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJson(String pluginName, String property, int value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(pluginName + "." + property, value);

        try (FileWriter file = new FileWriter(PluginsManager.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        if (jsonObject.has(pluginName + "." + property)) {
            return jsonObject.get(pluginName + "." + property).toString().replaceAll("\"", "");
        }
        return "";
    }
    public static boolean getBoolean(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        if (jsonObject.has(pluginName + "." + property)) {
            String newValue = jsonObject.get(pluginName + "." + property).toString().replaceAll("\"", "");
            return Boolean.parseBoolean(newValue);
        }
        return false;
    }
    public static int getInt(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        if(jsonObject.has(pluginName + "." + property)){
            String newValue = jsonObject.get(pluginName + "." + property).toString().replaceAll("\"", "");
            return Integer.parseInt(newValue);
        }
        return 0;
    }

    // Without :

    public static void writeJsonWithout(String property, String value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(property, value);

        try (FileWriter file = new FileWriter(PluginsManager.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJsonWithout(String property, boolean value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        // JsonObject plugin = jsonObject.getAsJsonObject(pluginName);
        assert jsonObject != null;
        jsonObject.addProperty(property, value);

        try (FileWriter file = new FileWriter(PluginsManager.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJsonWithout(String property, int value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(property, value);

        try (FileWriter file = new FileWriter(PluginsManager.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStringWithout(String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        if (jsonObject.has(property)) {
            return jsonObject.get(property).toString().replaceAll("\"", "");
        }
        return "";
    }
    public static boolean getBooleanWithout(String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        if (jsonObject.has(property)) {
            String newValue = jsonObject.get(property).toString().replaceAll("\"", "");
            return Boolean.parseBoolean(newValue);
        }
        return false;
    }
    public static int getIntWithout(String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(PluginsManager.pluginFile);
        assert jsonObject != null;
        if(jsonObject.has(property)){
            String newValue = jsonObject.get(property).toString().replaceAll("\"", "");
            return Integer.parseInt(newValue);
        }
        return 0;
    }
}
