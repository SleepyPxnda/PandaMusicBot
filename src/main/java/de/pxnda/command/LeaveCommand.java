package de.pxnda.command;

import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LeaveCommand implements ICommand {

    private Guild guild;
    private TextChannel channel;
    private Message message;

    public LeaveCommand(MessageReceivedEvent event){
        this.guild = event.getGuild();
        this.channel = event.getTextChannel();
        this.message = event.getMessage();
    }

    @Override
    public void execute() {
        VoiceChannel currentChannel = guild.getSelfMember().getVoiceState().getChannel();

        if(currentChannel != null){
            guild.getAudioManager().closeAudioConnection();
            Main.playerManager.getGuildMusicManager(guild).player.stopTrack();
            Main.playerManager.getGuildMusicManager(guild).scheduler.getQueue().clear();
        }
        else
        {
            channel.sendMessage("I'm not in a Voice Channel").queue();
        }
    }
}
