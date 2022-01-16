package com.jaoafa.AwayFromKeyboard.Command;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.jaoafa.AwayFromKeyboard.Task.Task_AFK;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jaoafa.AwayFromKeyboard.Library.AFKPlayer;
import org.jetbrains.annotations.NotNull;

public class Cmd_AFK implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (args.length >= 1 && args[0].equalsIgnoreCase("help")) {
			return false;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("data")) {
				Map<String, AFKPlayer> list = AFKPlayer.getAFKPlayers();
				sender.sendMessage("[AFK] " + ChatColor.GREEN + "データ数: " + list.size());
				for (Entry<String, AFKPlayer> player : list.entrySet()) {
					sender.sendMessage("[AFK] " + ChatColor.GREEN + player.getKey() + " | afking: " + player.getValue().isAFK());
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("debug")) {
				sender.sendMessage("[AFK] " + ChatColor.GREEN + "Task_AFKタスクをデバッグモードで実行します。");
				new Task_AFK(sender).run();
				return true;
			}
			Player player = Bukkit.getPlayerExact(args[0]);
			if (player == null) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("[AFK] " + ChatColor.GREEN + "このコマンドはゲーム内から実行してください。");
					return true;
				}
				AFKPlayer afkplayer = new AFKPlayer((Player) sender);
				String message = String.join(" ", args);
				if (!afkplayer.isAFK()) {
					afkplayer.start(message);
				} else {
					sender.sendMessage("[AFK] " + ChatColor.GREEN + "あなたは現在AFK状態です。AFKにメッセージを設定するには、一度AFKを解除してください。");
				}
				return true;
			}
			AFKPlayer afkplayer = new AFKPlayer(player);
			if (afkplayer.isAFK()) {
				sender.sendMessage("[AFK] " + ChatColor.GREEN + "指定されたプレイヤー「" + player.getName() + "」は現在AFKです。");
				long sa = afkplayer.getAFKingSec();
				StringBuilder builder = new StringBuilder();

				int year = (int) (sa / 31536000L);
				int year_remain = (int) (sa % 31536000L);
				if (year != 0) {
					builder.append(year).append("年");
				}
				int month = (int) (year_remain / 2592000L);
				int month_remain = (int) (year_remain % 2592000L);
				if (month != 0) {
					builder.append(month).append("か月");
				}
				int day = (int) (month_remain / 86400L);
				int day_remain = (int) (month_remain % 86400L);
				if (day != 0) {
					builder.append(day).append("日");
				}
				int hour = (int) (day_remain / 3600L);
				int hour_remain = (int) (day_remain % 3600L);
				if (hour != 0) {
					builder.append(hour).append("時間");
				}
				int minute = (int) (hour_remain / 60L);
				if (minute != 0) {
					builder.append(minute).append("分");
				}
				int sec = (int) (hour_remain % 60L);
				if (sec != 0) {
					builder.append(sec).append("秒");
				}
				String startTime = sdfFormat(new Date(afkplayer.getAFKStartTime() * 1000));
				sender.sendMessage("[AFK] " + ChatColor.GREEN + "AFK開始時刻: " + startTime);
				sender.sendMessage("[AFK] " + ChatColor.GREEN + "AFK経過時間: " + builder);
			} else {
				sender.sendMessage("[AFK] " + ChatColor.GREEN + "指定されたプレイヤー「" + player.getName() + "」は現在AFKではありません。");
				if (afkplayer.getLastActionTime() != -1L) {
					String actionTime = sdfFormat(new Date(afkplayer.getLastActionTime() * 1000));
					sender.sendMessage("[AFK] " + ChatColor.GREEN + "このプレイヤーの最終アクションは " + actionTime + " です。");
					if (afkplayer.getMessage() != null) {
						sender.sendMessage("[AFK] " + ChatColor.GREEN + "プレイヤーが設定したAFKメッセージ: " + afkplayer.getMessage());
					}
				}
			}
			return true;
		}
		if (!(sender instanceof Player player)) {
			sender.sendMessage("[AFK] " + ChatColor.GREEN + "このコマンドはゲーム内から実行してください。");
			return true;
		}
        AFKPlayer afkplayer = new AFKPlayer(player);
		if (!afkplayer.isAFK()) {
			afkplayer.start();
		} else {
			afkplayer.end();
		}
		return true;
	}

	public static String sdfFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(date);
	}
}
