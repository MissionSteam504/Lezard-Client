package fr.lezard.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.lezard.Lezard;

public class FileWriterJson {
	
	@SuppressWarnings("deprecation")
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
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(pluginName + "." + property, value);

        try (FileWriter file = new FileWriter(Lezard.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJson(String pluginName, String property, boolean value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(pluginName + "." + property, value);

        try (FileWriter file = new FileWriter(Lezard.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJson(String pluginName, String property, float value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(pluginName + "." + property, value);

        try (FileWriter file = new FileWriter(Lezard.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJson(String pluginName, String property, int value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(pluginName + "." + property, value);

        try (FileWriter file = new FileWriter(Lezard.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        if (jsonObject.has(pluginName + "." + property)) {
            return jsonObject.get(pluginName + "." + property).toString().replaceAll("\"", "");
        }
        return "";
    }
    public static boolean getBoolean(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        if (jsonObject.has(pluginName + "." + property)) {
            String newValue = jsonObject.get(pluginName + "." + property).toString().replaceAll("\"", "");
            return Boolean.parseBoolean(newValue);
        }
        return false;
    }
    public static int getInt(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        if(jsonObject.has(pluginName + "." + property)){
            String newValue = jsonObject.get(pluginName + "." + property).toString().replaceAll("\"", "");
            return Integer.parseInt(newValue);
        }
        return 0;
    }
    public static float getFloat(String pluginName, String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        if(jsonObject.has(pluginName + "." + property)){
            String newValue = jsonObject.get(pluginName + "." + property).toString().replaceAll("\"", "");
            return Float.parseFloat(newValue);
        }
        return 0f;
    }
    
    

    // Without :

    public static void writeJsonWithout(String property, String value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(property, value);

        try (FileWriter file = new FileWriter(Lezard.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJsonWithout(String property, boolean value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(property, value);

        try (FileWriter file = new FileWriter(Lezard.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeJsonWithout(String property, int value){
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        jsonObject.addProperty(property, value);

        try (FileWriter file = new FileWriter(Lezard.pluginFile)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getStringWithout(String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        if (jsonObject.has(property)) {
            return jsonObject.get(property).toString().replaceAll("\"", "");
        }
        return "";
    }
    public static boolean getBooleanWithout(String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        if (jsonObject.has(property)) {
            String newValue = jsonObject.get(property).toString().replaceAll("\"", "");
            return Boolean.parseBoolean(newValue);
        }
        return false;
    }
    public static int getIntWithout(String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        if(jsonObject.has(property)){
            String newValue = jsonObject.get(property).toString().replaceAll("\"", "");
            return Integer.parseInt(newValue);
        }
        return 0;
    }
    public static float getFloatWithout(String property) {
        JsonObject jsonObject = (JsonObject) jsonFileParser(Lezard.pluginFile);
        assert jsonObject != null;
        if(jsonObject.has(property)){
            String newValue = jsonObject.get(property).toString().replaceAll("\"", "");
            return Float.parseFloat(newValue);
        }
        return 0f;
    }
}
