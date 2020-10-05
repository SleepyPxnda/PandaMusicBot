package de.pxnda.command;

import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import de.pxnda.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ICommand {

    private final Guild guild;
    private final AudioManager audioManager;
    private final VoiceChannel userVoiceChannel;
    private final TextChannel textChannel;
    private final Message message;
    private final GuildMusicManager manager;

    public PlayCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        audioManager = guild.getAudioManager();
        userVoiceChannel = e.getMember().getVoiceState().getChannel();
        textChannel = e.getTextChannel();
        message = e.getMessage();
        this.manager = Main.playerManager.getGuildMusicManager(guild);
    }

    @Override
    public void execute() {
        if(userVoiceChannel != null){


            int argumentLength = message.getContentRaw().split(" ").length;

            if(argumentLength <= 1){
                textChannel.sendMessage("pls pass an URL as second argument").queue();
                return;
            }

            String songUrl = message.getContentRaw().split(" ")[1];

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


            Main.playerManager.loadAndPlay(textChannel, songUrl, message.getAuthor(), false);

        }
        else
        {
            textChannel.sendMessage("You need to be in a **Voicechannel** to use this").queue();
        }
    }
}
