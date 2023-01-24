package units.progettosdm.graphicsclass;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import units.progettosdm.backendclass.Box;

public class GraphicalBoxLabel extends Label {
    private final Box backendBox;
    private final String player1Token;
    private final String player2Token;


    public GraphicalBoxLabel(Circle[] dots, Box box, String player1Token, String player2Token) {

        super();
        this.backendBox = box;
        this.player1Token = player1Token;
        this.player2Token = player2Token;
        this.setLayoutX(dots[0].getCenterX());
        this.setLayoutY(dots[0].getCenterY());
        this.setPrefWidth(dots[1].getCenterX() - dots[0].getCenterX());
        this.setPrefHeight(dots[1].getCenterY() - dots[0].getCenterY());
        this.setMaxWidth(dots[1].getCenterX() - dots[0].getCenterX());
        this.setMaxHeight(dots[1].getCenterY() - dots[0].getCenterY());
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("boxes");
    }

    public void setBoxSelected() {
        if (backendBox.checkClosedBox()) {
            if (backendBox.getBoxCharacter().equals(player1Token)) {
                this.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 255, 0.5), new CornerRadii(0.5), new Insets(0.0))));
                this.setTextFill(Color.rgb(0, 0, 255, 1));
            } else if (backendBox.getBoxCharacter().equals(player2Token)) {
                this.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0, 0.5), new CornerRadii(0.5), new Insets(0.0))));
                this.setTextFill(Color.rgb(255, 0, 0, 1));
            }
            this.setText(backendBox.getBoxCharacter());
            this.setStyle("-fx-font-size:" + getHeight() * 0.7);
        }
    }
}