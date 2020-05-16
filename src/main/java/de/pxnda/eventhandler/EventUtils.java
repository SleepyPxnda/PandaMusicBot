package de.pxnda.eventhandler;

import de.pxnda.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;

public class EventUtils {
    public static void handleTempChannelBot(GuildVoiceUpdateEvent event, Guild guild) {

        //Falls das Event null ist, um Fehler zu verhindern da der Fehler beim testen paarmal kam
        if (event == null) return;

        //Falls die Evententity wirklich ein User oder Bot ist, also eine wirkliche Instanz ist
        if (event.getEntity().isFake()) return;

        if (event.getChannelLeft() != null) {
            //Setze die variable channelLeft auf den Channel welcher geleavet wurde
            VoiceChannel channelLeft = event.getChannelLeft();

            //Bugprevention falls der aus irgendeinem Grund null wird
            if (channelLeft == null) return;

            VoiceChannel currentChannel = guild.getSelfMember().getVoiceState().getChannel();

            if (currentChannel != null) {
                if (channelLeft.getIdLong() == currentChannel.getIdLong()) {
                    if (channelLeft.getMembers().size() == 1) {

                        guild.getAudioManager().closeAudioConnection();
                        Main.playerManager.getGuildMusicManager(guild).player.stopTrack();
                        Main.playerManager.getGuildMusicManager(guild).scheduler.getQueue().clear();
                    }
                }
            }
        }
    }
}
