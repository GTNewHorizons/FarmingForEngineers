package com.guigs44.farmingforengineers;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;

import com.guigs44.farmingforengineers.registry.AbstractRegistry;
import com.guigs44.farmingforengineers.registry.MarketRegistry;
import com.guigs44.farmingforengineers.utilities.ChatComponentBuilder;

public class CommandFarmingForEngineers extends CommandBase {

    @Override
    public String getCommandName() {
        return "farmingforengineers";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/farmingforengineers reload";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            throw new WrongUsageException(getCommandUsage(sender));
        }
        if (args[0].equals("reload")) {
            AbstractRegistry.registryErrors.clear();
            MarketRegistry.INSTANCE.load(FarmingForEngineers.configDir);
            sender.addChatMessage(ChatComponentBuilder.of("commands.farmingforengineers:reload.success").build());
            if (AbstractRegistry.registryErrors.size() > 0) {
                sender.addChatMessage(
                        ChatComponentBuilder.of("There were errors loading the Farming for Engineers registries:")
                                .setColor(EnumChatFormatting.RED).build());
                for (String error : AbstractRegistry.registryErrors) {
                    sender.addChatMessage(
                            ChatComponentBuilder.of("* " + error).setColor(EnumChatFormatting.WHITE).build());
                }
            }
        } else {
            throw new WrongUsageException(getCommandUsage(sender));
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        return getListOfStringsMatchingLastWord(args, "reload");
    }
}
