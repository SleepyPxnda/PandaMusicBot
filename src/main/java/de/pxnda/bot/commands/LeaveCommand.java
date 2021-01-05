package de.pxnda.bot.commands;


import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LeaveCommand implements ICommand {

    private final Guild guild;
    private final TextChannel channel;

    public LeaveCommand(MessageReceivedEvent event){
        this.guild = event.getGuild();
        this.channel = event.getTextChannel();
    }

    @Override
    public void execute() {
        VoiceChannel currentChannel = guild.getSelfMember().getVoiceState().getChannel();

        if(currentChannel != null){
            guild.getAudioManager().closeAudioConnection();
            BotApplication.playerManager.getGuildMusicManager(guild).player.stopTrack();
            BotApplication.playerManager.getGuildMusicManager(guild).scheduler.getQueue().clear();
            BotApplication.playerManager.getGuildMusicManager(guild).scheduler.setLooping(false);
        }
        else
        {
            channel.sendMessage("I'm not in a Voice Channel").queue();
        }
    }
}
