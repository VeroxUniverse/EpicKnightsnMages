package net.vpixelstudio.knightsnmages.common.util;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.vpixelstudio.knightsnmages.KnightsnmagesMod;
import net.vpixelstudio.knightsnmages.common.init.ModBlocksInit;

public class ModItemGroups {
    public static final ItemGroup MOD_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(KnightsnmagesMod.MOD_ID, "mod_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.mod_group"))
                    .icon(() -> new ItemStack(Items.IRON_AXE)).entries((displayContext, entries) -> {

                        //entries.add(ModItemsInit.ITEM);
                        //entries.add(ModBlocksInit.BLOCK);

                        entries.add(ModBlocksInit.ALTAR_BLOCK);
                        entries.add(ModBlocksInit.PEDESTAL_BLOCK);

                    }).build());


    public static void registerItemGroups() {
        KnightsnmagesMod.LOGGER.info("Registering Item Groups for " + KnightsnmagesMod.MOD_ID);
    }
}
