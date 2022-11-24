package com.guigs44.farmingforengineers.network;

import com.guigs44.farmingforengineers.container.ContainerMarketClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.inventory.Container;

import javax.annotation.Nullable;

public class HandlerMarketList implements IMessageHandler<MessageMarketList, IMessage> {

	@Override
	@Nullable
	public IMessage onMessage(final MessageMarketList message, MessageContext ctx) {
        Container container = FMLClientHandler.instance().getClientPlayerEntity().openContainer;
        if(container instanceof ContainerMarketClient) {
            ((ContainerMarketClient) container).setEntryList(message.getEntryList());
        }
		return null;
	}

}
