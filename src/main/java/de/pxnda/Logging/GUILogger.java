package de.pxnda.Logging;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.HashMap;

public class GUILogger extends Application implements ILogger{

    private static ObservableList<String> serverChooserList = FXCollections.observableArrayList();
    private static HashMap<String, ObservableList<String>> serverHistory = new HashMap<>();

    Stage window;
    ListView<String> serverLogger;
    ChoiceBox<String> serverChooser;

    Color sceneBackground = Color.BLACK;


    @Override
    public void start(Stage stage)
    {
        window = stage;
        window.setTitle("PandaMusicLogger");
        serverLogger = new ListView<>();
        serverLogger.setBackground(Background.EMPTY);

        serverLogger.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ColorRectCell();
            }
        });

        serverChooser = new ChoiceBox<>();
        serverChooser.setItems(serverChooserList);
        serverChooserList.add("Select Server ...");
        serverChooser.setValue("Select Server ...");

        EventHandler<ActionEvent> event = event1 -> {
            if(serverChooser.getValue() != null){
                String value = serverChooser.getValue();
                if(serverHistory.containsKey(value)){
                    serverLogger.setItems(serverHistory.get(value));
                }
            }
        };

        serverChooser.setOnAction(event);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(serverLogger, serverChooser);
        layout.setBackground(Background.EMPTY);

        Scene scene = new Scene(layout, 1024, 576);

        scene.setFill(Color.valueOf("#3b3a38"));
        window.setScene(scene);
        window.show();
    }

    @Override
    public void log(String log) {
            String server =  log.split(" #-# ")[1];

            if(serverHistory.containsKey(server)){
                serverHistory.get(server).add(log.replace(" #-# ", " | "));
                System.out.println("Found Log, added Entry");
            }
            else
            {
                serverHistory.put(server, FXCollections.observableArrayList());
                serverHistory.get(server).add(log.replace(" #-# ", " | "));
                serverChooserList.add(server);
                System.out.println("New Server log registered, added Entry");
            }
    }

    static class ColorRectCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setStyle("-fx-background-color: #3b3a38; -fx-text-fill: #e6a015");
                setText(item);
            }
            else
            {
                setText(null);
                setStyle("-fx-background-color: #3b3a38; -fx-text-fill: white");
            }
        }
    }
}