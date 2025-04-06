package wynntools.wynntools;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

public class WynnToolsClient implements ClientModInitializer {

	private static final Identifier EXAMPLE_LAYER = Identifier.of("wynntools", "hud-example-layer");

	@Override
	public void onInitializeClient() {
		OuterVoidItemDatabase.init(); //this should just be automattically done without calling a function but i dont care

		HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, EXAMPLE_LAYER, OuterVoidItemDetection::renderItemsToHud));
		ClientTickEvents.END_CLIENT_TICK.register(OuterVoidItemDetection::processSeenItems);

		Config.loadConfig();

		//add config options
		Option<?> option;

		option = Option.<Boolean>createBuilder()
				.name(Text.of("Outer Void Item Helper Main Toggle"))
				.description(OptionDescription.of(Text.of("toggles the outer void item helper")))
				.binding(true, Config::isOuter_Void_Item_Helper_Main_Toggle, Config::setOuter_Void_Item_Helper_Main_Toggle)
				.controller(TickBoxControllerBuilder::create)
				.build();
		ConfigScreen.addOption(ConfigScreen.Categories.OUTER_VOID, option);

		option = Option.<Utils.Rarities>createBuilder()
				.name(Text.of("Metal Swarf Color"))
				.description(OptionDescription.of(Text.of("should the metal swarf item have a special color")))
				.binding(
						Utils.Rarities.UNIQUE,
						Config::getMetal_Swarf_Color,
						Config::setMetal_Swarf_Color
				)
				.controller(optionC -> EnumControllerBuilder.create(optionC)
						.enumClass(Utils.Rarities.class)
				)
				.build();
		ConfigScreen.addOption(ConfigScreen.Categories.OUTER_VOID, option);

		option = Option.<Utils.Rarities>createBuilder()
				.name(Text.of("Show Lines Above Rarity"))
				.description(OptionDescription.of(Text.of("above what rarity should lines to the item be shown")))
				.binding(
						Utils.Rarities.UNIQUE,
						Config::getShow_Lines_Above_Rarity,
						Config::setShow_Lines_Above_Rarity
				)
				.controller(optionC -> EnumControllerBuilder.create(optionC)
						.enumClass(Utils.Rarities.class)
				)
				.build();
		ConfigScreen.addOption(ConfigScreen.Categories.OUTER_VOID, option);

	}
}