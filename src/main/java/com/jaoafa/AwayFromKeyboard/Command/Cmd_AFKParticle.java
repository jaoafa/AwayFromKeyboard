package com.jaoafa.AwayFromKeyboard.Command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.jaoafa.AwayFromKeyboard.Main;
import com.jaoafa.AwayFromKeyboard.Library.AFKParticle;
import com.jaoafa.AwayFromKeyboard.Library.Library;
import com.jaoafa.AwayFromKeyboard.Library.MonoPlayerVote;

public class Cmd_AFKParticle implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
							+ String.join(", ", Arrays.asList(availableParts).stream().map(part -> part.name())
									.collect(Collectors.toList())));
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
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if (!(sender instanceof Player)) {
			return Main.getJavaPlugin().onTabComplete(sender, cmd, alias, args);
		}
		Player player = (Player) sender;
		MonoPlayerVote mpv = new MonoPlayerVote(player);
		AFKParticle[] availableParts = Library.getAvailableAFKParticle(mpv.getVoteCount());
		if (args.length == 1) {
			return Arrays.asList(availableParts).stream()
					.filter(part -> part.name().toUpperCase().startsWith(args[0].toUpperCase()))
					.map(part -> part.name()).collect(Collectors.toList());
		}
		return Arrays.asList(availableParts).stream().map(part -> part.name()).collect(Collectors.toList());
	}
}
