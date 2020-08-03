package de.pxnda.command;

import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import de.pxnda.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LoopCommand implements ICommand {
    private final TextChannel textChannel;
    private final GuildMusicManager manager;

    public LoopCommand(MessageReceivedEvent event){
        this.textChannel = event.getTextChannel();
        Guild guild = event.getGuild();
        this.manager = Main.playerManager.getGuildMusicManager(guild);
    }


    @Override
    public void execute() {
        if(!manager.scheduler.isLooping()){
            textChannel.sendMessage("Looping **enabled**! Song: " + manager.player.getPlayingTrack().getInfo().title).queue();
            manager.scheduler.setLooping(true);
        }
        else
        {
            textChannel.sendMessage("Looping **disabled**! Continuing with the Queue").queue();
            manager.scheduler.setLooping(false);
        }
    }
}
