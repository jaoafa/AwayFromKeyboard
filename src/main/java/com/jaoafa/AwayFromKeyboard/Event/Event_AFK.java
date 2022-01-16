package com.jaoafa.AwayFromKeyboard.Event;

import com.jaoafa.AwayFromKeyboard.Library.AFKPlayer;
import com.jaoafa.jaosuperachievement2.api.Achievementjao;
import com.jaoafa.jaosuperachievement2.lib.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Event_AFK implements Listener {
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		AFKPlayer afkplayer = new AFKPlayer(player);
		if (afkplayer.isAFK()) {
            afkplayer.end();
		}
        afkplayer.setNowLastActionTime();
	}

	@EventHandler
	public void OnEvent_PlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		AFKPlayer afkplayer = new AFKPlayer(player);
		if (afkplayer.isAFK()) {
			Plugin jsa = Bukkit.getPluginManager().getPlugin("jao-Super-Achievement2");
			if (jsa != null && jsa.isEnabled()) {
                Achievementjao.getAchievementAsync(player, Achievement.WEREYOUTHERE);
			}
		}
		afkplayer.clear();
	}

	@EventHandler
	public void OnEvent_PlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		AFKPlayer afkplayer = new AFKPlayer(player);
        if (afkplayer.isAFK()) {
            afkplayer.end();
        }
        afkplayer.setNowLastActionTime();
		player.resetTitle();
	}

	@EventHandler
	public void OnEvent_InvClose(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player player)) {
			return;
		}
        AFKPlayer afkplayer = new AFKPlayer(player);
		if (!afkplayer.isAFK()) {
			return;
		}
		ItemStack is = player.getInventory().getHelmet();
		if (is != null && is.getType() == Material.ICE) {
			return;
		}

        Achievementjao.getAchievementAsync(player, Achievement.SOHOT);
	}
}
