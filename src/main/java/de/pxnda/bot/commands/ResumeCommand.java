package de.pxnda.bot.commands;


import de.pxnda.bot.BotApplication;;
import de.pxnda.bot.util.commands.ICommand;
import de.pxnda.bot.util.musichandlers.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ResumeCommand implements ICommand {

    private final Guild guild;
    private final GuildMusicManager manager;
    private final TextChannel channel;

    public ResumeCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        this.manager = BotApplication.playerManager.getGuildMusicManager(guild);
        this.channel = e.getTextChannel();
    }

    @Override
    public void execute() {
        if(manager.player.isPaused()){
            manager.player.setPaused(false);
            channel.sendMessage("I **resumed** to play " + manager.player.getPlayingTrack().getInfo().title).queue();
        }
        else
        {
            channel.sendMessage("I'm not paused").queue();
        }
    }
}
