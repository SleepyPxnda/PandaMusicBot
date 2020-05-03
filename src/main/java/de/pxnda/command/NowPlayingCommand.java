package de.pxnda.command;


import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static de.pxnda.Utils.UtilFuncs.convertToTimeStamp;

public class NowPlayingCommand implements ICommand {

    private final Guild guild;
    private final TextChannel channel;

    public NowPlayingCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        this.channel = e.getTextChannel();
    }

    @Override
    public void execute() {
        AudioTrack currentTrack = Main.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();

        long timeNeededbyTracksBefore = currentTrack.getDuration() - currentTrack.getPosition();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("The current **Son**g is - " + "[ " + convertToTimeStamp(timeNeededbyTracksBefore) + " ]");
        embed.addField(currentTrack.getInfo().title, currentTrack.getInfo().uri, false);

        channel.sendMessage(embed.build()).queue();
    }
}
