package net.vpixelstudio.knightsnmages.common.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.vpixelstudio.knightsnmages.KnightsnmagesMod;
import net.vpixelstudio.knightsnmages.common.blocks.AltarBlock;
import net.vpixelstudio.knightsnmages.common.blocks.PedestalBlock;

public class ModBlocksInit {

    public static final Block ALTAR_BLOCK = registerBlock("altar_block",
            new AltarBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static final Block PEDESTAL_BLOCK = registerBlock("pedestal_block",
            new PedestalBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).sounds(BlockSoundGroup.STONE).nonOpaque()));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(KnightsnmagesMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(KnightsnmagesMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        KnightsnmagesMod.LOGGER.info("Registering ModBlocks for " + KnightsnmagesMod.MOD_ID);
    }
}