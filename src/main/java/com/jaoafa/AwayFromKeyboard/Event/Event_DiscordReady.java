package com.jaoafa.AwayFromKeyboard.Event;

import com.jaoafa.AwayFromKeyboard.Main;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import java.util.logging.Logger;

public class Event_DiscordReady {
    @SubscribeEvent
    public void onReadyEvent(ReadyEvent event) {
        Main.ServerChatChannel = event.getJDA().getTextChannelById(Main.ServerChatID);
        Logger logger = Main.getMain().getLogger();
        if (Main.ServerChatChannel != null) {
            logger.info("Main.ServerChatChannel != null. found.");
        } else {
            logger.info("Main.ServerChatChannel == null. not found.");
        }
    }
}
