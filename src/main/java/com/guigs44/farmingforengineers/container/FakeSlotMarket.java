package com.guigs44.farmingforengineers.container;

import com.guigs44.farmingforengineers.registry.MarketEntry;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;

public class FakeSlotMarket extends FakeSlot {

    private MarketEntry entry;

    public FakeSlotMarket(int slotId, int x, int y) {
        super(slotId, x, y);
    }

    @Override
    public ItemStack getStack() {
        return entry != null ? entry.getOutputItem() : null;
    }

    @Override
    public boolean getHasStack() {
        return entry != null;
    }

    public void setEntry(@Nullable MarketEntry entry) {
        this.entry = entry;
    }

    @Nullable
    public MarketEntry getEntry() {
        return entry;
    }
}
