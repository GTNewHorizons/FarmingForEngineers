package com.guigs44.farmingforengineers;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init() {
        GameRegistry.addRecipe(
                new ItemStack(FarmingForEngineers.blockMarket),
                "PCP",
                "W W",
                "WWW",
                'C',
                new ItemStack(Blocks.wool, 1, 1),
                'P',
                Blocks.planks,
                'W',
                Blocks.log);
    }
}
