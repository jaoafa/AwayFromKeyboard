package com.jaoafa.AwayFromKeyboard.Library;

import com.jaoafa.AwayFromKeyboard.Main;
import com.jaoafa.AwayFromKeyboard.Task.Task_AFKING;
import com.jaoafa.jaosuperachievement2.api.Achievementjao;
import com.jaoafa.jaosuperachievement2.lib.Achievement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AFKPlayer {
    static final Map<String, AFKPlayer> players = new HashMap<>();

    private final Player player;
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

        player.playerListName(player.playerListName()
            .replaceText(TextReplacementConfig
                .builder()
                .matchLiteral(player.getName())
                .replacement(Component.text(player.getName(), NamedTextColor.DARK_GRAY))
                .build()));

        player.sendTitlePart(TitlePart.TITLE, Component.text("AFK NOW!", NamedTextColor.RED));
        player.sendTitlePart(TitlePart.SUBTITLE, Component.text("When you are back, please enter the command '/afk' or Move.", NamedTextColor.RED, TextDecoration.BOLD));
        player.sendTitlePart(TitlePart.TIMES, Title.Times.of(Duration.ZERO, ChronoUnit.FOREVER.getDuration(), Duration.ZERO));

        if (message != null) {
            Bukkit.getServer().broadcast(Component.text("%s is afk! (%s)".formatted(player.getName(), message), NamedTextColor.DARK_GRAY));
        } else {
            Bukkit.getServer().broadcast(Component.text(player.getName() + " is afk!", NamedTextColor.DARK_GRAY));
        }
        this.message = message;

        AFKParticle particle = mpv.getSelectedParticle();
        AFKParticle[] availableParts = Library.getAvailableAFKParticle(mpv.getVoteCount());
        if (!Arrays.asList(availableParts).contains(particle)) {
            particle = null;
            mpv.setSelectedParticle(null);
        }
        if (particle == null) {
            particle = Library.getAvailableLastAFKParticle(mpv.getVoteCount());
        }
        Task = new Task_AFKING(player, particle).runTaskTimer(Main.getJavaPlugin(), 0L, particle.getTick());

        players.put(player.getName(), this);

        Plugin jsa = Bukkit.getPluginManager().getPlugin("jao-Super-Achievement2");
        if (jsa != null && jsa.isEnabled()) {
            Achievementjao.getAchievementAsync(player, Achievement.BACKSOON);
        }

        if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE) && jsa != null && jsa.isEnabled()) {
            Achievementjao.getAchievementAsync(player, Achievement.HOTAFK);
        }

        if (Main.ServerChatChannel() != null) {
            if (message != null) {
                Main.ServerChatChannel()
                    .sendMessage(Library.DiscordEscape(player.getName()) + " is afk! (`" + message + "`)")
                    .queue();
            } else {
                Main.ServerChatChannel().sendMessage(Library.DiscordEscape(player.getName()) + " is afk!").queue();
            }
        } else {
            Main.ServerChatChannel = Main.getJDA().getTextChannelById(Main.ServerChatID);
        }
    }

    public void end() {
        isAFKing = false;
        if (Task != null && !Task.isCancelled()) {
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

        player.playerListName(player.playerListName()
            .replaceText(TextReplacementConfig
                .builder()
                .match(player.getName())
                .replacement(Component.text(player.getName(), NamedTextColor.WHITE))
                .build()));

        player.resetTitle();

        Bukkit.getServer().broadcast(Component.text("%s is now online!".formatted(player.getName()), NamedTextColor.DARK_GRAY));

        addAFKTime();

        players.put(player.getName(), this);

        if (Main.ServerChatChannel() != null) {
            Main.ServerChatChannel().sendMessage(Library.DiscordEscape(player.getName()) + " is now online!")
                .queue();
        } else {
            Main.ServerChatChannel = Main.getJDA().getTextChannelById(Main.ServerChatID);
        }
    }

    public void clear() {
        if (isAFKing) {
            end();
        }
        players.remove(player.getName());
    }

    public boolean isAFK() {
        if (Task == null || Task.isCancelled()) {
            isAFKing = false;
            players.put(player.getName(), this);
            return false;
        }
        return isAFKing;
    }

    void addAFKTime(){
        try {
            Connection conn = Main.getMySQLDBManager().getConnection();
            boolean exists;
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM afktime WHERE uuid = ?")) {
                stmt.setString(1, player.getUniqueId().toString());
                try (ResultSet res = stmt.executeQuery()) {
                    exists = res.next();
                }
            }

            if(exists){
                // あれば加算
                try (PreparedStatement stmt = conn.prepareStatement("UPDATE afktime SET seconds = seconds + ? WHERE uuid = ?")) {
                    stmt.setLong(1, getAFKingSec());
                    stmt.setString(2, player.getUniqueId().toString());
                    stmt.executeUpdate();
                }
            }else{
                // なければ作成
                try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO afktime (player, uuid, seconds, created_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)")) {
                    stmt.setString(1, player.getName());
                    stmt.setString(2, player.getUniqueId().toString());
                    stmt.setLong(3, getAFKingSec());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
