package de.pxnda.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import de.pxnda.music.GuildMusicManager;
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
        this.manager = Main.playerManager.getGuildMusicManager(guild);
    }

    @Override
    public void execute() {
        if(manager.player.getPlayingTrack() != null){
            AudioTrack currentTrack = Main.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();
            manager.player.stopTrack();
            textChannel.sendMessage("\uD83D\uDED1 _" + currentTrack.getInfo().title + "_ ").queue();
        }
    }
}
