package com.jaoafa.AwayFromKeyboard.Library;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.connorlinfoot.titleapi.TitleAPI;
import com.jaoafa.AwayFromKeyboard.Main;
import com.jaoafa.AwayFromKeyboard.Task.Task_AFKING;
import com.jaoafa.jaoSuperAchievement2.API.AchievementAPI;
import com.jaoafa.jaoSuperAchievement2.API.Achievementjao;
import com.jaoafa.jaoSuperAchievement2.Lib.AchievementType;

public class AFKPlayer {
	static Map<String, AFKPlayer> players = new HashMap<>();

	private Player player;
	private boolean isAFKing = false;
	private long AFKStartTime = -1L;
	private BukkitTask Task = null;
	private ItemStack HeadItem = null;
	private long LastActionTime = -1L;
	private String message = null;

	public AFKPlayer(Player player) {
		if (players.containsKey(player.getName())) {
			AFKPlayer afkplayer = players.get(player.getName());
			this.isAFKing = afkplayer.isAFK();
			this.AFKStartTime = afkplayer.getAFKStartTime();
			this.Task = afkplayer.getTask();
			this.HeadItem = afkplayer.getHeadItem();
			this.LastActionTime = afkplayer.getLastActionTime();
		}
		this.player = player;
		players.put(player.getName(), this);
	}

	public void start() {
		start(null);
	}

	public void start(String message) {
		if (isAFK()) {
			return;
		}
		isAFKing = true;
		AFKStartTime = System.currentTimeMillis() / 1000L;

		MonoPlayerVote mpv = new MonoPlayerVote(player);

		PlayerInventory playerinv = player.getInventory();
		HeadItem = playerinv.getHelmet();
		player.getInventory().setHelmet(new ItemStack(Material.ICE));
		player.updateInventory();

		String listname = player.getPlayerListName().replaceAll(player.getName(),
				ChatColor.DARK_GRAY + player.getName());
		player.setPlayerListName(listname);

		TitleAPI.sendTitle(player, 0, 99999999, 0, ChatColor.RED + "AFK NOW!",
				ChatColor.BLUE + "" + ChatColor.BOLD + "When you are back, please enter the command '/afk' or Move.");

		if (message != null) {
			Bukkit.broadcastMessage(ChatColor.DARK_GRAY + player.getName() + " is afk! (" + message + ")");
		} else {
			Bukkit.broadcastMessage(ChatColor.DARK_GRAY + player.getName() + " is afk!");
		}
		this.message = message;

		AFKParticle particle = mpv.getSelectedParticle();
		if (particle == null) {
			particle = Library.getAvailableLastAFKParticle(mpv.getVoteCount());
		}
		Task = new Task_AFKING(player, particle).runTaskTimer(Main.getJavaPlugin(), 0L, particle.getTick());

		players.put(player.getName(), this);

		Plugin jsa = Bukkit.getPluginManager().getPlugin("jao-Super-Achievement2");
		if (jsa != null && jsa.isEnabled()) {
			if (!Achievementjao.getAchievement(player, new AchievementType(31))) {
				player.sendMessage(AchievementAPI.getPrefix() + "実績の解除中に問題が発生しました。もう一度お試しください。");
				return;
			}
		}

		if (Main.ServerChatChannel() != null) {
			if (message != null) {
				Main.ServerChatChannel()
						.sendMessage(Library.DiscordEscape(player.getName()) + " is afk! (`" + message + "`)")
						.queue();
			} else {
				Main.ServerChatChannel().sendMessage(Library.DiscordEscape(player.getName()) + " is afk!").queue();
			}
		}
	}

	public void end() {
		isAFKing = false;
		if (Task == null || Task.isCancelled()) {
			Task.cancel();
			Task = null;
		}
		PlayerInventory playerinv = player.getInventory();
		if (HeadItem != null) {
			if (HeadItem.getType() == Material.ICE)
				HeadItem = new ItemStack(Material.AIR);
			playerinv.setHelmet(HeadItem);
		} else {
			playerinv.setHelmet(new ItemStack(Material.AIR));
		}
		HeadItem = null;

		String listname = player.getPlayerListName().replaceAll(player.getName(), ChatColor.WHITE + player.getName());
		player.setPlayerListName(listname);

		Bukkit.broadcastMessage(ChatColor.DARK_GRAY + player.getName() + " is now online!");

		TitleAPI.clearTitle(player);

		players.put(player.getName(), this);

		if (Main.ServerChatChannel() != null) {
			Main.ServerChatChannel().sendMessage(Library.DiscordEscape(player.getName()) + " is now online!")
					.queue();
		}
	}

	public void clear() {
		if (isAFKing) {
			end();
		}
		if (players.containsKey(player.getName())) {
			players.remove(player.getName());
		}
	}

	public boolean isAFK() {
		if (Task == null || Task.isCancelled()) {
			isAFKing = false;
			players.put(player.getName(), this);
			return false;
		}
		return isAFKing;
	}

	public long getAFKingSec() {
		return (System.currentTimeMillis() / 1000L) - AFKStartTime;
	}

	public long getAFKStartTime() {
		return AFKStartTime;
	}

	public long getLastActionTime() {
		return LastActionTime;
	}

	public void setNowLastActionTime() {
		LastActionTime = System.currentTimeMillis() / 1000L;
		players.put(player.getName(), this);
	}

	public static Map<String, AFKPlayer> getAFKPlayers() {
		return players;
	}

	public Player getPlayer() {
		return player;
	}

	public BukkitTask getTask() {
		return Task;
	}

	public ItemStack getHeadItem() {
		return HeadItem;
	}

	public String getMessage() {
		return message;
	}
}
