package com.guigs44.farmingforengineers;

import com.guigs44.farmingforengineers.item.ItemBlockMarket;
import com.guigs44.farmingforengineers.network.GuiHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerBlock(FarmingForEngineers.blockMarket, ItemBlockMarket.class, "market");
        NetworkRegistry.INSTANCE.registerGuiHandler(FarmingForEngineers.instance, new GuiHandler());
    }
}
