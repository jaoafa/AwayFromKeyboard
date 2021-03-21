package com.jaoafa.AwayFromKeyboard.Command;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jaoafa.AwayFromKeyboard.Main;
import com.jaoafa.AwayFromKeyboard.Library.AFKParticle;
import com.jaoafa.AwayFromKeyboard.Task.Task_Part;

public class Cmd_Part implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 8) {
			sender.sendMessage(
					"Usage: /part <Particle> <Count> <Extra> <Tick> <OffsetX> <OffsetY> <OffsetZ> <SpawnOffset>");
			return true;
		}
		try {
			int count = Integer.parseInt(args[1]);
			double extra = Double.parseDouble(args[2]);
			long tick = Long.parseLong(args[3]);
			double offsetX = Double.parseDouble(args[4]);
			double offsetY = Double.parseDouble(args[5]);
			double offsetZ = Double.parseDouble(args[6]);
			double spawnOffset = Double.parseDouble(args[7]);
			if (sender instanceof Player) {
				Player player = (Player) sender;

				AFKParticle particle = AFKParticle.fromString(args[0]);
				if (particle == null) {
					sender.sendMessage("指定されたパーティクルは非対応か存在しません。");
					return true;
				}

				Location loc = player.getLocation();

				(new Task_Part(loc, particle, count, extra, offsetX, offsetY, offsetZ, spawnOffset))
						.runTaskTimer(Main.getJavaPlugin(), 0L, tick);
			} else if (sender instanceof BlockCommandSender) {
				BlockCommandSender bcs = (BlockCommandSender) sender;

				if (bcs.getBlock() == null || !(bcs.getBlock().getState() instanceof org.bukkit.block.CommandBlock)) {
					return true;
				}
				AFKParticle particle = AFKParticle.fromString(args[0]);
				if (particle == null) {
					sender.sendMessage("指定されたパーティクルは非対応か存在しません。");
					return true;
				}

				Location loc = bcs.getBlock().getLocation().add(0.0D, 1.0D, 0.0D);

				new Task_Part(loc, particle, count, extra, offsetX, offsetY, offsetZ, spawnOffset)
						.runTaskTimer(Main.getJavaPlugin(), 0L, tick);
			}
			return true;
		} catch (Exception e) {
			sender.sendMessage("Exception: " + e.getClass().getName() + " -> " + e.getMessage());
		}
		return false;
	}
}
