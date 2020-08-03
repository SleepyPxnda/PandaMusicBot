package de.pxnda;

import de.pxnda.Logging.ConsoleLogger;
import de.pxnda.Logging.ILogger;
import de.pxnda.eventhandler.GuildVoiceEventHandler;
import de.pxnda.eventhandler.MessageEventHandler;
import de.pxnda.music.PlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<VoiceChannel> tempChannelList;
    public static PlayerManager playerManager;
    public static String prefix = "*";
    public static ILogger Logger;
    public static JDA jda;

    //ToDo: Replace all Messages with Embed

    public static void main(String[] args) {

        try {
            jda = new JDABuilder("NzM5MTAwNDQ4NjkwMTQzMjYy.XyVi7g.k2gyw6vBGnBBwph_PnitgzhGM-w")
                    .addEventListeners(new GuildVoiceEventHandler(), new MessageEventHandler())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        jda.getPresence().setPresence(Activity.listening("to *"), false);

        SavedSongStorage.globalPlaylistStorage.put("finns-dnd", SavedSongStorage.FinnsList);

        playerManager = new PlayerManager();
        tempChannelList = new ArrayList<>();

        System.out.println("Bot started with prefix " + prefix);
        System.out.println("Starting Gui ....");
        
        Logger = new ConsoleLogger();

    }
}
