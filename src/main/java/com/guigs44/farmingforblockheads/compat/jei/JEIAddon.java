package com.guigs44.farmingforblockheads.compat.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import com.guigs44.farmingforblockheads.block.ModBlocks;
import com.guigs44.farmingforblockheads.client.gui.GuiMarket;
import com.guigs44.farmingforblockheads.registry.MarketEntry;
import com.guigs44.farmingforblockheads.registry.MarketRegistry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

@JEIPlugin
public class JEIAddon extends BlankModPlugin {

	@Override
	public void register(IModRegistry registry) {
		registry.addRecipeCategories(new MarketCategory(registry.getJeiHelpers().getGuiHelper()));
		registry.addRecipeHandlers(new MarketRecipeHandler());
		List<MarketRecipe> marketRecipes = Lists.newArrayList();
		for(MarketEntry entry : MarketRegistry.getEntries()) {
			marketRecipes.add(new MarketRecipe(entry));
		}
		registry.addRecipes(marketRecipes);

		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.market), MarketCategory.UID);

		registry.addAdvancedGuiHandlers(new IAdvancedGuiHandler<GuiMarket>() {
			@Override
			public Class<GuiMarket> getGuiContainerClass() {
				return GuiMarket.class;
			}

			@Nullable
			@Override
			public List<Rectangle> getGuiExtraAreas(GuiMarket guiContainer) {
				List<Rectangle> list = Lists.newArrayList();
				for(GuiButton button : guiContainer.getFilterButtons()) {
					list.add(new Rectangle(button.xPosition, button.yPosition, button.width, button.height));
				}
				return list;
			}

			@Nullable
			@Override
			public Object getIngredientUnderMouse(GuiMarket guiContainer, int mouseX, int mouseY) {
				return null;
			}
		});
	}

}
