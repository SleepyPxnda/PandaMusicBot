package de.pxnda.command;

import de.pxnda.Main;
import de.pxnda.Utils.CommandExecutor;
import de.pxnda.Utils.ICommand;
import de.pxnda.command.JoinCommand;
import de.pxnda.music.GuildMusicManager;
import de.pxnda.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.internal.audio.AudioConnection;

import java.util.concurrent.TimeUnit;

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

            if(manager.player.isPaused()){
                manager.player.setPaused(false);
                textChannel.sendMessage("I got paused, so I **resumed** to play");
            }

            audioManager.openAudioConnection(userVoiceChannel);

            Main.playerManager.loadAndPlay(textChannel, message.getContentRaw().split(" ")[1], message);
            message.delete().queueAfter(5, TimeUnit.SECONDS);

        }
        else
        {
            textChannel.sendMessage("You need to be in a **Voicechannel** to use this").queue();
        }
    }
}
