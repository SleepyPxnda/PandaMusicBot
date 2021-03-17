package de.pxnda.command;

import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import de.pxnda.music.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PauseCommand implements ICommand {
    private final Guild guild;
    private final GuildMusicManager manager;
    private final TextChannel channel;

    public PauseCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
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
            channel.sendMessage("I **paused** the Music for you").queue();
        }
    }
}
