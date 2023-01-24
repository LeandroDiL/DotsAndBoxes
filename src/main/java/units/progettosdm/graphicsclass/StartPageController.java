package units.progettosdm.graphicsclass;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.regex.Pattern;


import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadBoardSizeDeclarationException;


public class StartPageController {
    @FXML
    Pane mainPane;
    @FXML
    TextField textFieldNamePlayer1;
    @FXML
    Label labelErrorPlayer1;
    @FXML
    TextField textFieldNamePlayer2;
    @FXML
    Label labelErrorPlayer2;
    @FXML
    TextField textFieldNumberOfColumn;
    @FXML
    TextField textFieldNumberOfRows;
    @FXML
    Label labelErrorGridSize;

    Stage stage;

    @FXML
    protected void onStartGameButtonClick(ActionEvent event) throws IOException {
        if (validateTextField() & validateGridSizeInput()) {
            FXMLLoader fxmlLoader = new FXMLLoader(StartPageController.class.getResource("gamePage.fxml"));
            int n, m;

            n = Integer.parseInt(textFieldNumberOfColumn.getText());
            m = Integer.parseInt(textFieldNumberOfRows.getText());
            Scene scene;
            try {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(StartPageController.class.getResource("styleGamePage.css") + "");
                stage.setScene(scene);
                stage.show();
                GamePageController controller = fxmlLoader.getController();
                stage.setScene(scene);
                controller.initializeGame(n, m, textFieldNamePlayer1.getText(), textFieldNamePlayer2.getText());
            } catch (BadBoardSizeDeclarationException e) {
                setErrorStyleAndErrorText(textFieldNumberOfColumn, labelErrorGridSize, "La dimensione della griglia è minore di due");
                setErrorStyleAndErrorText(textFieldNumberOfRows, labelErrorGridSize, "La dimensione della griglia è minore di due");
                scene = mainPane.getScene();
                stage.setScene(scene);
            } catch (BadArchDeclarationException e) {
                e.printStackTrace();
            }
            stage.show();
        }
    }

    private boolean validateTextField() {
        boolean response;
        if (containErrorStyle(textFieldNamePlayer1)) {
            removeErrorStyleAndSetErrorLabelEmpty(textFieldNamePlayer1, labelErrorPlayer1);
        }
        if (containErrorStyle(textFieldNamePlayer2)) {
            removeErrorStyleAndSetErrorLabelEmpty(textFieldNamePlayer2, labelErrorPlayer2);
        }

        response = TextFieldRespectInputConstrain(textFieldNamePlayer1, labelErrorPlayer1) & TextFieldRespectInputConstrain(textFieldNamePlayer2, labelErrorPlayer2);

        if (!textInTextFieldIsEqual(textFieldNamePlayer1, "") && textInTextFieldIsEqual(textFieldNamePlayer1, textFieldNamePlayer2.getText())) {
            setErrorStyleAndErrorText(textFieldNamePlayer1, labelErrorPlayer1, "Inserisci un nome diverso dall'altro giocatore");
            setErrorStyleAndErrorText(textFieldNamePlayer2, labelErrorPlayer2, "Inserisci un nome diverso dall'altro giocatore");

            response = false;
        }


        return response;
    }

    private boolean TextFieldRespectInputConstrain(TextField namePlayer, Label errorPlayer) {
        boolean response = true;
        if (textInTextFieldIsEqual(namePlayer, "")) {
            setErrorStyleAndErrorText(namePlayer, errorPlayer, "Inserisci un nome");
            response = false;
        } else if ((!namePlayer.getText().matches("[a-zA-Z]+")) && (!namePlayer.getText().equals(""))) {
            setErrorStyleAndErrorText(namePlayer, errorPlayer, "Il nome può contenere solo lettere");
            response = false;
        }

        if (namePlayer.getText().length() >= 10) {
            setErrorStyleAndErrorText(namePlayer, errorPlayer, "Nome troppo lungo");
            response = false;
        }
        return response;
    }

    private boolean validateGridSizeInput() {
        boolean response;
        int maxSize = 20;
        if (containErrorStyle(textFieldNumberOfColumn)) {
            removeErrorStyleAndSetErrorLabelEmpty(textFieldNumberOfColumn, labelErrorGridSize);
        }
        if (containErrorStyle(textFieldNumberOfRows)) {
            removeErrorStyleAndSetErrorLabelEmpty(textFieldNumberOfRows, labelErrorGridSize);
        }

        response = GridSizeInputRespectConstrain(maxSize, textFieldNumberOfColumn) & GridSizeInputRespectConstrain(maxSize, textFieldNumberOfRows);


        return response;
    }

    private boolean GridSizeInputRespectConstrain(int maxSize, TextField textFieldSize) {
        boolean response = true;
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (textFieldSize.getText().equals("")) {
            setErrorStyleAndErrorText(textFieldSize, labelErrorGridSize, "Inserisci la dimensione della griglia");
            response = false;
        } else if (!pattern.matcher(textFieldSize.getText()).matches()) {
            setErrorStyleAndErrorText(textFieldSize, labelErrorGridSize, "Il valore inserito non è un numero");
            response = false;
        } else {
            if (Integer.parseInt(textFieldSize.getText()) > maxSize) {
                setErrorStyleAndErrorText(textFieldSize, labelErrorGridSize, "Il valore inserito è maggiore di " + maxSize);
                response = false;
            }
        }
        return response;
    }

    private boolean textInTextFieldIsEqual(TextField namePlayer1, String s) {
        return namePlayer1.getText().equals(s);
    }

    private void removeErrorStyleAndSetErrorLabelEmpty(TextField namePlayer, Label errorPlayer) {
        namePlayer.getStyleClass().removeAll(Collections.singleton("error"));
        errorPlayer.setText("");
    }

    private void setErrorStyleAndErrorText(TextField namePlayer, Label errorPlayer, String text) {
        namePlayer.getStyleClass().add("error");
        errorPlayer.setText(text);
    }

    private boolean containErrorStyle(TextField namePlayer1) {
        return namePlayer1.getStyleClass().contains("error");
    }


    @FXML
    void initialize() {
        Image img = new Image(String.valueOf(StartPageController.class.getResource("sfondoCarta1.jpg")));
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        mainPane.setBackground(bGround);
    }
}

