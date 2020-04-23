package de.pxnda.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import de.pxnda.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ResumeCommand implements ICommand {

    private AudioTrack currentTrack;
    private Guild guild;
    private GuildMusicManager manager;
    private TextChannel channel;

    public ResumeCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        this.currentTrack = Main.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();
        this.manager = Main.playerManager.getGuildMusicManager(guild);
        this.channel = e.getTextChannel();
    }

    @Override
    public void execute() {
        if(manager.player.isPaused()){
            manager.player.setPaused(false);
            channel.sendMessage("I resumed to play " + manager.player.getPlayingTrack().getInfo().title).queue();
        }
        else
        {
            channel.sendMessage("I'm not paused").queue();
        }
    }
}
