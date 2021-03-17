package de.pxnda.bot.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SkipCommand implements ICommand {

    private final TextChannel channel;
    private final Guild guild;

    public SkipCommand(MessageReceivedEvent e) {
        this.channel = e.getTextChannel();
        this.guild = e.getGuild();
    }

    @Override
    public void execute() {
        AudioTrack currentTrack = BotApplication.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();

        if(currentTrack != null) {
            channel.sendMessage("\uD83D\uDD34 _" + currentTrack.getInfo().title + "_ ").queue();
            BotApplication.playerManager.getGuildMusicManager(guild).scheduler.nextTrack();
        }
        else
        {
            channel.sendMessage("Im empty").queue();
        }

    }
}
