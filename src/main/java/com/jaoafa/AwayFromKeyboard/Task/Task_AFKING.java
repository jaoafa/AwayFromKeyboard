package com.jaoafa.AwayFromKeyboard.Task;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.jaoafa.AwayFromKeyboard.Library.AFKParticle;
import com.jaoafa.AwayFromKeyboard.Library.AFKPlayer;

public class Task_AFKING extends BukkitRunnable {
	private final Player player;
	private final AFKParticle particle;

	public Task_AFKING(Player player, AFKParticle particle) {
		this.player = player;
		this.particle = particle;
	}

	@Override
	public void run() {
		AFKPlayer afkplayer = new AFKPlayer(player);
		if (!afkplayer.isAFK()) {
			return;
		}
		if (!player.isOnline()) {
			afkplayer.end();
		}
		if (afkplayer.getTask().getTaskId() != getTaskId()) {
			cancel();
			return;
		}
		Location loc = player.getLocation();
		if (particle.getSpawnOffset() != 0D) {
			loc = loc.add(0D, particle.getSpawnOffset(), 0D);
		}
		player.getWorld().spawnParticle(particle.getParticle(), loc, particle.getCount(),
				particle.getOffsetX(), particle.getOffsetY(), particle.getOffsetZ(), particle.getExtra());
		String listname = player.getPlayerListName();
		if (!listname.contains(ChatColor.DARK_GRAY + player.getName())) {
			listname = listname.replaceAll(player.getName(), ChatColor.DARK_GRAY + player.getName());
			player.setPlayerListName(listname);
		}
	}
}
