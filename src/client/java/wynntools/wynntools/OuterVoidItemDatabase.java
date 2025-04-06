package wynntools.wynntools;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.ItemEntity;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OuterVoidItemDatabase {
    public static class Pair{
        public String name;
        public float model;

        Pair(String name, float model){
            this.name = name;
            this.model = model;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair pair2){
                return Objects.equals(pair2.name, this.name) && Float.compare(pair2.model, this.model) == 0;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, model);
        }

    }

    // Predefined dataset (can be expanded as needed)
    private static final Map<Pair, Utils.Rarities> itemRarityMap = new HashMap<>();

    static public void init() {
        Pair Amphora_Sherd = new Pair("Brick", -1.0F);
        itemRarityMap.put(Amphora_Sherd, Utils.Rarities.UNIQUE);

        Pair Bleached_Branch = new Pair("Stick", -1.0F);
        itemRarityMap.put(Bleached_Branch, Utils.Rarities.UNIQUE);

        Pair Discarded_Scrap = new Pair("Paper", 18.0F);
        itemRarityMap.put(Discarded_Scrap, Utils.Rarities.UNIQUE);

        Pair Fossilized_Starfish = new Pair("Paper", 85.0F);
        itemRarityMap.put(Fossilized_Starfish, Utils.Rarities.UNIQUE);

        Pair Fallen_Blade = new Pair("Flint", -1.0F);
        itemRarityMap.put(Fallen_Blade, Utils.Rarities.UNIQUE);

        Pair Fallen_Sand = new Pair("Red Sand", -1.0F);
        itemRarityMap.put(Fallen_Sand, Utils.Rarities.UNIQUE);

        Pair Lightless_Blossom = new Pair("Pink Tulip", -1.0F);
        itemRarityMap.put(Lightless_Blossom, Utils.Rarities.UNIQUE);

        Pair Matte_Bead = new Pair("Clay Ball", -1.0F);
        itemRarityMap.put(Matte_Bead, Utils.Rarities.UNIQUE);

        Pair Metal_Swarf = new Pair("Iron Nugget", -1.0F);
        itemRarityMap.put(Metal_Swarf, Utils.Rarities.UNIQUE);

        Pair Mosaic_Tile = new Pair("White Glazed Terracotta", -1.0F);
        itemRarityMap.put(Mosaic_Tile, Utils.Rarities.UNIQUE);

        Pair Shriveled_Voidgloom = new Pair("Chorus Plant", -1.0F);
        itemRarityMap.put(Shriveled_Voidgloom, Utils.Rarities.UNIQUE);

        Pair Void_Slime = new Pair("Slimeball", -1.0F);
        itemRarityMap.put(Void_Slime, Utils.Rarities.UNIQUE);

        Pair Voidwarped_Root = new Pair("Chorus Fruit", -1.0F);
        itemRarityMap.put(Voidwarped_Root, Utils.Rarities.UNIQUE);

        Pair Wind_shorn_Stone = new Pair("Stone Button", -1.0F);
        itemRarityMap.put(Wind_shorn_Stone, Utils.Rarities.UNIQUE);

        Pair Abandoned_Pot = new Pair("Flower Pot", -1.0F);
        itemRarityMap.put(Abandoned_Pot, Utils.Rarities.RARE);

        Pair Almanac_Page = new Pair("Paper", -1.0F);
        itemRarityMap.put(Almanac_Page, Utils.Rarities.RARE);

        Pair Arable_Chunk = new Pair("Grass Block", -1.0F);
        itemRarityMap.put(Arable_Chunk, Utils.Rarities.RARE);

        Pair Bottle_of_Yogurt = new Pair("Glass Bottle", -1.0F);
        itemRarityMap.put(Bottle_of_Yogurt, Utils.Rarities.RARE);

        Pair Due_Delivery = new Pair("Piston", -1.0F);
        itemRarityMap.put(Due_Delivery, Utils.Rarities.RARE);

        Pair Elestial_Voidstone = new Pair("Paper", 73.0F);
        itemRarityMap.put(Elestial_Voidstone, Utils.Rarities.RARE);

        Pair Frying_Pan = new Pair("Diamond Shovel", -1.0F);
        itemRarityMap.put(Frying_Pan, Utils.Rarities.RARE);

        Pair Hobby_Horse = new Pair("Iron Horse Armor", 542.0F);
        itemRarityMap.put(Hobby_Horse, Utils.Rarities.RARE);

        Pair Lone_Component = new Pair("Brewing Stand", -1.0F);
        itemRarityMap.put(Lone_Component, Utils.Rarities.RARE);

        Pair Metal_Plate = new Pair("Iron Trapdoor", -1.0F);
        itemRarityMap.put(Metal_Plate, Utils.Rarities.RARE);

        Pair Missing_Coinage = new Pair("Paper", 68.0F);
        itemRarityMap.put(Missing_Coinage, Utils.Rarities.RARE);

        Pair Packaged_Brownie = new Pair("Nether Brick", -1.0F);
        itemRarityMap.put(Packaged_Brownie, Utils.Rarities.RARE);

        Pair Steel_Rod = new Pair("End Rod", -1.0F);
        itemRarityMap.put(Steel_Rod, Utils.Rarities.RARE);

        Pair Void_Carapace = new Pair("Shulker Shell", -1.0F);
        itemRarityMap.put(Void_Carapace, Utils.Rarities.RARE);

        Pair Black_Prism = new Pair("Black Stained Glass", -1.0F);
        itemRarityMap.put(Black_Prism, Utils.Rarities.LEGENDARY);

        Pair History_Textbook = new Pair("Daylight Detector", -1.0F);
        itemRarityMap.put(History_Textbook, Utils.Rarities.LEGENDARY);

        Pair Large_Metal_Chunk = new Pair("Block of Iron", -1.0F);
        itemRarityMap.put(Large_Metal_Chunk, Utils.Rarities.LEGENDARY);

        Pair Luxury_Timepiece = new Pair("Clock", -1.0F);
        itemRarityMap.put(Luxury_Timepiece, Utils.Rarities.LEGENDARY);

        Pair Miracle_Leftovers = new Pair("Mushroom Stew", -1.0F);
        itemRarityMap.put(Miracle_Leftovers, Utils.Rarities.LEGENDARY);

        Pair Precious_Mineral = new Pair("Paper", 59.0F);
        itemRarityMap.put(Precious_Mineral, Utils.Rarities.LEGENDARY);

        Pair Small_Ruby = new Pair("Paper", 72.0F);
        itemRarityMap.put(Small_Ruby, Utils.Rarities.LEGENDARY);

        Pair Golem_Capacitor = new Pair("Beacon", -1.0F);
        itemRarityMap.put(Golem_Capacitor, Utils.Rarities.FABLED);

        Pair Relic_of_the_Shattering = new Pair("Totem of Undying", -1.0F);
        itemRarityMap.put(Relic_of_the_Shattering, Utils.Rarities.FABLED);

        Pair Tangible_Intangibility = new Pair("Purple Glazed Terracotta", -1.0F);
        itemRarityMap.put(Tangible_Intangibility, Utils.Rarities.FABLED);

        Pair Archaic_Writing = new Pair("Default", -1.0F);
        itemRarityMap.put(Archaic_Writing, Utils.Rarities.MYTHIC);


        //why are they differnet when they spawn in that when i pick them up and drop them :cry::cry:
        // diamond axes with durabilty instead of custom model data
        Pair Discarded_Scrap2 = new Pair("Diamond Axe", 96.0F);
        itemRarityMap.put(Discarded_Scrap2, Utils.Rarities.UNIQUE);

        Pair Fossilized_Starfish2 = new Pair("Diamond Axe", 28.0F);
        itemRarityMap.put(Fossilized_Starfish2, Utils.Rarities.UNIQUE);

        Pair Almanac_Page2 = new Pair("Diamond Axe", 0F);
        itemRarityMap.put(Almanac_Page2, Utils.Rarities.RARE);

        Pair Elestial_Voidstone2 = new Pair("Diamond Axe", 66.0F);
        itemRarityMap.put(Elestial_Voidstone2, Utils.Rarities.RARE);

        Pair Missing_Coinage2 = new Pair("Diamond Axe", 61.0F);
        itemRarityMap.put(Missing_Coinage2, Utils.Rarities.RARE);

        Pair Small_Ruby2 = new Pair("Diamond Axe", 65.0F);
        itemRarityMap.put(Small_Ruby2, Utils.Rarities.LEGENDARY);

        Pair Precious_Mineral2 = new Pair("Diamond Axe", 53.0F);
        itemRarityMap.put(Precious_Mineral2, Utils.Rarities.LEGENDARY);
    }

    // Function to get rarity based on vanilla name and optional custom_model_data
    public static Utils.Rarities getRarity(String name, float model) {

        if (Objects.equals(name, "Iron Nugget")){
            return Config.getMetal_Swarf_Color();
        }

        Pair item = new Pair(name, model);
        if (itemRarityMap.containsKey(item)) {
            return itemRarityMap.get(item);
        }
        return Utils.Rarities.UNKNOWN;
    }
    public static Utils.Rarities getRarity(ItemEntity item) {
        String name = item.getStack().getName().getString();
        CustomModelDataComponent modelData = item.getStack().get(DataComponentTypes.CUSTOM_MODEL_DATA);
        float ID;
        if (modelData != null) {
            ID = modelData.floats().getFirst();
        } else ID = -1;
        if (Objects.equals(name, "Diamond Axe")) {
            ID = item.getStack().getDamage();
        }
        return getRarity(name, ID);
    }

    public static Utils.Rarities getRarity(String name) {
        Pair item = new Pair(name, -1.0F);
        if (itemRarityMap.containsKey(item)) {
            return itemRarityMap.get(item);
        }
        return Utils.Rarities.UNKNOWN;
    }

    public static Color getColor(Utils.Rarities rarity) {
        return switch (rarity) {
            case COMMON -> Color.WHITE;
            case UNIQUE -> new Color(0xF2F250);
            case RARE -> new Color(0xF652F7);
            case SET -> new Color(0x89EF57);
            case LEGENDARY -> new Color(0x75DDE1);
            case FABLED -> Color.RED;
            case MYTHIC -> new Color(0xA116A1);
            default -> new Color(0x00710e);
        };
    }
}
