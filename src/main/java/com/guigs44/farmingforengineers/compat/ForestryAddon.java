package com.guigs44.farmingforengineers.compat;

import com.guigs44.farmingforengineers.registry.MarketEntry;
import com.guigs44.farmingforengineers.registry.MarketRegistry;
import com.guigs44.farmingforengineers.registry.MarketRegistryDefaultHandler;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.TreeManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ForestryAddon {

    private static final String KEY_SAPLINGS = "Forestry Saplings";

    public ForestryAddon() {
        MarketRegistry.registerDefaultHandler(KEY_SAPLINGS, new MarketRegistryDefaultHandler() {
            @Override
            public void apply(MarketRegistry registry, ItemStack defaultPayment) {
                for (ITree tree : TreeManager.treeRoot.getIndividualTemplates()) {
                    ItemStack saplingStack = TreeManager.treeRoot.getMemberStack(tree, 0); // 0 == sapling I guess
                    registry.registerEntry(saplingStack, defaultPayment, MarketEntry.EntryType.SAPLINGS);
                }
            }

            @Override
            public boolean isEnabledByDefault() {
                return true;
            }

            @Override
            public ItemStack getDefaultPayment() {
                return new ItemStack(Items.emerald, 2);
            }
        });
    }
}
