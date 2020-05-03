package de.pxnda;

import de.pxnda.Utils.CustomLogger;
import de.pxnda.eventhandler.EventHandlers;
import de.pxnda.music.PlayerManager;
import javafx.application.Application;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<VoiceChannel> tempChannelList;
    public static PlayerManager playerManager;
    public static String prefix = "*";

    //ToDo: Replace all Messages with Embed

    public static void main(String[] args) {

        try {
            new JDABuilder("Njk1MzQ3NTI0ODM1MzQ0NDA0.XpMmlw.ck4FgKN8jYsVaxjiwWTe5iQ-Ewg").addEventListeners(new EventHandlers()).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerManager = new PlayerManager();
        tempChannelList = new ArrayList<>();

        System.out.println("Bot started with prefix " + prefix);
        System.out.println("Starting Gui ....");

        Application.launch(CustomLogger.class, args);
    }



}
