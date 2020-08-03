package de.pxnda.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import de.pxnda.music.GuildMusicManager;
import de.pxnda.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.BlockingQueue;

import static de.pxnda.Utils.UtilFuncs.convertToTimeStamp;

public class QueueCommand implements ICommand {
    private final TextChannel channel;
    private final GuildMusicManager manager;

    public QueueCommand(MessageReceivedEvent e) {
        Guild guild = e.getGuild();
        this.channel = e.getTextChannel();
        this.manager = Main.playerManager.getGuildMusicManager(guild);
    }

    //ToDo: ADjust Structure of this command, quite a mess
    @Override
    public void execute() {
        BlockingQueue<AudioTrack> trackQueue = manager.scheduler.getQueue();
        AudioTrack currentTrack = manager.player.getPlayingTrack();

        int timeNeededbyTracksBefore  = 0;
        EmbedBuilder embed = new EmbedBuilder();

        int maxQueueShown = 25;
        int i = 0;
        String looped = manager.scheduler.isLooping() ? " **Looping**" : " ";

        if(currentTrack != null){
            timeNeededbyTracksBefore += (currentTrack.getDuration() - currentTrack.getPosition());
            embed.addField("-------------------------",
                    "Current Song - [ " + convertToTimeStamp(timeNeededbyTracksBefore) + " ]" + looped, false);
            embed.addField(currentTrack.getInfo().title, currentTrack.getInfo().uri, false);
            embed.addField("-------------------------", "Following", false);
        }


        if(trackQueue.size() > 0){

            int maxlength = Math.min(trackQueue.size(), maxQueueShown);
            for (AudioTrack track : trackQueue) {
                if(i >= maxlength){
                    break;
                }
                i++;
                embed.addField((i) + ": " + track.getInfo().title + " [ " + convertToTimeStamp(timeNeededbyTracksBefore) + " ]", track.getInfo().uri, true);
                timeNeededbyTracksBefore += track.getDuration();
            }
        }

        if(trackQueue.size() == 0){
            embed.addField("Nothing to play", "I feel empty - 'Queue'", false);
        }

        //Footer Handler
        if(trackQueue.size() > maxQueueShown){
            embed.setFooter("Time until silence: " + convertToTimeStamp(timeNeededbyTracksBefore) + " | " + (trackQueue.size() - maxQueueShown) + " Songs not listed");
        }
        else
        {
            embed.setFooter("Time until silence: " + convertToTimeStamp(timeNeededbyTracksBefore));
        }

        channel.sendMessage(embed.build()).queue();
    }


}
