package com.guigs44.farmingforengineers.client;

import com.guigs44.farmingforengineers.CommonProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        //		RenderingRegistry.registerEntityRenderingHandler(EntityMerchant.class, new IRenderFactory<EntityMerchant>()
        // {
        //			@Override
        //			public Render<? super EntityMerchant> createRenderFor(RenderManager manager) {
        //				return new RenderMerchant(manager);
        //			}
        //		});

        //noinspection ConstantConditions
        // ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.market), 0, new
        // ModelResourceLocation(ModBlocks.market.getRegistryName(), "inventory"));
    }
}
