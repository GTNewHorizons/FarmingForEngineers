package com.guigs44.farmingforengineers;

import com.guigs44.farmingforengineers.registry.AbstractRegistry;
import com.guigs44.farmingforengineers.registry.MarketRegistry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.util.List;

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
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0) {
			throw new WrongUsageException(getCommandUsage(sender));
		}
		if(args[0].equals("reload")) {
			AbstractRegistry.registryErrors.clear();
			MarketRegistry.INSTANCE.load(FarmingForEngineers.configDir);
			sender.addChatMessage(new TextComponentTranslation("commands.farmingforengineers:reload.success"));
			if(AbstractRegistry.registryErrors.size() > 0) {
				sender.addChatMessage(new TextComponentString(TextFormatting.RED + "There were errors loading the Farming for Blockheads registries:"));
				TextFormatting lastFormatting = TextFormatting.WHITE;
				for(String error : AbstractRegistry.registryErrors) {
					sender.addChatMessage(new TextComponentString(lastFormatting + "* " + error));
					lastFormatting = lastFormatting == TextFormatting.GRAY ? TextFormatting.WHITE : TextFormatting.GRAY;
				}
			}
		} else {
			throw new WrongUsageException(getCommandUsage(sender));
		}
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return getListOfStringsMatchingLastWord(args, "reload");
	}

}
