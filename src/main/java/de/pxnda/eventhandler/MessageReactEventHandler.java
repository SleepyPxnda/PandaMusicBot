package de.pxnda.eventhandler;

import de.pxnda.Main;
import de.pxnda.Utils.CommandExecutor;
import de.pxnda.command.PlayCommand;
import de.pxnda.command.PrepareSavedPlaylistCommand;
import de.pxnda.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import okhttp3.internal.http2.Http2Connection;

import java.awt.*;

public class MessageReactEventHandler extends ListenerAdapter {

    public void onMessageReactionAdd(MessageReactionAddEvent event){
        String msg = event.getMessageId();
        Guild guild = event.getGuild();
        GuildMusicManager manager = Main.playerManager.getGuildMusicManager(guild);
        TextChannel textChannel = event.getTextChannel();
        AudioManager audioManager = guild.getAudioManager();
        VoiceChannel userVoiceChannel = event.getMember().getVoiceState().getChannel();

        if(event.getUser().isBot())
                return;

        if(event.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("▶")){
            if(PrepareSavedPlaylistCommand.idToLink.containsKey(msg)){
                String songUrl = PrepareSavedPlaylistCommand.idToLink.get(msg);

                if(manager.player.isPaused()){
                    manager.player.setPaused(false);
                    textChannel.sendMessage("I got paused, so I **resumed** to play");
                }

                audioManager.openAudioConnection(userVoiceChannel);

                Main.playerManager.loadAndPlay(textChannel, songUrl, null);
            }
        }
        if(event.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("❌")){
            if(PrepareSavedPlaylistCommand.idToLink.containsKey(msg)){
                if(manager.player.getPlayingTrack() != null){
                    manager.player.stopTrack();
                    manager.scheduler.getQueue().clear();
                    textChannel.sendMessage("I **stopped** the current Song for you").queue();
                }
            }
        }
    }

}
