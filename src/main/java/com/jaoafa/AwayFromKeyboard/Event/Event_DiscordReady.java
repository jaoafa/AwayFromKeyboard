package com.jaoafa.AwayFromKeyboard.Event;

import com.jaoafa.AwayFromKeyboard.Main;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class Event_DiscordReady {
	@SubscribeEvent
	public void onReadyEvent(ReadyEvent event) {
		Main.ServerChatChannel = event.getJDA().getTextChannelById(Main.ServerChatID);
		if (Main.ServerChatChannel != null) {
			System.out.println("Main.ServerChatChannel != null. found.");
		} else {
			System.out.println("Main.ServerChatChannel == null. not found.");
		}
	}
}
