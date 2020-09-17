package com.jaoafa.AwayFromKeyboard.Task;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.jaoafa.AwayFromKeyboard.Library.AFKPlayer;
import com.jaoafa.jaoSuperAchievement2.API.AchievementAPI;
import com.jaoafa.jaoSuperAchievement2.API.Achievementjao;
import com.jaoafa.jaoSuperAchievement2.Lib.AchievementType;

public class Task_AFK extends BukkitRunnable {
	CommandSender debugSender = null;
	public Task_AFK(){}
	public Task_AFK(CommandSender debugSender){
		this.debugSender = debugSender;
	}
	/**
	 * AFKチェックタスク(1分毎)
	 * @author mine_book000
	 */
	@Override
	public void run() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			AFKPlayer afkplayer = new AFKPlayer(player);
			if (afkplayer.isAFK()) {
				Plugin jsa = Bukkit.getPluginManager().getPlugin("jao-Super-Achievement2");
				if (jsa != null && jsa.isEnabled()) {
					if (afkplayer.getAFKingSec() >= 5 * 60) {
						if (!Achievementjao.getAchievement(player, new AchievementType(32))) {
							player.sendMessage(AchievementAPI.getPrefix() + "実績の解除中に問題が発生しました。もう一度お試しください。");
							return;
						}
					}
					if (afkplayer.getAFKingSec() >= 15 * 60) {
						if (!Achievementjao.getAchievement(player, new AchievementType(33))) {
							player.sendMessage(AchievementAPI.getPrefix() + "実績の解除中に問題が発生しました。もう一度お試しください。");
							return;
						}
					}
				}
				sendDebugSender(player.getName() + ": isAFK = true -> skip");
				continue; // AFKかどうかを調べるのでAFKは無視
			}
			if (afkplayer.getLastActionTime() == -1L) {
				sendDebugSender(player.getName() + ": getLastActionTime = -1L -> skip");
				continue; // AFKタイムが設定されてないと処理しようがないので無視
			}
			if (player.getGameMode() == GameMode.SPECTATOR) {
				sendDebugSender(player.getName() + ": getGameMode() = SPECTATOR -> skip");
				continue; // スペクテイターモードは誰かにくっついて動いててもMoveイベント発生しないので無視
			}
			if (player.isInsideVehicle()) {
				sendDebugSender(player.getName() + ": isInsideVehicle() = true -> skip");
				continue; // トロッコ関連はMoveイベント発生しないっぽい？
			}
			long nowtime = System.currentTimeMillis() / 1000L;
			long lastmovetime = afkplayer.getLastActionTime();
			long sa = nowtime - lastmovetime; // 前回移動した時間から現在の時間の差を求めて3分差があったらAFK扱い
			if (sa >= 180) {
				afkplayer.start();
				sendDebugSender(String.format("%s: nowtime = %d / lastmovetime = %d / sa(%d) >= 180 -> start afk", player.getName(), nowtime, lastmovetime, sa));
			}else{
				sendDebugSender(String.format("%s: nowtime = %d / lastmovetime = %d / sa(%d) < 180 -> skip", player.getName(), nowtime, lastmovetime, sa));
			}
		}
	}
	void sendDebugSender(String message){
		debugSender.sendMessage("[Task_AFK-DEBUG] " + ChatColor.GREEN + message);
	}
}
