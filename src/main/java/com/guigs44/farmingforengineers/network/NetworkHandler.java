package com.guigs44.farmingforengineers.network;

import com.guigs44.farmingforengineers.FarmingForEngineers;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

public class NetworkHandler {
    public static final SimpleNetworkWrapper instance =
            NetworkRegistry.INSTANCE.newSimpleChannel(FarmingForEngineers.MOD_ID);

    public static void init() {
        instance.registerMessage(HandlerMarketList.class, MessageMarketList.class, 0, Side.CLIENT);
        instance.registerMessage(HandlerMarketSelect.class, MessageMarketSelect.class, 1, Side.SERVER);
    }

    //	public static Minecraft getThreadListener(MessageContext ctx) {
    //		return ctx.side == Side.SERVER ? ctx.getServerHandler().playerEntity.worldObj : getClientThreadListener();
    //	}

    @SideOnly(Side.CLIENT)
    public static Minecraft getClientThreadListener() {
        return Minecraft.getMinecraft();
    }
}
