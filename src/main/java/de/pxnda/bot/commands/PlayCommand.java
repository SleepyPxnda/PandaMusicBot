package de.pxnda.bot.commands;


import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import de.pxnda.bot.util.musichandlers.GuildMusicManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ICommand {

    private final Guild guild;
    private final AudioManager audioManager;
    private final VoiceChannel userVoiceChannel;
    private final TextChannel textChannel;
    private final String songUrl;
    private final User messageAuthor;
    private final GuildMusicManager manager;

    public PlayCommand(String serverId, String userId, String channelId, String songUrl) {

        this.guild = BotApplication.jda.getGuildById(serverId);
        audioManager = guild.getAudioManager();
        messageAuthor = guild.getMemberById(userId).getUser();
        userVoiceChannel = guild.getMemberById(userId).getVoiceState().getChannel();
        textChannel = guild.getTextChannelById(channelId);
        this.songUrl = songUrl;
        this.manager = BotApplication.playerManager.getGuildMusicManager(guild);

    }

    @Override
    public void execute() {
        if(userVoiceChannel != null){

            if(songUrl == null){
                textChannel.sendMessage("pls pass an URL as second argument").queue();
                return;
            }

            if(manager.player.isPaused()){
                manager.player.setPaused(false);
                textChannel.sendMessage("I got paused, so I **resumed** to play").queue();
            }

            try {
                audioManager.openAudioConnection(userVoiceChannel);
            }catch (UnsupportedOperationException e){
                textChannel.sendMessage("Can't join your Channel | Unsupported Operation").queue();
                return;
            }catch (InsufficientPermissionException e){
                textChannel.sendMessage("Can't join your Channel | Missing Permission: " + e.getPermission().getName()).queue();
                return;
            }catch (Exception e){
                textChannel.sendMessage("Can't join your Channel | New Exception").queue();
                return;
            }

            BotApplication.playerManager.loadAndPlay(textChannel, songUrl, messageAuthor, false);

        }
        else
        {
            textChannel.sendMessage("You need to be in a **Voicechannel** to use this").queue();
        }
    }
}
