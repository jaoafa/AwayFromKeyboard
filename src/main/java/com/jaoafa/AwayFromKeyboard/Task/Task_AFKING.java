package com.jaoafa.AwayFromKeyboard.Task;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
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

        if(!player.playerListName().contains(Component.text(player.getName(), NamedTextColor.DARK_GRAY))) {
            player.playerListName(player.playerListName()
                .replaceText(TextReplacementConfig
                    .builder()
                    .match(player.getName())
                    .replacement(Component.text(player.getName(), NamedTextColor.DARK_GRAY))
                    .build()));
        }
	}
}
