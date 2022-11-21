package com.guigs44.farmingforengineers.compat;

import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.TreeManager;
import com.guigs44.farmingforengineers.registry.MarketEntry;
import com.guigs44.farmingforengineers.registry.MarketRegistry;
import com.guigs44.farmingforengineers.registry.MarketRegistryDefaultHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ForestryAddon {

	private static final String KEY_SAPLINGS = "Forestry Saplings";

	public ForestryAddon() {
		MarketRegistry.registerDefaultHandler(KEY_SAPLINGS, new MarketRegistryDefaultHandler() {
			@Override
			public void apply(MarketRegistry registry, ItemStack defaultPayment) {
				for(ITree tree : TreeManager.treeRoot.getIndividualTemplates()) {
					ItemStack saplingStack = TreeManager.treeRoot.getMemberStack(tree, EnumGermlingType.SAPLING);
					registry.registerEntry(saplingStack, defaultPayment, MarketEntry.EntryType.SAPLINGS);
				}
			}

			@Override
			public boolean isEnabledByDefault() {
				return false;
			}

			@Override
			public ItemStack getDefaultPayment() {
				return new ItemStack(Items.EMERALD, 2);
			}
		});
	}

}
