package com.guigs44.farmingforengineers.client.gui;

import java.util.Collection;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.guigs44.farmingforengineers.container.ContainerMarketClient;
import com.guigs44.farmingforengineers.container.FakeSlotMarket;
import com.guigs44.farmingforengineers.container.SlotMarketBuy;
import com.guigs44.farmingforengineers.registry.MarketEntry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

// @MouseTweaksIgnore
public class GuiMarket extends GuiContainer {

    private static final int SCROLLBAR_COLOR = 0xFFAAAAAA;
    private static final int SCROLLBAR_Y = 8;
    private static final int SCROLLBAR_WIDTH = 7;
    private static final int SCROLLBAR_HEIGHT = 77;
    private static final int VISIBLE_ROWS = 4;

    private static final ResourceLocation TEXTURE = new ResourceLocation("farmingforengineers:textures/gui/market.png");

    private final ContainerMarketClient container;
    private final List<GuiButtonMarketFilter> filterButtons = Lists.newArrayList();

    private boolean isEventHandler;
    private int scrollBarScaledHeight;
    private int scrollBarXPos;
    private int scrollBarYPos;
    private int currentOffset;

    private int mouseClickY = -1;
    private int indexWhenClicked;
    private int lastNumberOfMoves;
    private Slot hoverSlot;

    private GuiTextField searchBar;

    public GuiMarket(ContainerMarketClient container) {
        super(container);
        this.container = container;
    }

    @Override
    public void initGui() {
        ySize = 174;
        super.initGui();

        searchBar = new GuiTextField(fontRendererObj, guiLeft + xSize - 78, guiTop - 5, 70, 10);

        int id = 1;
        int curY = -80;
        for (MarketEntry.EntryType type : MarketEntry.EntryType.values()) {
            GuiButtonMarketFilter filterButton = new GuiButtonMarketFilter(
                    id++,
                    width / 2 + 87,
                    height / 2 + curY,
                    container,
                    type);
            buttonList.add(filterButton);
            filterButtons.add(filterButton);

            curY += 20;
        }

        if (!isEventHandler) {
            MinecraftForge.EVENT_BUS.register(this);
            isEventHandler = true;
        }

        recalculateScrollBar();
    }

    @Override
    protected void actionPerformed(GuiButton button) { // TODO: handle exceptions
        if (button instanceof GuiButtonMarketFilter) {
            if (container.getCurrentFilter() == ((GuiButtonMarketFilter) button).getFilterType()) {
                container.setFilterType(null);
            } else {
                container.setFilterType(((GuiButtonMarketFilter) button).getFilterType());
            }
            container.populateMarketSlots();
            setCurrentOffset(currentOffset);
        }
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int delta = Mouse.getEventDWheel();
        if (delta == 0) {
            return;
        }
        setCurrentOffset(delta > 0 ? currentOffset - 1 : currentOffset + 1);
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int state) {
        super.mouseMovedOrUp(mouseX, mouseY, state);
        if (state != -1 && mouseClickY != -1) {
            mouseClickY = -1;
            indexWhenClicked = 0;
            lastNumberOfMoves = 0;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) { // TODO: handle exceptions
        super.mouseClicked(mouseX, mouseY, button);
        if (button == 1 && mouseX >= searchBar.xPosition
                && mouseX < searchBar.xPosition + searchBar.width
                && mouseY >= searchBar.yPosition
                && mouseY < searchBar.yPosition + searchBar.height) {
            searchBar.setText("");
            container.search(null);
            container.populateMarketSlots();
            setCurrentOffset(currentOffset);
        } else {
            searchBar.mouseClicked(mouseX, mouseY, button);
        }
        if (mouseX >= scrollBarXPos && mouseX <= scrollBarXPos + SCROLLBAR_WIDTH
                && mouseY >= scrollBarYPos
                && mouseY <= scrollBarYPos + scrollBarScaledHeight) {
            mouseClickY = mouseY;
            indexWhenClicked = currentOffset;
        }
    }

    @Override
    protected void keyTyped(char c, int keyCode) {
        if (searchBar.textboxKeyTyped(c, keyCode)) {
            container.search(searchBar.getText());
            container.populateMarketSlots();
            setCurrentOffset(currentOffset);
        } else {
            super.keyTyped(c, keyCode);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (GuiButton sortButton : filterButtons) {
            if (sortButton.func_146115_a() && sortButton.enabled) { // func_146115_a == isMouseOverSlot
                drawHoveringText(
                        ((GuiButtonMarketFilter) sortButton).getTooltipLines(),
                        mouseX,
                        mouseY,
                        Minecraft.getMinecraft().fontRenderer);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        if (container.isDirty()) {
            recalculateScrollBar();
            container.setDirty(false);
        }
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop - 10, 0, 0, xSize, ySize + 10);
        if (container.getSelectedEntry() != null && !container.isReadyToBuy()) {
            drawTexturedModalRect(guiLeft + 43, guiTop + 40, 176, 0, 14, 14);
        }

        if (mouseClickY != -1) {
            float pixelsPerFilter = (SCROLLBAR_HEIGHT - scrollBarScaledHeight)
                    / (float) Math.max(1, (int) Math.ceil(container.getFilteredListCount() / 3f) - VISIBLE_ROWS);
            if (pixelsPerFilter != 0) {
                int numberOfFiltersMoved = (int) ((mouseY - mouseClickY) / pixelsPerFilter);
                if (numberOfFiltersMoved != lastNumberOfMoves) {
                    setCurrentOffset(indexWhenClicked + numberOfFiltersMoved);
                    lastNumberOfMoves = numberOfFiltersMoved;
                }
            }
        }

        fontRendererObj.drawString(
                I18n.format("container.farmingforengineers:market"),
                guiLeft + 10,
                guiTop + 10,
                0xFFFFFF,
                true);

        if (container.getSelectedEntry() == null) {
            drawCenteredString(
                    fontRendererObj,
                    I18n.format("gui.farmingforengineers:market.no_selection"),
                    guiLeft + 49,
                    guiTop + 65,
                    0xFFFFFF);
        } else {
            drawCenteredString(
                    fontRendererObj,
                    getFormattedCostStringShort(container.getSelectedEntry()),
                    guiLeft + 49,
                    guiTop + 65,
                    0xFFFFFF);
        }

        GuiContainer.drawRect(
                scrollBarXPos,
                scrollBarYPos,
                scrollBarXPos + SCROLLBAR_WIDTH,
                scrollBarYPos + scrollBarScaledHeight,
                SCROLLBAR_COLOR);
        GL11.glColor4f(1f, 1f, 1f, 1f);

        searchBar.drawTextBox();

        hoverSlot = getSlotAtPosition(mouseX, mouseY);
    }

    public Collection<GuiButtonMarketFilter> getFilterButtons() {
        return filterButtons;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (isEventHandler) {
            MinecraftForge.EVENT_BUS.unregister(this);
            isEventHandler = false;
        }
    }

    public void recalculateScrollBar() {
        int scrollBarTotalHeight = SCROLLBAR_HEIGHT - 1;
        this.scrollBarScaledHeight = (int) (scrollBarTotalHeight
                * Math.min(1f, ((float) VISIBLE_ROWS / (Math.ceil(container.getFilteredListCount() / 3f)))));
        this.scrollBarXPos = guiLeft + xSize - SCROLLBAR_WIDTH - 9;
        this.scrollBarYPos = guiTop + SCROLLBAR_Y
                + ((scrollBarTotalHeight - scrollBarScaledHeight) * currentOffset
                        / Math.max(1, (int) Math.ceil((container.getFilteredListCount() / 3f)) - VISIBLE_ROWS));
    }

    public void setCurrentOffset(int currentOffset) {
        this.currentOffset = Math
                .max(0, Math.min(currentOffset, (int) Math.ceil(container.getFilteredListCount() / 3f) - VISIBLE_ROWS));

        container.setScrollOffset(this.currentOffset);

        recalculateScrollBar();
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        // noinspection ConstantConditions
        if (hoverSlot != null && event.itemStack == hoverSlot.getStack()) {
            MarketEntry hoverEntry = null;

            if (hoverSlot instanceof FakeSlotMarket) {
                hoverEntry = ((FakeSlotMarket) hoverSlot).getEntry();
            } else if (hoverSlot instanceof SlotMarketBuy) {
                hoverEntry = container.getSelectedEntry();
            }

            if (hoverEntry != null) {
                event.toolTip.add(getFormattedCostString(hoverEntry));
            }
        }
    }

    private Slot getSlotAtPosition(int x, int y) {
        for (int k = 0; k < inventorySlots.inventorySlots.size(); ++k) {
            Slot slot = (Slot) inventorySlots.inventorySlots.get(k);

            if (isMouseOverSlot(slot, x, y)) {
                return slot;
            }
        }
        return null;
    }

    private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
        return func_146978_c(slotIn.xDisplayPosition, slotIn.yDisplayPosition, 16, 16, mouseX, mouseY);
    }

    private String getFormattedCostString(MarketEntry entry) {
        String color = "\u00a7a"; // green
        if (entry.getCostItem().getItem() == Items.diamond) {
            color = "\u00a7b"; // aqua
        }
        return color + I18n.format(
                "gui.farmingforengineers:market.tooltip_cost",
                I18n.format(
                        "gui.farmingforengineers:market.cost",
                        entry.getCostItem().stackSize,
                        entry.getCostItem().getDisplayName()));
    }

    private String getFormattedCostStringShort(MarketEntry entry) {
        String color = "\u00a7a"; // green
        if (entry.getCostItem().getItem() == Items.diamond) {
            color = "\u00a7b"; // aqua
        }
        return color + I18n.format(
                "gui.farmingforengineers:market.cost",
                entry.getCostItem().stackSize,
                entry.getCostItem().getDisplayName());
    }
}
