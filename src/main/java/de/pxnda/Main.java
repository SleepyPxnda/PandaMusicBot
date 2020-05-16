package de.pxnda;

import de.pxnda.Logging.ConsoleLogger;
import de.pxnda.Logging.GUILogger;
import de.pxnda.Logging.ILogger;
import de.pxnda.Utils.SavedSongStorage;
import de.pxnda.eventhandler.GuildVoiceEventHandler;
import de.pxnda.eventhandler.MessageEventHandler;
import de.pxnda.eventhandler.MessageReactEventHandler;
import de.pxnda.music.PlayerManager;
import javafx.application.Application;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.Arrays;
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
            jda = new JDABuilder("Njk1MzQ3NTI0ODM1MzQ0NDA0.XpMmlw.ck4FgKN8jYsVaxjiwWTe5iQ-Ewg")
                    .addEventListeners(new GuildVoiceEventHandler(), new MessageEventHandler(), new MessageReactEventHandler())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SavedSongStorage.globalPlaylistStorage.put("finns-dnd", SavedSongStorage.FinnsList);

        playerManager = new PlayerManager();
        tempChannelList = new ArrayList<>();

        System.out.println("Bot started with prefix " + prefix);
        System.out.println("Starting Gui ....");

        List<String> argsList = Arrays.asList(args);
        
        if(argsList.contains("gui")){
            Logger = new GUILogger();
            Application.launch(GUILogger.class, args);
        }
        else
        {
            Logger = new ConsoleLogger();
        }
    }
}
