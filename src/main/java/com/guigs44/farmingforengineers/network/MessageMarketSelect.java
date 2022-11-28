package com.guigs44.farmingforengineers.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class MessageMarketSelect implements IMessage {

    private ItemStack outputItem;

    public MessageMarketSelect() {}

    public MessageMarketSelect(ItemStack outputItem) {
        this.outputItem = outputItem;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        outputItem = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, outputItem);
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }
}
