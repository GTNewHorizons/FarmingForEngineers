package com.guigs44.farmingforblockheads.registry;

import net.minecraft.item.ItemStack;

public interface MarketRegistryDefaultHandler {
	void apply(MarketRegistry registry, ItemStack defaultPayment);
	boolean isEnabledByDefault();
	ItemStack getDefaultPayment();
}
