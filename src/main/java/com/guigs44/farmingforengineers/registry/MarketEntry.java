package com.guigs44.farmingforengineers.registry;

import com.guigs44.farmingforengineers.FarmingForEngineers;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MarketEntry {

    public enum EntryType {
        SEEDS("gui.farmingforengineers:market.tooltip_seeds"),
        SAPLINGS("gui.farmingforengineers:market.tooltip_saplings"),
        OTHER("gui.farmingforengineers:market.tooltip_other");

        private static final ResourceLocation TEXTURE =
                new ResourceLocation(FarmingForEngineers.MOD_ID, "textures/gui/market.png");
        private static final EntryType[] values = values();
        private String tooltip;

        EntryType(String tooltip) {
            this.tooltip = tooltip;
        }

        public String getTooltip() {
            return tooltip;
        }

        public ResourceLocation getIconTexture() {
            return TEXTURE;
        }

        public int getIconTextureX() {
            return 196 + ordinal() * 20;
        }

        public int getIconTextureY() {
            return 14;
        }

        public boolean passes(MarketEntry entry) {
            return entry.getType() == this;
        }

        public static EntryType fromId(int id) {
            return values[id];
        }
    }

    private final ItemStack outputItem;
    private final ItemStack costItem;
    private final EntryType type;

    public MarketEntry(ItemStack outputItem, ItemStack costItem, EntryType type) {
        this.outputItem = outputItem;
        this.costItem = costItem;
        this.type = type;
    }

    public ItemStack getCostItem() {
        return costItem;
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public EntryType getType() {
        return type;
    }
}
