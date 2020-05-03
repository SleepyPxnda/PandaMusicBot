package de.pxnda.Utils;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;

public class CustomLogger extends Application {

    private static ObservableList<String> serverChooserList = FXCollections.observableArrayList();
    private static HashMap<String, ObservableList<String>> serverHistory = new HashMap<>();

    Stage window;
    ListView<String> serverLogger;
    ChoiceBox<String> serverChooser;


    @Override
    public void start(Stage stage)
    {
        window = stage;
        window.setTitle("PandaMusicLogger");
        serverLogger = new ListView<>();

        serverChooser = new ChoiceBox<>();
        serverChooser.setItems(serverChooserList);
        serverChooserList.add("Select Server ...");
        serverChooser.setValue("Select Server ...");

        EventHandler<ActionEvent> event = event1 -> {
            if(serverChooser.getValue() != null){
                String value = serverChooser.getValue();
                System.out.println(value);
                System.out.println(serverHistory.size());
                if(serverHistory.containsKey(value)){
                    serverLogger.setItems(serverHistory.get(value));
                }
            }
        };

        serverChooser.setOnAction(event);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(serverLogger, serverChooser);

        Scene scene = new Scene(layout, 1024, 576);

        window.setScene(scene);
        window.show();
    }

    public static void addEntry(String entry) {
        String server =  entry.split(" #-# ")[1];

        if(serverHistory.containsKey(server)){
            serverHistory.get(server).add(entry.replace(" #-# ", " | "));
            System.out.println("Found Log, added Entry");
        }
        else
        {
            serverHistory.put(server, FXCollections.observableArrayList());
            serverHistory.get(server).add(entry.replace(" #-# ", " | "));
            serverChooserList.add(server);
            System.out.println("New Server log registered, added Entry");
        }

    }
}