package com.jaoafa.AwayFromKeyboard.Event;

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

import com.connorlinfoot.titleapi.TitleAPI;
import com.jaoafa.AwayFromKeyboard.Library.AFKPlayer;
import com.jaoafa.jaoSuperAchievement2.API.AchievementAPI;
import com.jaoafa.jaoSuperAchievement2.API.Achievementjao;
import com.jaoafa.jaoSuperAchievement2.Lib.AchievementType;

public class Event_AFK implements Listener {
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		AFKPlayer afkplayer = new AFKPlayer(player);
		afkplayer.setNowLastActionTime();
		if (!afkplayer.isAFK()) {
			return;
		}
		afkplayer.end();
	}

	@EventHandler
	public void OnEvent_PlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		AFKPlayer afkplayer = new AFKPlayer(player);
		if (afkplayer.isAFK()) {
			Plugin jsa = Bukkit.getPluginManager().getPlugin("jao-Super-Achievement2");
			if (jsa != null && jsa.isEnabled()) {
				if (!Achievementjao.getAchievement(player, new AchievementType(36))) {
					player.sendMessage(AchievementAPI.getPrefix() + "実績の解除中に問題が発生しました。もう一度お試しください。");
					return;
				}
			}
		}
		afkplayer.clear();
	}

	@EventHandler
	public void OnEvent_PlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		AFKPlayer afkplayer = new AFKPlayer(player);
		afkplayer.setNowLastActionTime();
		if (!afkplayer.isAFK()) {
			return;
		}
		afkplayer.end();
		TitleAPI.clearTitle(player);
	}

	@EventHandler
	public void OnEvent_InvClose(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getPlayer();
		AFKPlayer afkplayer = new AFKPlayer(player);
		if (!afkplayer.isAFK()) {
			return;
		}
		ItemStack is = player.getInventory().getHelmet();
		if (is != null && is.getType() == Material.ICE) {
			return;
		}

		if (!Achievementjao.getAchievement(player, new AchievementType(40))) {
			player.sendMessage(AchievementAPI.getPrefix() + "実績の解除中に問題が発生しました。もう一度お試しください。");
		}
	}
}
