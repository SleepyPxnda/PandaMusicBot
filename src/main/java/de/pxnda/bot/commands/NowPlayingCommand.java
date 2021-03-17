package de.pxnda.bot.commands;


import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static de.pxnda.bot.util.commands.UtilFuncs.convertToTimeStamp;

public class NowPlayingCommand implements ICommand {

    private final Guild guild;
    private final TextChannel channel;

    public NowPlayingCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        this.channel = e.getTextChannel();
    }

    @Override
    public void execute() {
        AudioTrack currentTrack = BotApplication.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();

        long timeNeededbyTracksBefore = currentTrack.getDuration() - currentTrack.getPosition();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("The current **Song** is - " + "[ " + convertToTimeStamp(timeNeededbyTracksBefore) + " ]");
        embed.addField(currentTrack.getInfo().title, currentTrack.getInfo().uri, false);

        channel.sendMessage(embed.build()).queue();
    }
}
