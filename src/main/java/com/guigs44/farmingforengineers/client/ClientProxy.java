package com.guigs44.farmingforengineers.client;

import com.guigs44.farmingforengineers.CommonProxy;
import com.guigs44.farmingforengineers.client.render.block.MarketBlockRenderer;
import com.guigs44.farmingforengineers.client.render.tile.TileEntityMarketRenderer;
import com.guigs44.farmingforengineers.tile.TileMarket;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        //        RenderingRegistry.registerEntityRenderingHandler(EntityMerchant.class, new
        // IRenderFactory<EntityMerchant>()
        //         {
        //             @Override
        //             public Render<? super EntityMerchant> createRenderFor(RenderManager manager) {
        //                 return new RenderMerchant(manager);
        //             }
        //        });

        RenderingRegistry.registerBlockHandler(MarketBlockRenderer.RENDER_ID, new MarketBlockRenderer());
        //noinspection ConstantConditions
        // ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.market), 0, new
        // ModelResourceLocation(ModBlocks.market.getRegistryName(), "inventory"));
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ClientRegistry.bindTileEntitySpecialRenderer(TileMarket.class, new TileEntityMarketRenderer());

        RenderingRegistry.registerBlockHandler(MarketBlockRenderer.RENDER_ID, new MarketBlockRenderer());
    }
}
