package com.jaoafa.AwayFromKeyboard;

import com.jaoafa.AwayFromKeyboard.Command.Cmd_AFK;
import com.jaoafa.AwayFromKeyboard.Command.Cmd_AFKParticle;
import com.jaoafa.AwayFromKeyboard.Command.Cmd_Part;
import com.jaoafa.AwayFromKeyboard.Event.Event_AFK;
import com.jaoafa.AwayFromKeyboard.Event.Event_DiscordReady;
import com.jaoafa.AwayFromKeyboard.Library.MySQLDBManager;
import com.jaoafa.AwayFromKeyboard.Task.Task_AFK;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    static Main main;
    static JDA JDA;
    public static String ServerChatID;
    public static TextChannel ServerChatChannel;
    static MySQLDBManager MySQLDBManager;

    /**
     * プラグインが起動したときに呼び出し
     *
     * @author mine_book000
     * @since 2020/06/30
     */
    @Override
    public void onEnable() {
        main = this;

        FileConfiguration config = getConfig();
        if (!config.contains("discordtoken")) {
            getLogger().warning("Discordへの接続に失敗しました。(コンフィグにトークンが設定されていません)");
            getLogger().warning("AwayFromKeyboardプラグインを終了します。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (config.contains("serverchatid")) {
            ServerChatID = config.getString("serverchatid");
        }

        try {
            JDABuilder jdabuilder = JDABuilder.createDefault(config.getString("discordtoken"))
                    .setAutoReconnect(true)
                    .setBulkDeleteSplittingEnabled(false)
                    .setToken(config.getString("discordtoken"))
                    .setContextEnabled(false);

            jdabuilder.addEventListeners(new Event_DiscordReady());

            JDA = jdabuilder.build().awaitReady();
        } catch (Exception e) {
            getLogger().warning("Discordへの接続に失敗しました。(" + e.getMessage() + ")");
            getLogger().warning("AwayFromKeyboardプラグインを終了します。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!config.contains("sqlserver") || !config.contains("sqlport") || !config.contains("sqldatabase")
            || !config.contains("sqluser") || !config.contains("sqlpassword")) {
            getLogger().warning("MySQLへの接続に失敗しました。(コンフィグにSQL接続情報が設定されていません)");
            getLogger().warning("AwayFromKeyboardプラグインを終了します。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            MySQLDBManager = new MySQLDBManager(
                config.getString("sqlserver"),
                config.getString("sqlport"),
                config.getString("sqldatabase"),
                config.getString("sqluser"),
                config.getString("sqlpassword"));
        } catch (ClassNotFoundException e) {
            getLogger().warning("MySQLへの接続に失敗しました。(MySQL接続するためのクラスが見つかりません)");
            getLogger().warning("AwayFromKeyboardプラグインを終了します。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Objects.requireNonNull(getCommand("afk")).setExecutor(new Cmd_AFK());
        Objects.requireNonNull(getCommand("afkparticle")).setExecutor(new Cmd_AFKParticle());
        Objects.requireNonNull(getCommand("afkparticle")).setTabCompleter(new Cmd_AFKParticle());
        Objects.requireNonNull(getCommand("part")).setExecutor(new Cmd_Part());

        getServer().getPluginManager().registerEvents(new Event_AFK(), this);

        new Task_AFK().runTaskTimerAsynchronously(this, 0L, 1200L);
    }

    public static JavaPlugin getJavaPlugin() {
        return main;
    }

    public static JDA getJDA() {
        return JDA;
    }

    public static TextChannel ServerChatChannel() {
        return ServerChatChannel;
    }

    public static MySQLDBManager getMySQLDBManager() {
        return MySQLDBManager;
    }

    public static Main getMain() {
        return main;
    }
}
