package units.progettosdm.graphicsclass;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class PlayerTurnSlider extends Label {
    public String playerName;

    public PlayerTurnSlider() {
    }

    public void setPlayerAndSlide(String text, Color color) {
        playerName = text;
        setText("Turno di " + text);
        setTextFill(color);
        slidingEffect();
    }

    private void slidingEffect() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), this);
        translateTransition.setFromY(-15);
        translateTransition.setToY(0);
        translateTransition.play();
    }
}
