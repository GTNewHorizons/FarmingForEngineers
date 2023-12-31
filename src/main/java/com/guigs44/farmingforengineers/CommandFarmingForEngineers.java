package com.guigs44.farmingforengineers;

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

    // Replaced by processCommand()
    //
    // @Override
    // public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    // if(args.length == 0) {
    // throw new WrongUsageException(getCommandUsage(sender));
    // }
    // if(args[0].equals("reload")) {
    // AbstractRegistry.registryErrors.clear();
    // MarketRegistry.INSTANCE.load(FarmingForEngineers.configDir);
    // sender.addChatMessage(new TextComponentTranslation("commands.farmingforengineers:reload.success"));
    // if(AbstractRegistry.registryErrors.size() > 0) {
    // sender.addChatMessage(new TextComponentString(TextFormatting.RED + "There were errors loading the Farming for
    // Engineers registries:"));
    // TextFormatting lastFormatting = TextFormatting.WHITE;
    // for(String error : AbstractRegistry.registryErrors) {
    // sender.addChatMessage(new TextComponentString(lastFormatting + "* " + error));
    // lastFormatting = lastFormatting == TextFormatting.GRAY ? TextFormatting.WHITE : TextFormatting.GRAY;
    // }
    // }
    // } else {
    // throw new WrongUsageException(getCommandUsage(sender));
    // }
    // }

    // TODO: Implement/fix
    // @Override
    // public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
    // @Nullable BlockPos pos) {
    // return getListOfStringsMatchingLastWord(args, "reload");
    // }

}
