package net.vpixelstudio.knightsnmages.common.init;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.vpixelstudio.knightsnmages.KnightsnmagesMod;

public class ModItemsInit {


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(KnightsnmagesMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        KnightsnmagesMod.LOGGER.info("Registering Mod Items for " + KnightsnmagesMod.MOD_ID);
    }
}
