package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    private List<Boolean> positionList = new ArrayList<>();

    private int placeOfButton;
    private int points;
    private int counter;
    private int lastIndex;

    private HBox hbox = new HBox();
    private Random generator = new Random();

    private Button quitButton = new Button("QUIT");
    private Button newGameButton = new Button("NEW GAME");

    private List<Button> buttonList = new ArrayList<>();

    private GridPane grid = new GridPane();

    private Label labelPointValue = new Label();
    private Label labelPointText = new Label();


    @Override
    public void start(Stage primaryStage) throws Exception {
       for (int i = 0; i <=12 ; i++) {
           positionList.add(i,false);
       }
        hbox.getChildren().addAll(labelPointText, labelPointValue);
        int id;
        for (int i = 1; i <= 12; i++) {
            Button button = new Button("");
            button.setPrefHeight(130);
            button.setPrefWidth(130);
            id = i <= 6 ? i : i - 6;
            button.setId(String.valueOf(id));
            buttonList.add(button);
            button.setOnAction(event -> {
                        if (button.getGraphic() == null) {
                            Button clickedButton = (Button) event.getSource();
                            counter++;
                            Image image = new Image(getClass().getResourceAsStream("/" +
                                    clickedButton.getId() + ".png"));
                            ImageView imageView = new ImageView();
                            imageView.setImage(image);
                            imageView.setFitWidth(110);
                            imageView.setFitHeight(110);
                            clickedButton.setGraphic(imageView);
                            if (counter % 2 == 0) {
                                if (buttonList.get(lastIndex).getId().equals(clickedButton.getId())) {
                                    buttonList.get(lastIndex).setDisable(true);
                                    clickedButton.setDisable(true);
                                    points++;
                                    labelPointValue.setText(String.valueOf(points));
                                } else {
                                    buttonList.get(lastIndex).setGraphic(null);
                                    counter = 1;
                                }
                            }
                            lastIndex = buttonList.indexOf(clickedButton);
                        }
                    }
            );

            while(true){
                placeOfButton = generator.nextInt(12);//Random placement of elements
                if(positionList.get(placeOfButton) == false){
                    positionList.remove(placeOfButton);
                    positionList.add(placeOfButton,true);
                    if (placeOfButton < 6) {
                        grid.add(button, placeOfButton, 1);
                    }
                    else {
                        grid.add(button, placeOfButton - 6, 2);
                    }
                    break;
                }
                placeOfButton = generator.nextInt(12);
        }
        }

        initialization(primaryStage);

        quitButton.setOnAction(actionEvent -> {
            quit(primaryStage);
        });
        newGameButton.setOnAction(actionEvent -> {
            reset();
        });
    }
    public void initialization(Stage primaryStage) {
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 7, 0, 3));

        labelPointText.setText("POINTS: ");
        labelPointText.setFont(new Font("Helvetica", 25));
        labelPointText.setMinSize(30, 50);
        labelPointValue.setText(String.valueOf(points));
        labelPointValue.setFont(new Font("Helvetica", 25));
        labelPointValue.setMinSize(30, 50);

        quitButton.setPrefHeight(50);
        quitButton.setPrefWidth(100);
        newGameButton.setPrefHeight(50);
        newGameButton.setPrefWidth(100);

        grid.add(quitButton, 7, 1);
        grid.add(newGameButton, 7, 2);
        grid.add(hbox, 2, 0);
        Scene scene = new Scene(grid, 960, 380);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();
    }


    public void reset() {//Game reset
        for (Button button : buttonList) {
            button.setGraphic(null);
            button.setDisable(false);
            points = 0;
            labelPointValue.setText(String.valueOf(points));
        }
    }
    public void quit(Stage primaryStage) {
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

