package com.guigs44.farmingforengineers.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityMarketRenderer extends TileEntitySpecialRenderer {
    private static final ResourceLocation textureRoof =
            new ResourceLocation("cookingforengineers", "textures/blocks/market_roof.png");
    private static final ResourceLocation textureTop =
            new ResourceLocation("cookingforengineers", "textures/blocks/market_top.png");

    @Override
    public void renderTileEntityAt(
            TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {}
}
