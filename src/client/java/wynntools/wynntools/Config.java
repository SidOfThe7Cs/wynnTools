package wynntools.wynntools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Config {
    // TODO make some kind of list that auto saves and loads and has getter and setters for each option added to it
    private static Utils.Rarities Metal_Swarf_Color = Utils.Rarities.UNIQUE;
    private static Utils.Rarities Show_Lines_Above_Rarity = Utils.Rarities.UNIQUE;
    private static boolean Outer_Void_Item_Helper_Main_Toggle = true;
    private static Utils.Rarities Lowest_rarity_To_Show = Utils.Rarities.UNIQUE;

    private static final Path CONFIG_PATH = Paths.get("config", "wynntoolsconfig.json");

    // Add a map to store HUD elements by name
    private static final Map<String, TextHudElement> hudElementMap = new HashMap<>();


    public static void saveConfig() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Metal_Swarf_Color", getMetal_Swarf_Color());
        json.put("Show_Lines_Above_Rarity", getShow_Lines_Above_Rarity());
        json.put("Outer_Void_Item_Helper_Main_Toggle", isOuter_Void_Item_Helper_Main_Toggle());
        json.put("Lowest_rarity_To_Show", getLowest_rarity_To_Show());


        JSONArray elementsArray = new JSONArray();
        for (TextHudElement element : hudElementMap.values()) {
            JSONObject elementJson = new JSONObject();
            elementJson.put("name", element.getName());
            elementJson.put("x", element.getX());
            elementJson.put("y", element.getY());
            elementJson.put("text", element.getText());
            elementJson.put("colour", element.getColour());
            elementJson.put("scale", element.getScale());
            elementsArray.put(elementJson);
        }
        json.put("hudElements", elementsArray);

        try {
            Files.write(CONFIG_PATH, json.toString(4).getBytes()); // Pretty print with 4 spaces
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = new String(Files.readAllBytes(CONFIG_PATH));
                JSONObject obj = new JSONObject(json);

                setOuter_Void_Item_Helper_Main_Toggle(obj.getBoolean("Outer_Void_Item_Helper_Main_Toggle"));

                String Metal_Swarf_Color = obj.optString("Metal_Swarf_Color", "UNIQUE");
                try {
                    setMetal_Swarf_Color(Utils.Rarities.valueOf(Metal_Swarf_Color));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid Metal_Swarf_Color value: " + Metal_Swarf_Color + ". Using default value.");
                    setMetal_Swarf_Color(Utils.Rarities.UNIQUE);
                }

                String Show_Lines_Above_Rarity = obj.optString("Show_Lines_Above_Rarity", "RARE");
                try {
                    setShow_Lines_Above_Rarity(Utils.Rarities.valueOf(Show_Lines_Above_Rarity));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid Show_Lines_Above_Rarity value: " + Show_Lines_Above_Rarity + ". Using default value.");
                    setShow_Lines_Above_Rarity(Utils.Rarities.UNIQUE);
                }

                String Lowest_rarity_To_Show = obj.optString("Lowest_rarity_To_Show", "UNIQUE");
                try {
                    setLowest_rarity_To_Show(Utils.Rarities.valueOf(Lowest_rarity_To_Show));
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid Lowest_rarity_To_Show value: " + Lowest_rarity_To_Show + ". Using default value.");
                    setLowest_rarity_To_Show(Utils.Rarities.UNIQUE);
                }

                JSONArray elementsArray = obj.getJSONArray("hudElements");
                hudElementMap.clear();

                for (int i = 0; i < elementsArray.length(); i++) {
                    JSONObject elementJson = elementsArray.getJSONObject(i);
                    if (elementJson.has("name")) {
                        String name = elementJson.getString("name");
                        int x = elementJson.has("x") ? elementJson.getInt("x") : 100;
                        int y = elementJson.has("y") ? elementJson.getInt("y") : 100;
                        String text = elementJson.has("text") ? elementJson.getString("text") : "notextwasfound";
                        int colour = elementJson.has("colour") ? elementJson.getInt("colour") : 0xFF0000;
                        float scale = elementJson.has("scale") ? (float) elementJson.getDouble("scale") : 1;
                        TextHudElement element = new TextHudElement(x, y, text, colour, name, scale);
                        hudElementMap.put(name, element);
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addHudElement(TextHudElement element) throws JSONException {
        hudElementMap.put(element.getName(), element);
        saveConfig();
    }
    public static TextHudElement getHudElementByName(String name) {
        return hudElementMap.get(name);
    }
    public static Map<String, TextHudElement> getHudElements() {
        return hudElementMap;
    }
    public static boolean exists(String name){
        return hudElementMap.containsKey(name);
    }


    // getters and setters
    public static Utils.Rarities getMetal_Swarf_Color() {
        return Metal_Swarf_Color;
    }

    public static void setMetal_Swarf_Color(Utils.Rarities metal_Swarf_Color) {
        Metal_Swarf_Color = metal_Swarf_Color;
    }

    public static Utils.Rarities getShow_Lines_Above_Rarity() {
        return Show_Lines_Above_Rarity;
    }

    public static void setShow_Lines_Above_Rarity(Utils.Rarities show_Lines_Above_Rarity) {
        Show_Lines_Above_Rarity = show_Lines_Above_Rarity;
    }

    public static boolean isOuter_Void_Item_Helper_Main_Toggle() {
        return Outer_Void_Item_Helper_Main_Toggle;
    }

    public static void setOuter_Void_Item_Helper_Main_Toggle(boolean outer_Void_Item_Helper_Main_Toggle) {
        Outer_Void_Item_Helper_Main_Toggle = outer_Void_Item_Helper_Main_Toggle;
    }

    public static Utils.Rarities getLowest_rarity_To_Show() {
        return Lowest_rarity_To_Show;
    }

    public static void setLowest_rarity_To_Show(Utils.Rarities lowest_rarity_To_Show) {
        Lowest_rarity_To_Show = lowest_rarity_To_Show;
    }
}
