package com.guigs44.farmingforengineers.client.render;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.guigs44.farmingforengineers.FarmingForEngineers;

/**
 * A renderer for {@link com.guigs44.farmingforengineers.entity.EntityMerchant}. Just a re-skinned villager.
 */
public class RenderMerchant extends RenderLiving {

    private static final ResourceLocation MERCHANT_TEXTURE = new ResourceLocation(
            FarmingForEngineers.MOD_ID,
            "textures/entity/merchant.png");
    public static final float SHADOW_SIZE = 0.5f;

    public RenderMerchant() {
        super(new ModelVillager(SHADOW_SIZE), SHADOW_SIZE);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return MERCHANT_TEXTURE;
    }
}
