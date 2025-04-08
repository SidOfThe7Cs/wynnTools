package wynntools.wynntools;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

public class Config {
    private static final Path CONFIG_PATH = Paths.get("config", "wynntoolsconfigv2.json");
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of("wynntools", "config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(CONFIG_PATH)
                            .setJson5(true)
                            .build())
                    .build();

    @SerialEntry private static boolean Outer_Void_Item_Helper_Main_Toggle = true;
    @SerialEntry private static Utils.Rarities Metal_Swarf_Color = Utils.Rarities.UNIQUE;
    @SerialEntry private static Utils.Rarities Outer_Void_Show_Lines_At_Rarity = Utils.Rarities.RARE;
    @SerialEntry private static Utils.Rarities Outer_Void_Lowest_Rarity_To_Show = Utils.Rarities.UNIQUE;
    @SerialEntry private static boolean Outer_Void_Show_Distance_Numbers = true;
    @SerialEntry private static int Outer_Void_Item_Helper_Range = 400;
    @SerialEntry private static int Outer_Void_Max_Lines_To_Draw = 6;
    @SerialEntry private static boolean Outer_Void_Next_Item_Unique_Color = false;
    @SerialEntry private static Color Outer_Void_Next_Item_Color = Color.white;

    // getters and setters
    public static Utils.Rarities getMetal_Swarf_Color() {
        return Metal_Swarf_Color;
    }

    public static void setMetal_Swarf_Color(Utils.Rarities metal_Swarf_Color) {
        Metal_Swarf_Color = metal_Swarf_Color;
    }

    public static Utils.Rarities getOuter_Void_Show_Lines_At_Rarity() {
        return Outer_Void_Show_Lines_At_Rarity;
    }

    public static void setOuter_Void_Show_Lines_At_Rarity(Utils.Rarities outer_Void_Show_Lines_At_Rarity) {
        Outer_Void_Show_Lines_At_Rarity = outer_Void_Show_Lines_At_Rarity;
    }

    public static boolean isOuter_Void_Item_Helper_Main_Toggle() {
        return Outer_Void_Item_Helper_Main_Toggle;
    }

    public static void setOuter_Void_Item_Helper_Main_Toggle(boolean outer_Void_Item_Helper_Main_Toggle) {
        Outer_Void_Item_Helper_Main_Toggle = outer_Void_Item_Helper_Main_Toggle;
    }

    public static Utils.Rarities getOuter_Void_Lowest_Rarity_To_Show() {
        return Outer_Void_Lowest_Rarity_To_Show;
    }

    public static void setOuter_Void_Lowest_Rarity_To_Show(Utils.Rarities lowest_rarity_To_Show) {
        Outer_Void_Lowest_Rarity_To_Show = lowest_rarity_To_Show;
    }

    public static int getOuter_Void_Item_Helper_Range() {
        return Outer_Void_Item_Helper_Range;
    }

    public static void setOuter_Void_Item_Helper_Range(int outer_Void_Item_Helper_Range) {
        Outer_Void_Item_Helper_Range = outer_Void_Item_Helper_Range;
    }

    public static boolean isOuter_Void_Show_Distance_Numbers() {
        return Outer_Void_Show_Distance_Numbers;
    }

    public static void setOuter_Void_Show_Distance_Numbers(boolean outer_Void_Show_Distance_Numbers) {
        Outer_Void_Show_Distance_Numbers = outer_Void_Show_Distance_Numbers;
    }

    public static int getOuter_Void_Max_Lines_To_Draw() {
        return Outer_Void_Max_Lines_To_Draw;
    }

    public static void setOuter_Void_Max_Lines_To_Draw(int outer_Void_Max_Lines_To_Draw) {
        Outer_Void_Max_Lines_To_Draw = outer_Void_Max_Lines_To_Draw;
    }

    public static boolean isOuter_Void_Next_Item_Unique_Color() {
        return Outer_Void_Next_Item_Unique_Color;
    }

    public static void setOuter_Void_Next_Item_Unique_Color(boolean outer_Void_Next_Item_Unique_Color) {
        Outer_Void_Next_Item_Unique_Color = outer_Void_Next_Item_Unique_Color;
    }

    public static Color getOuter_Void_Next_Item_Color() {
        return Outer_Void_Next_Item_Color;
    }

    public static void setOuter_Void_Next_Item_Color(Color outer_Void_Next_Item_Color) {
        Outer_Void_Next_Item_Color = outer_Void_Next_Item_Color;
    }

    public enum Categories {
        OUTER_VOID("Outer Void");

        private final String displayName;
        private final List<Option<?>> options;

        Categories(String displayName) {
            this.displayName = displayName;
            this.options = new ArrayList<>();
        }

        public String getDisplayName() {
            return displayName;
        }

        private void addOption(Option<?> option) {
            options.add(option);
        }

        public List<Option<?>> getOptions() {
            return options;
        }
    }

    public static Screen createConfigScreen(Screen parent) {

        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Text.of("Why are you using the narrator?"));

        // Create a single category
        ConfigCategory.Builder categoryBuilder = ConfigCategory.createBuilder()
                .name(Text.of("Settings")); // Set the name for the category

        // Iterate over all categories
        for (Categories category : Categories.values()) {
            if (!category.getOptions().isEmpty()) {
                OptionGroup.Builder groupBuilder = OptionGroup.createBuilder()
                        .name(Text.of(category.getDisplayName()));

                // Add options to the group
                for (Option<?> option : category.getOptions()) {
                    groupBuilder.option(option);
                }

                // Add the group to the category
                categoryBuilder.group(groupBuilder.build());
            }
        }

        // Build the category and add it to the builder
        builder.category(categoryBuilder.build());

        return builder
                .save(Config.HANDLER::save)
                .build()
                .generateScreen(parent);
    }

    public static void addOption(Categories category, Option<?> option) {
        category.addOption(option);
    }
}
