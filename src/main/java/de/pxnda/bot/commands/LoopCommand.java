package de.pxnda.bot.commands;


import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import de.pxnda.bot.util.musichandlers.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LoopCommand implements ICommand {
    private final TextChannel textChannel;
    private final GuildMusicManager manager;

    public LoopCommand(MessageReceivedEvent event){
        this.textChannel = event.getTextChannel();
        Guild guild = event.getGuild();
        this.manager = BotApplication.playerManager.getGuildMusicManager(guild);
    }


    @Override
    public void execute() {
        if(manager.player.getPlayingTrack() == null){
            textChannel.sendMessage("Pls start a Song before trying to loop it").queue();
            return;
        }

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
