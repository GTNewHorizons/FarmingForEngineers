package com.guigs44.farmingforengineers.container;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import com.guigs44.farmingforengineers.network.MessageMarketSelect;
import com.guigs44.farmingforengineers.network.NetworkHandler;
import com.guigs44.farmingforengineers.registry.MarketEntry;

public class ContainerMarketClient extends ContainerMarket {

    private final List<MarketEntry> itemList = Lists.newArrayList();
    private final List<MarketEntry> filteredItems = Lists.newArrayList();

    private final Comparator<MarketEntry> comparator = Comparator.comparingInt(o -> o.getType().ordinal());

    private String currentSearch;
    private MarketEntry.EntryType currentFilter;
    private boolean isDirty;
    private int scrollOffset;

    public ContainerMarketClient(EntityPlayer player, int posX, int posY, int posZ) {
        super(player, posX, posY, posZ);
    }

    @Override
    public ItemStack slotClick(int slotNumber, int dragType, int clickType, EntityPlayer player) {
        if (slotNumber >= 0 && slotNumber < inventorySlots.size()) {
            Slot slot = (Slot) inventorySlots.get(slotNumber);
            if (player.worldObj.isRemote) {
                if (slot instanceof FakeSlotMarket) {
                    FakeSlotMarket slotMarket = (FakeSlotMarket) slot;
                    MarketEntry entry = slotMarket.getEntry();
                    if (entry != null) {
                        selectedEntry = entry;
                        NetworkHandler.instance.sendToServer(new MessageMarketSelect(entry.getOutputItem()));
                    }
                }
            }
        }
        return super.slotClick(slotNumber, dragType, clickType, player);
    }

    public void search(@Nullable String term) {
        this.currentSearch = term;
        applyFilters();
    }

    public void setFilterType(@Nullable MarketEntry.EntryType filterType) {
        this.currentFilter = filterType;
        applyFilters();
    }

    public void applyFilters() {
        this.scrollOffset = 0;
        filteredItems.clear();
        boolean hasSearchFilter = currentSearch != null && !currentSearch.trim().isEmpty();
        if (currentFilter == null && !hasSearchFilter) {
            filteredItems.addAll(itemList);
        } else {
            for (MarketEntry entry : itemList) {
                if (hasSearchFilter && !entry.getOutputItem().getDisplayName().toLowerCase()
                        .contains(currentSearch.toLowerCase())) {
                    continue;
                }
                if (currentFilter != null && !currentFilter.passes(entry)) {
                    continue;
                }
                filteredItems.add(entry);
            }
        }

        filteredItems.sort(comparator);
    }

    public int getFilteredListCount() {
        return filteredItems.size();
    }

    public void setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
        populateMarketSlots();
    }

    public void populateMarketSlots() {
        int i = scrollOffset * 3;
        for (FakeSlotMarket slot : marketSlots) {
            if (i < filteredItems.size()) {
                slot.setEntry(filteredItems.get(i));
                i++;
            } else {
                slot.setEntry(null);
            }
        }
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

    public void setEntryList(Collection<MarketEntry> entryList) {
        this.itemList.clear();
        this.itemList.addAll(entryList);

        // Re-apply the filters to populate filteredItems
        applyFilters();

        // Updates the items inside the entry slots
        populateMarketSlots();

        setDirty(true);
    }

    @Nullable
    public MarketEntry.EntryType getCurrentFilter() {
        return currentFilter;
    }
}
