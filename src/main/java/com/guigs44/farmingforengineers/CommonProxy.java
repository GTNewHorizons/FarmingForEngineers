package com.guigs44.farmingforengineers;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerBlock(FarmingForEngineers.blockMarket, "market");
    }
}
