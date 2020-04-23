package de.pxnda.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.BlockingQueue;

public class ForwardCommand implements ICommand {

    private final TextChannel channel;
    private final Guild guild;
    private final String numberToBeSkipped;
    private AudioTrack currentTrack;
    private final BlockingQueue<AudioTrack> trackQueue;

    public ForwardCommand(MessageReceivedEvent e, String arg) {
        this.channel = e.getTextChannel();
        this.guild = e.getGuild();
        this.numberToBeSkipped = arg;
        this.currentTrack = Main.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();
        this.trackQueue = Main.playerManager.getGuildMusicManager(guild).scheduler.getQueue();
    }

    @Override
    public void execute() {
        int number = Integer.parseInt(numberToBeSkipped);

        if(currentTrack != null) {
            if (number <= trackQueue.size()) {
                for (int i = 0; i < number; i++) {
                    currentTrack = Main.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();
                    channel.sendMessage("I skipped the current Track: " + currentTrack.getInfo().title).queue();
                    Main.playerManager.getGuildMusicManager(guild).scheduler.nextTrack();
                }
            } else {
                trackQueue.clear();
                channel.sendMessage("Since you wanted to skip more than the Queuelenght I cleared the queue for you").queue();
            }
        }
        else {
            channel.sendMessage("I can't forward to that non-existing Song ").queue();
        }
    }
}
