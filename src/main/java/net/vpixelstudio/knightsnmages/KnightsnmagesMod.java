package net.vpixelstudio.knightsnmages;

import net.fabricmc.api.ModInitializer;

import net.vpixelstudio.knightsnmages.common.init.ModBlocksInit;
import net.vpixelstudio.knightsnmages.common.init.ModItemsInit;
import net.vpixelstudio.knightsnmages.common.util.ModItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnightsnmagesMod implements ModInitializer {
	public static final String MOD_ID = "knightsnmages";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerItemGroups();

		ModItemsInit.registerModItems();
		ModBlocksInit.registerModBlocks();

		LOGGER.info("Epic Knights'n'Maged Loaded!");
	}
}