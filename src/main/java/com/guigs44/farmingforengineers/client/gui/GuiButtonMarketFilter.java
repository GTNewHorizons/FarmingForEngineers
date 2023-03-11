package com.guigs44.farmingforengineers.client.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.guigs44.farmingforengineers.container.ContainerMarketClient;
import com.guigs44.farmingforengineers.registry.MarketEntry;

public class GuiButtonMarketFilter extends GuiButton {

    private final ContainerMarketClient container;
    private final MarketEntry.EntryType filterType;
    private final List<String> tooltipLines = Lists.newArrayList();
    private boolean hovered;

    public GuiButtonMarketFilter(int buttonId, int x, int y, ContainerMarketClient container,
            MarketEntry.EntryType filterType) {
        super(buttonId, x, y, 20, 20, "");
        this.container = container;
        this.filterType = filterType;
        this.tooltipLines.add(I18n.format(this.filterType.getTooltip()));
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition
                && mouseX < this.xPosition + this.width
                && mouseY < this.yPosition + this.height;

        int texY = filterType.getIconTextureY();
        if (container.getCurrentFilter() != null && container.getCurrentFilter() != filterType) {
            texY += 40;
        } else if (hovered) {
            texY += 20;
        }
        GL11.glColor4f(1f, 1f, 1f, 1f); // maybe?
        // GlStateManager.color(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(filterType.getIconTexture());
        drawTexturedModalRect(xPosition, yPosition, filterType.getIconTextureX(), texY, width, height);
    }

    public List<String> getTooltipLines() {
        return tooltipLines;
    }

    public MarketEntry.EntryType getFilterType() {
        return filterType;
    }
}
