package com.guigs44.farmingforengineers.network;

import com.guigs44.farmingforengineers.container.ContainerMarket;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import javax.annotation.Nullable;
import net.minecraft.inventory.Container;

public class HandlerMarketSelect implements IMessageHandler<MessageMarketSelect, IMessage> {
    @Override
    @Nullable
    public IMessage onMessage(MessageMarketSelect message, MessageContext ctx) {
        Container container = ctx.getServerHandler().playerEntity.openContainer;
        if (container instanceof ContainerMarket) {
            ((ContainerMarket) container).selectMarketEntry(message.getOutputItem());
        }
        return null;
    }
}
