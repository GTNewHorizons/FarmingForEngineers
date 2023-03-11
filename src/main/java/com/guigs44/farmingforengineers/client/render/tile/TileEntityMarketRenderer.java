package com.guigs44.farmingforengineers.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

public class TileEntityMarketRenderer extends TileEntitySpecialRenderer {

    private static final ResourceLocation texture = new ResourceLocation(
            "farmingforengineers",
            "textures/blocks/texture.png");
    private static final ResourceLocation marketModel = new ResourceLocation(
            "farmingforengineers",
            "models/block/market.obj");
    private static final IModelCustom model = AdvancedModelLoader.loadModel(marketModel);

    @Override
    public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float partialTick) {
        bindTexture(texture);

        GL11.glPushMatrix();
        GL11.glTranslated(posX + 0.5, posY + 0.5, posZ + 0.5);
        model.renderAll();
        GL11.glPopMatrix();
    }
}
