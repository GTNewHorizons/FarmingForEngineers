package com.guigs44.farmingforengineers;

import com.guigs44.farmingforengineers.block.ModBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRecipes {

    public static void init() {
        GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(ModBlocks.market),
                "PCP",
                "W W",
                "WWW",
                'C',
                new ItemStack(Blocks.wool, 1, 1),
                'P',
                "plankWood",
                'W',
                "logWood"));
    }
}
