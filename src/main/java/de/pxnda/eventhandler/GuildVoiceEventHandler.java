package de.pxnda.eventhandler;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceEventHandler extends ListenerAdapter {
    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        EventUtils.handleTempChannelBot(event, event.getGuild());
    }
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        if (event != null) {
            EventUtils.handleTempChannelBot(event, event.getGuild());
        }
    }

    //Event welches bei jeglichen Arten von Bewegung eines Users zwischen Voicechannels gefeuert wird
    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        EventUtils.handleTempChannelBot(event, event.getGuild());
    }
}
