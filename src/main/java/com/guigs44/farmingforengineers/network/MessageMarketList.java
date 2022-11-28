package com.guigs44.farmingforengineers.network;

import com.google.common.collect.Lists;
import com.guigs44.farmingforengineers.registry.MarketEntry;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import net.minecraft.item.ItemStack;

public class MessageMarketList implements IMessage {

    private Collection<MarketEntry> entryList;

    public MessageMarketList() {}

    public MessageMarketList(Collection<MarketEntry> entryList) {
        this.entryList = entryList;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int entryCount = buf.readInt();
        entryList = Lists.newArrayListWithCapacity(entryCount);
        for (int i = 0; i < entryCount; i++) {
            entryList.add(readEntry(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        int count = entryList.size();
        buf.writeInt(count);
        for (MarketEntry recipe : entryList) {
            writeEntry(recipe, buf);
        }
    }

    public Collection<MarketEntry> getEntryList() {
        return entryList;
    }

    private MarketEntry readEntry(ByteBuf buf) {
        ItemStack outputItem = ByteBufUtils.readItemStack(buf);
        ItemStack costItem = ByteBufUtils.readItemStack(buf);
        MarketEntry.EntryType type = MarketEntry.EntryType.fromId(buf.readByte());
        return new MarketEntry(outputItem, costItem, type);
    }

    private void writeEntry(MarketEntry entry, ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, entry.getOutputItem());
        ByteBufUtils.writeItemStack(buf, entry.getCostItem());
        buf.writeByte(entry.getType().ordinal());
    }
}
