package com.guigs44.farmingforblockheads.compat;

import com.guigs44.farmingforblockheads.registry.MarketEntry;
import com.guigs44.farmingforblockheads.registry.MarketRegistry;
import com.guigs44.farmingforblockheads.registry.MarketRegistryDefaultHandler;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class HarvestcraftAddon {

	private static final String KEY_SEEDS = "Pams Harvestcraft Seeds";
	private static final String KEY_SAPLINGS = "Pams Harvestcraft Saplings";
	private static final String[] SEEDS = new String[] {
			"blackberry", "blueberry", "candleberry", "raspberry", "strawberry", "cactusfruit", "asparagus", "barley", "oats", "rye", "corn", "bambooshoot", "cantaloupe", "cucumber", "wintersquash", "zucchini", "beet", "onion", "parsnip", "peanut", "radish", "rutabaga", "sweetpotato", "turnip", "rhubarb", "celery", "garlic", "ginger", "spiceleaf", "tealeaf", "coffeebean", "mustardseeds", "broccoli", "cauliflower", "leek", "lettuce", "scallion", "artichoke", "brusselsprout", "cabbage", "spinach", "whitemushroom", "bean", "soybean", "bellpepper", "chilipepper", "eggplant", "okra", "peas", "tomato", "cotton", "pineapple", "grape", "kiwi", "cranberry", "rice", "seaweed", "curryleaf", "sesameseeds", "waterchestnut",
	};

	private static final String[] SAPLINGS = new String[] {
			"apple", "almond", "apricot", "avocado", "banana", "cashew", "cherry", "chestnut", "coconut", "date", "dragonfruit", "durian", "fig", "gooseberry", "grapefruit", "lemon", "lime", "mango", "nutmeg", "olive", "orange", "papaya", "peach", "pear", "pecan", "peppercorn", "persimmon", "pistachio", "plum", "pomegranate", "starfruit", "vanillabean", "walnut", "cinnamon", "maple", "paperbark"
	};

	public HarvestcraftAddon() {
		MarketRegistry.registerDefaultHandler(KEY_SEEDS, new MarketRegistryDefaultHandler() {
			@Override
			public void apply(MarketRegistry registry, ItemStack defaultPayment) {
				for (String cropName : SEEDS) {
					String seedName = cropName + "seeditem";
					Item seedItem = Item.REGISTRY.getObject(new ResourceLocation(Compat.HARVESTCRAFT, seedName));
					if (seedItem != null) {
						registry.registerEntry(new ItemStack(seedItem), defaultPayment, MarketEntry.EntryType.SEEDS);
					}
				}
			}

			@Override
			public boolean isEnabledByDefault() {
				return true;
			}

			@Override
			public ItemStack getDefaultPayment() {
				return new ItemStack(Items.EMERALD);
			}
		});

		MarketRegistry.registerDefaultHandler(KEY_SAPLINGS, new MarketRegistryDefaultHandler() {
			@Override
			public void apply(MarketRegistry registry, ItemStack defaultPayment) {
				for (String treeName : SAPLINGS) {
					String saplingName = treeName + "_sapling";
					Item saplingItem = Item.REGISTRY.getObject(new ResourceLocation(Compat.HARVESTCRAFT, saplingName));
					if (saplingItem != null) {
						registry.registerEntry(new ItemStack(saplingItem), defaultPayment, MarketEntry.EntryType.SAPLINGS);
					}
				}
			}

			@Override
			public boolean isEnabledByDefault() {
				return true;
			}

			@Override
			public ItemStack getDefaultPayment() {
				return new ItemStack(Items.EMERALD);
			}
		});
	}

}
