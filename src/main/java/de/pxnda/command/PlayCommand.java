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
    private Guild guild;
    private AudioManager audioManager;
    private VoiceChannel userVoiceChannel;
    private TextChannel textChannel;
    private Message message;

    public PlayCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        audioManager = guild.getAudioManager();
        userVoiceChannel = e.getMember().getVoiceState().getChannel();
        textChannel = e.getTextChannel();
        message = e.getMessage();
    }

    @Override
    public void execute() {
        if(userVoiceChannel != null){
            AudioManager manager = guild.getAudioManager();
            manager.openAudioConnection(userVoiceChannel);
            message.addReaction("✅").queue();

            Main.playerManager.loadAndPlay(textChannel, message.getContentRaw().split(" ")[1], message);

        }
        else
        {
            textChannel.sendMessage("You need to be in a Voicechannel to use this").queue();
        }
    }
}
