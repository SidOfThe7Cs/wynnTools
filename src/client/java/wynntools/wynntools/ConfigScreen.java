package wynntools.wynntools;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.Option;
import java.util.ArrayList;
import java.util.List;

public class ConfigScreen {
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

        public void addOption(Option<?> option) {
            options.add(option);
        }

        public List<Option<?>> getOptions() {
            return options;
        }
    }

    public static void addOption(Categories category, Option<?> option) {
        category.addOption(option);
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
                .save(Config::saveConfig)
                .build()
                .generateScreen(parent);
    }

}
