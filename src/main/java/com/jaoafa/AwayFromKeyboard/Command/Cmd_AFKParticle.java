package com.jaoafa.AwayFromKeyboard.Command;

import com.jaoafa.AwayFromKeyboard.Library.AFKParticle;
import com.jaoafa.AwayFromKeyboard.Library.Library;
import com.jaoafa.AwayFromKeyboard.Library.MonoPlayerVote;
import com.jaoafa.AwayFromKeyboard.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Cmd_AFKParticle implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[AFKParticle] " + ChatColor.GREEN + "このコマンドはプレイヤーのみ使用できます。");
            return true;
        }
        Player player = (Player) sender;

        MonoPlayerVote mpv = new MonoPlayerVote(player);
        AFKParticle[] availableParts = Library.getAvailableAFKParticle(mpv.getVoteCount());

        if (args.length == 0) {
            sender.sendMessage(
                "[AFKParticle] " + ChatColor.GREEN + "次のパーティクルが設定できます: "
                    + Arrays.stream(availableParts).map(Enum::name)
                    .collect(Collectors.joining(", ")));
            return true;
        }

        if (args[0].equalsIgnoreCase("null")) {
            mpv.setSelectedParticle(null);
            sender.sendMessage("[AFKParticle] " + ChatColor.GREEN + "AFKパーティクルをリセットしました。最後に解放されたものが自動的に選択されます。");
            return true;
        }

        AFKParticle particle = AFKParticle.fromString(args[0]);
        if (particle == null) {
            sender.sendMessage("[AFKParticle] " + ChatColor.GREEN + "指定されたパーティクルは存在しないか利用できません。");
            return true;
        }
        if (!Arrays.asList(availableParts).contains(particle)) {
            sender.sendMessage("[AFKParticle] " + ChatColor.GREEN + "指定されたパーティクルはあなたの投票数では利用できません。");
            return true;
        }

        mpv.setSelectedParticle(particle);

        sender.sendMessage("[AFKParticle] " + ChatColor.GREEN + "AFKパーティクルを設定しました。");
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return Main.getJavaPlugin().onTabComplete(sender, cmd, alias, args);
        }
        Player player = (Player) sender;
        MonoPlayerVote mpv = new MonoPlayerVote(player);
        AFKParticle[] availableParts = Library.getAvailableAFKParticle(mpv.getVoteCount());
        if (args.length == 1) {
            return Arrays.stream(availableParts)
                .map(Enum::name)
                .filter(name -> name.toUpperCase().startsWith(args[0].toUpperCase()))
                .collect(Collectors.toList());
        }
        return Arrays.stream(availableParts).map(Enum::name).collect(Collectors.toList());
    }
}
