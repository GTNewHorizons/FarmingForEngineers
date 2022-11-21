package com.guigs44.farmingforblockheads;

import com.guigs44.farmingforblockheads.block.ModBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRecipes {

	public static void init() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.market), "PCP", "W W", "WWW", 'C',
            new ItemStack(Blocks.wool, 1), 'P', "plankWood", 'W', "logWood")); //Will be replaced
	}

}
