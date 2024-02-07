package com.guigs44.farmingforengineers;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.guigs44.farmingforengineers.block.ModBlocks;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init() {
        GameRegistry.addRecipe(
                new ItemStack(ModBlocks.market),
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
