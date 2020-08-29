/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 */
package net.minecraft.server.commands;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.players.BanListEntry;
import net.minecraft.server.players.PlayerList;

public class BanListCommands {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("banlist").requires(commandSourceStack -> commandSourceStack.hasPermission(3))).executes(commandContext -> {
            PlayerList playerList = ((CommandSourceStack)commandContext.getSource()).getServer().getPlayerList();
            return BanListCommands.showList((CommandSourceStack)commandContext.getSource(), Lists.newArrayList((Iterable)Iterables.concat(playerList.getBans().getEntries(), playerList.getIpBans().getEntries())));
        })).then(Commands.literal("ips").executes(commandContext -> BanListCommands.showList((CommandSourceStack)commandContext.getSource(), ((CommandSourceStack)commandContext.getSource()).getServer().getPlayerList().getIpBans().getEntries())))).then(Commands.literal("players").executes(commandContext -> BanListCommands.showList((CommandSourceStack)commandContext.getSource(), ((CommandSourceStack)commandContext.getSource()).getServer().getPlayerList().getBans().getEntries()))));
    }

    private static int showList(CommandSourceStack commandSourceStack, Collection<? extends BanListEntry<?>> collection) {
        if (collection.isEmpty()) {
            commandSourceStack.sendSuccess(new TranslatableComponent("commands.banlist.none"), false);
        } else {
            commandSourceStack.sendSuccess(new TranslatableComponent("commands.banlist.list", collection.size()), false);
            for (BanListEntry<?> banListEntry : collection) {
                commandSourceStack.sendSuccess(new TranslatableComponent("commands.banlist.entry", banListEntry.getDisplayName(), banListEntry.getSource(), banListEntry.getReason()), false);
            }
        }
        return collection.size();
    }
}
