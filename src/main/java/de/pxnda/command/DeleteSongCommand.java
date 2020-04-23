package de.pxnda.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class DeleteSongCommand implements ICommand {

    private TextChannel channel;
    private Guild guild;
    private String numberToBeSkipped;
    private BlockingQueue<AudioTrack> trackQueue;

    public DeleteSongCommand(MessageReceivedEvent e, String s) {
        this.channel = e.getTextChannel();
        this.guild = e.getGuild();
        this.numberToBeSkipped = s;
        this.trackQueue = Main.playerManager.getGuildMusicManager(guild).scheduler.getQueue();
    }

    @Override
    public void execute() {
        int number = Integer.parseInt(numberToBeSkipped) - 2;
        int index = number - 2;

        if(index > trackQueue.size()) {
            channel.sendMessage("That Index is not used in the Queue").queue();
            return;
        }

        if(index < 0){
            channel.sendMessage("Number is under 0").queue();
            return;
        }

        List<AudioTrack> trackQueueCopy = new ArrayList<>(trackQueue);
        AudioTrack tobeDeleted = trackQueueCopy.get(index);

        if(tobeDeleted != null){
            Boolean success = trackQueue.remove(tobeDeleted);

            if(success){
                channel.sendMessage("Deleted: " + tobeDeleted.getInfo().title + " from Queue at position " + number).queue();
            }else
            {
                channel.sendMessage("Something went wrong deleting Song at Number: " + number + " Index:" + index).queue();
            }
        }
    }
}
