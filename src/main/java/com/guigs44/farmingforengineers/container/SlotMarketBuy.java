package com.guigs44.farmingforengineers.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.guigs44.farmingforengineers.registry.MarketEntry;

public class SlotMarketBuy extends Slot {

    private final ContainerMarket container;

    public SlotMarketBuy(ContainerMarket container, IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
        this.container = container;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return container.isReadyToBuy();
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return false;
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack) {
        MarketEntry entry = container.getSelectedEntry();
        if (entry == null) {
            return;
        }

        container.onItemBought();
    }
}
