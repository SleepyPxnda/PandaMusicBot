package de.pxnda;

import de.pxnda.Utils.CommandExecutor;
import de.pxnda.Utils.CustomLogger;
import de.pxnda.command.*;
import de.pxnda.eventhandler.EventHandlers;
import de.pxnda.music.PlayerManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Timestamp;
import java.util.*;

public class Main {

    public static List<VoiceChannel> tempChannelList;
    public static PlayerManager playerManager;
    public static String prefix = "*";

    //ToDo: Replace all Messages with Embed

    public static void main(String[] args) {

        System.out.println("Systemargs:" + args);

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
