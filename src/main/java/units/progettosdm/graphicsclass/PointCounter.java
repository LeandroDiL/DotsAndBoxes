package units.progettosdm.graphicsclass;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class PointCounter extends HBox {
    private int points;
    private final Label point;

    public PointCounter(double height, double width, double layoutX, double layoutY, String player, Color color) {
        super();
        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
        this.setPrefWidth(width);
        this.setPrefHeight(height);

        this.points = 0;

        Label name = new Label();
        setLabelParameters(name, color, 0.75, player, 15);

        point = new Label();
        setLabelParameters(point, color, 0.25, Integer.toString(points), 12);

        this.getChildren().add(name);
        this.getChildren().add(point);

    }

    private void setLabelParameters(Label labelToSet, Color color, double widthPercentage, String textInsideLabel, int fontSize) {
        labelToSet.setPrefHeight(this.getPrefHeight());
        labelToSet.setPrefWidth(this.getPrefWidth() * widthPercentage);
        labelToSet.setBackground(new Background(new BackgroundFill(color, new CornerRadii(0.5), new Insets(0))));
        labelToSet.setAlignment(Pos.CENTER);
        labelToSet.setText(textInsideLabel);
        labelToSet.setFont(new Font("", fontSize));
    }

    public void setPoint(int score) {
        if (score != points) {
            points = score;
            point.setText(Integer.toString(points));
            slidingEffect();
        }
    }

    private void slidingEffect() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), point);
        translateTransition.setFromY(-15);
        translateTransition.setToY(0);
        translateTransition.play();
    }
}
