package com.jaoafa.AwayFromKeyboard.Event;

import com.jaoafa.AwayFromKeyboard.Main;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.logging.Logger;

public class Event_DiscordReady extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        Main.ServerChatChannel = event.getJDA().getTextChannelById(Main.ServerChatID);
        Logger logger = Main.getMain().getLogger();
        if (Main.ServerChatChannel != null) {
            logger.info("Main.ServerChatChannel != null. found.");
        } else {
            logger.info("Main.ServerChatChannel == null. not found.");
        }
    }
}
