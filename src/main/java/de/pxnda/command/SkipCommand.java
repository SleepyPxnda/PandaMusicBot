package de.pxnda.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SkipCommand implements ICommand {

    private TextChannel channel;
    private Guild guild;

    public SkipCommand(MessageReceivedEvent e) {
        this.channel = e.getTextChannel();
        this.guild = e.getGuild();
    }

    @Override
    public void execute() {
        AudioTrack currentTrack = Main.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();
        channel.sendMessage("Skipped Current Track: " + currentTrack.getInfo().title).queue();
        Main.playerManager.getGuildMusicManager(guild).scheduler.nextTrack();
    }
}
