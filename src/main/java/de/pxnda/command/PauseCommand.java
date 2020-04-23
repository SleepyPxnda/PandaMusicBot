package de.pxnda.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import de.pxnda.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PauseCommand implements ICommand {
    private AudioTrack currentTrack;
    private Guild guild;
    private GuildMusicManager manager;
    private TextChannel channel;

    public PauseCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        this.currentTrack = Main.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();
        this.manager = Main.playerManager.getGuildMusicManager(guild);
        this.channel = e.getTextChannel();
    }

    @Override
    public void execute() {
        if(manager.player.isPaused()){
            channel.sendMessage("I'm already paused").queue();
        }
        else
        {
            manager.player.setPaused(true);
        }
    }
}
