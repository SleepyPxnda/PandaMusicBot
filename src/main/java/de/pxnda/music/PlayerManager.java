package de.pxnda.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackUrl, User requester, boolean onWeb) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                if(onWeb){
                    channel.sendMessage("\uD83D\uDD35 - _" + track.getInfo().title + "_ - ➡️ **" + requester.getName()  + "**").queue();
                }
                else{
                    channel.sendMessage("\uD83D\uDFE2 - _" + track.getInfo().title + "_ - ➡️ **" + requester.getName()  + "**").queue();
                }


                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                if(playlist.getTracks().size() > 200){
                    channel.sendMessage("Too many songs in the List").queue();
                    return;
                }

                for (AudioTrack track : playlist.getTracks()){
                    play(musicManager, track);
                    System.out.println("Trackname: " + track.getInfo().title);
                }
                if(onWeb) {
                    channel.sendMessage("\uD83D\uDD35 " + playlist.getTracks().size() + " Songs from _" + playlist.getName() + "_ ➡️ **" + requester.getName() + "**").queue();
                } else {
                    channel.sendMessage("\uD83D\uDFE2 " + playlist.getTracks().size() + " Songs from _" + playlist.getName() + "_ ➡️ **" + requester.getName() + "**").queue();

                }
            }

            @Override
            public void noMatches() {
                channel.sendMessage("⚫ Nothing found... _" + trackUrl + "_ ").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("\uD83D\uDD34 Error _" + exception.getMessage() + "_").queue();
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }
}
