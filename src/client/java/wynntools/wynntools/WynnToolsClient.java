package wynntools.wynntools;

import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WynnToolsClient implements ClientModInitializer {

	private static final Identifier EXAMPLE_LAYER = Identifier.of("wynntools", "hud-example-layer");

	@Override
	public void onInitializeClient() {
		OuterVoidItemDatabase.init(); //this should just be automattically done without calling a function but i dont care

		HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, EXAMPLE_LAYER, OuterVoidItemDetection::renderItemsToHud));
		ClientTickEvents.END_CLIENT_TICK.register(OuterVoidItemDetection::processSeenItems);

		Config.HANDLER.load();

		//add config options
		Option<?> option;

		option = Option.<Boolean>createBuilder()
				.name(Text.of("Outer Void Item Helper Main Toggle"))
				.description(OptionDescription.of(Text.of("toggles the outer void item helper")))
				.binding(true, Config::isOuter_Void_Item_Helper_Main_Toggle, Config::setOuter_Void_Item_Helper_Main_Toggle)
				.controller(TickBoxControllerBuilder::create)
				.build();
		Config.addOption(Config.Categories.OUTER_VOID, option);

		option = ButtonOption.createBuilder()
				.name(Text.of("clear cached items"))
				.description(OptionDescription.of(Text.of("")))
				.action((screen, button) -> {
					// Action to perform when the button is pressed
					OuterVoidItemDetection.clearCache();
				})
				.build();
		Config.addOption(Config.Categories.OUTER_VOID, option);

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
		Config.addOption(Config.Categories.OUTER_VOID, option);

		option = Option.<Utils.Rarities>createBuilder()
				.name(Text.of("Show Lines Above Rarity"))
				.description(OptionDescription.of(Text.of("above what rarity should lines to the item be shown")))
				.binding(
						Utils.Rarities.UNIQUE,
						Config::getOuter_Void_Show_Lines_Above_Rarity,
						Config::setOuter_Void_Show_Lines_Above_Rarity
				)
				.controller(optionC -> EnumControllerBuilder.create(optionC)
						.enumClass(Utils.Rarities.class)
				)
				.build();
		Config.addOption(Config.Categories.OUTER_VOID, option);

		option = Option.<Utils.Rarities>createBuilder()
				.name(Text.of("Show items at rarity"))
				.description(OptionDescription.of(Text.of("at what rarity should item be shown")))
				.binding(
						Utils.Rarities.RARE,
						Config::getOuter_Void_Lowest_Rarity_To_Show,
						Config::setOuter_Void_Lowest_Rarity_To_Show
				)
				.controller(optionC -> EnumControllerBuilder.create(optionC)
						.enumClass(Utils.Rarities.class)
				)
				.build();
		Config.addOption(Config.Categories.OUTER_VOID, option);

		option = Option.<Boolean>createBuilder()
				.name(Text.of("show distance numbers to each item"))
				.description(OptionDescription.of(Text.of("shows how far away all items are")))
				.binding(true, Config::isOuter_Void_Show_Distance_Numbers, Config::setOuter_Void_Show_Distance_Numbers)
				.controller(TickBoxControllerBuilder::create)
				.build();
		Config.addOption(Config.Categories.OUTER_VOID, option);

		option = Option.<Integer>createBuilder()
				.name(Text.of("the range around you items should be shown"))
				.description(OptionDescription.of(Text.of("")))
				.binding(300, Config::getOuter_Void_Item_Helper_Range, Config::setOuter_Void_Item_Helper_Range)
				.controller(IntegerFieldControllerBuilder::create)
				.build();
		Config.addOption(Config.Categories.OUTER_VOID, option);

		option = Option.<Integer>createBuilder()
				.name(Text.of("the max number of lines to draw on the screen at once"))
				.description(OptionDescription.of(Text.of("prioritizes rarity and then distance")))
				.binding(20, Config::getOuter_Void_Max_Lines_To_Draw, Config::setOuter_Void_Max_Lines_To_Draw)
				.controller(IntegerFieldControllerBuilder::create)
				.build();
		Config.addOption(Config.Categories.OUTER_VOID, option);
	}
}