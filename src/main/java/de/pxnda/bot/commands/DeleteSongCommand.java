package de.pxnda.bot.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.bot.BotApplication;
import de.pxnda.bot.util.commands.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class DeleteSongCommand implements ICommand {

    private final TextChannel channel;
    private final Guild guild;
    private final String numberToBeSkipped;
    private final BlockingQueue<AudioTrack> trackQueue;

    public DeleteSongCommand(MessageReceivedEvent e, String s) {
        this.channel = e.getTextChannel();
        this.guild = e.getGuild();
        this.numberToBeSkipped = s;
        this.trackQueue = BotApplication.playerManager.getGuildMusicManager(guild).scheduler.getQueue();
    }

    @Override
    public void execute() {
        int number = Integer.parseInt(numberToBeSkipped);
        int index = number - 1;

        if(index > trackQueue.size()) {
            channel.sendMessage("My Queue doesnt contain anything at that Index").queue();
            return;
        }

        if(index < 0){
            channel.sendMessage("I'm sorry but that number is too small :D").queue();
            return;
        }

        List<AudioTrack> trackQueueCopy = new ArrayList<>(trackQueue);
        AudioTrack tobeDeleted = trackQueueCopy.get(index);

        if(tobeDeleted != null){
            Boolean success = trackQueue.remove(tobeDeleted);

            if(success){
                channel.sendMessage(" I **deleted** " + tobeDeleted.getInfo().title + " from my Queue at position " + number).queue();
            }else
            {
                channel.sendMessage("Something went wrong while I tried to delete Song at Number: " + number + " Index:" + index).queue();
            }
        }
    }
}
