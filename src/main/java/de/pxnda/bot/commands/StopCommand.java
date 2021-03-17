package de.pxnda.bot.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import de.pxnda.bot.util.musichandlers.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StopCommand implements ICommand {

    private final Guild guild;
    private final TextChannel textChannel;
    private final GuildMusicManager manager;

    public StopCommand(MessageReceivedEvent e){
        this.guild = e.getGuild();
        textChannel = e.getTextChannel();
        this.manager = BotApplication.playerManager.getGuildMusicManager(guild);
    }

    @Override
    public void execute() {
        if(manager.player.getPlayingTrack() != null){
            AudioTrack currentTrack = BotApplication.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();
            manager.player.stopTrack();
            textChannel.sendMessage("\uD83D\uDED1 _" + currentTrack.getInfo().title + "_ ").queue();
        }
    }
}
