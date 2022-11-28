package com.guigs44.farmingforengineers.compat;

import buildcraft.api.core.EnumColor;
import com.guigs44.farmingforengineers.registry.MarketEntry;
import com.guigs44.farmingforengineers.registry.MarketRegistry;
import com.guigs44.farmingforengineers.registry.MarketRegistryDefaultHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class VanillaAddon {

    private static final String[] ANIMALS =
            new String[] {"Pig", "Sheep", "Cow", "Chicken", "EntityHorse", "Ocelot", "Rabbit", "PolarBear", "Wolf"};

    public VanillaAddon() {
        MarketRegistry.registerDefaultHandler("Vanilla Seeds", new MarketRegistryDefaultHandler() {
            @Override
            public void apply(MarketRegistry registry, ItemStack defaultPayment) {
                registry.registerEntry(new ItemStack(Items.wheat_seeds), defaultPayment, MarketEntry.EntryType.SEEDS);
                registry.registerEntry(new ItemStack(Items.melon_seeds), defaultPayment, MarketEntry.EntryType.SEEDS);
                registry.registerEntry(new ItemStack(Items.pumpkin_seeds), defaultPayment, MarketEntry.EntryType.SEEDS);
            }

            @Override
            public boolean isEnabledByDefault() {
                return true;
            }

            @Override
            public ItemStack getDefaultPayment() {
                return new ItemStack(Items.emerald);
            }
        });

        MarketRegistry.registerDefaultHandler("Vanilla Saplings", new MarketRegistryDefaultHandler() {
            @Override
            public void apply(MarketRegistry registry, ItemStack defaultPayment) {
                //				for (EnumWoodType type : ) {
                //					registry.registerEntry(new ItemStack(Blocks.sapling, 1, type.ordinal()), defaultPayment,
                // MarketEntry.EntryType.SAPLINGS);
                //				}
            }

            @Override
            public boolean isEnabledByDefault() {
                return true;
            }

            @Override
            public ItemStack getDefaultPayment() {
                return new ItemStack(Items.emerald);
            }
        });

        MarketRegistry.registerDefaultHandler("Bonemeal", new MarketRegistryDefaultHandler() {
            @Override
            public void apply(MarketRegistry registry, ItemStack defaultPayment) {
                registry.registerEntry(
                        new ItemStack(Items.dye, 1, EnumColor.WHITE.ordinal()),
                        defaultPayment,
                        MarketEntry.EntryType.OTHER);
            }

            @Override
            public boolean isEnabledByDefault() {
                return true;
            }

            @Override
            public ItemStack getDefaultPayment() {
                return new ItemStack(Items.emerald);
            }
        });

        MarketRegistry.registerDefaultHandler("Animal Eggs", new MarketRegistryDefaultHandler() {
            @Override
            public void apply(MarketRegistry registry, ItemStack defaultPayment) {
                for (String animalName : ANIMALS) {
                    ItemStack eggStack = new ItemStack(Items.spawn_egg);

                    // \o/ Praise SideOnly \o/
                    NBTTagCompound tagCompound = eggStack.getTagCompound();
                    if (tagCompound == null) {
                        tagCompound = new NBTTagCompound();
                    }
                    NBTTagCompound entityTag = new NBTTagCompound();
                    entityTag.setString("id", animalName);
                    tagCompound.setTag("EntityTag", entityTag);
                    eggStack.setTagCompound(tagCompound);

                    registry.registerEntry(eggStack, defaultPayment, MarketEntry.EntryType.OTHER);
                }
            }

            @Override
            public boolean isEnabledByDefault() {
                return false;
            }

            @Override
            public ItemStack getDefaultPayment() {
                return new ItemStack(Items.emerald);
            }
        });
    }
}
