package de.pxnda.command;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.pxnda.Main;
import de.pxnda.Utils.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.BlockingQueue;

import static de.pxnda.Utils.UtilFuncs.convertToTimeStamp;

public class QueueCommand implements ICommand {
    private final Guild guild;
    private final TextChannel channel;

    public QueueCommand(MessageReceivedEvent e) {
        this.guild = e.getGuild();
        this.channel = e.getTextChannel();
    }

    //ToDo: ADjust Structure of this command, quite a mess
    @Override
    public void execute() {
        BlockingQueue<AudioTrack> trackQueue = Main.playerManager.getGuildMusicManager(guild).scheduler.getQueue();
        AudioTrack currentTrack = Main.playerManager.getGuildMusicManager(guild).player.getPlayingTrack();

        int timeNeededbyTracksBefore  = 0;
        EmbedBuilder embed = new EmbedBuilder();
        timeNeededbyTracksBefore += (currentTrack.getDuration() - currentTrack.getPosition());


        int maxQueueShown = 25;
        int i = 0;

        if(currentTrack != null){
            i = 1;
            embed.addField("-------------------------", "Currently Playing", false);
            embed.addField( i + ": " +currentTrack.getInfo().title, currentTrack.getInfo().uri, false);
            embed.addField("-------------------------", "Following", false);
        }


        if(trackQueue.size() > 0){

            int maxlength = Math.min(trackQueue.size(), maxQueueShown);



            for (AudioTrack track : trackQueue) {
                if(i > maxlength){
                    break;
                }
                i++;
                embed.addField((i) + ": " + track.getInfo().title + " [ in " + convertToTimeStamp(timeNeededbyTracksBefore) + " ]", track.getInfo().uri, false);
                timeNeededbyTracksBefore += track.getDuration();

            }
        }

        if(trackQueue.size() == 0){
            embed.addField("Nothing to play", "I fell empty - Queue", false);
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
