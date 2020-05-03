package de.pxnda.Utils;

import com.iwebpp.crypto.TweetNaclFast;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Random;

public class CustomLogger extends Application {

    private static ObservableList<String> data = FXCollections.observableArrayList();
    private static HashMap<String, Color> serverColor = new HashMap<>();


    @Override
    public void start(Stage stage)
    {
        stage.setTitle("PandaMusicLogger");

        ListView<String> listView = new ListView<>();
        listView.setItems(data);

        StackPane root = new StackPane();
        root.getChildren().add(listView);
        stage.setScene(new Scene(root, 300, 400));
        stage.show();

        listView.setCellFactory(param -> new ColoredCell(randomColor()));
    }

    static class ColoredCell extends ListCell<String> {

        private Color currentColor;

        public ColoredCell(Color color){
            currentColor = color;
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                String server = item.split(" - ")[1];
                Color textColor;

                if(serverColor.containsKey(server)){
                    textColor = serverColor.get(server);
                }
                else
                {
                    serverColor.put(server, currentColor);
                    textColor = currentColor;
                }
                this.setText(item);
                this.setTextFill(textColor);
            }
        }
    }

    public static void addEntry(String entry) {
        data.add(entry);
    }

    public Color randomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return Color.rgb(r, g, b);
    }
}