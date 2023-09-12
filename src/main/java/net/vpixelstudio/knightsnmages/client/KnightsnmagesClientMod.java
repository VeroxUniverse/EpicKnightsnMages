package net.vpixelstudio.knightsnmages.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.vpixelstudio.knightsnmages.common.init.ModBlocksInit;

@Environment(EnvType.CLIENT)
public class KnightsnmagesClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocksInit.ALTAR_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocksInit.PEDESTAL_BLOCK, RenderLayer.getCutout());
    }
}
