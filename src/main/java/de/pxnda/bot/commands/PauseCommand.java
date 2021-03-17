package de.pxnda.bot.commands;


import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import de.pxnda.bot.util.musichandlers.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PauseCommand implements ICommand {
    private final Guild guild;
    private final GuildMusicManager manager;
    private final TextChannel channel;

    public PauseCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        this.manager = BotApplication.playerManager.getGuildMusicManager(guild);
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
