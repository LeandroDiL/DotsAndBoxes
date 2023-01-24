package units.progettosdm.graphicsclass;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import units.progettosdm.backendclass.*;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadBoardSizeDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.io.IOException;
import java.util.*;


public class GamePageController {

    public final Color player1BackgroundColor = Color.rgb(0, 0, 255, 0.5);
    public final Color player2BackgroundColor = Color.rgb(255, 0, 0, 0.5);
    public final Color player1TextColor = Color.rgb(0, 0, 255, 1);
    public final Color player2TextColor = Color.rgb(255, 0, 0, 1);

    @FXML
    private Pane allWindowPane;
    @FXML
    private Pane gameViewPane;
    @FXML
    private Button exitButton;
    @FXML
    private Button backwardsButton;
    @FXML
    private Pane scoreboardAndLabelsPane;


    private PlayerTurnSlider nameOfPlayerThatPlayTheTurn;
    private PointCounter totalPointsOfPlayer1;
    private PointCounter totalPointsOfPlayer2;

    List<GraphicalArch> listOfGraphicalArches = new ArrayList<>();
    Map<Dot, Circle> mapOfDotsAndGraphicalDots = new HashMap<>();
    List<GraphicalBoxLabel> listOfLabels = new ArrayList<>();

    Group dotsGroup = new Group();
    Group archesGroup = new Group();
    Group labelsGroup = new Group();

    String player1Name;
    final String player1Token = "B";

    String player2Name;
    final String player2Token = "R";

    private double widthTablePane;
    private double heightTablePane;

    private Game actualMatch;

    int columnSize;
    int rowSize;

    @FXML
    void initialize() {
        Image img = new Image(String.valueOf(StartPageController.class.getResource("sfondoCarta1.jpg")));
        BackgroundImage bImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        allWindowPane.setBackground(bGround);
    }


    public void initializeGame(int n, int m, String player1, String player2) throws BadBoardSizeDeclarationException, BadArchDeclarationException {
        actualMatch = new Game(n, m, player1, player2);
        columnSize = actualMatch.getScoreboardSize()[0];
        rowSize = actualMatch.getScoreboardSize()[1];
        Stage stage = (Stage) allWindowPane.getScene().getWindow();
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setFullScreen(true);

        this.player1Name = player1;
        this.player2Name = player2;

        try {
            initializeDotArchesAndLabels();
        } catch (BadDotDeclarationException e) {
            System.out.println(e.getMessage());
        }

        changePlayerNameOnTopLabel();


    }

    public void initializeDotArchesAndLabels() throws BadDotDeclarationException {
        drawGameField();
        createNxMGridOfGraphicalDots();
        createGraphicalArchesFromBackendArches();
        createGraphicalLabelsFromBackendBoxes();

        drawListOfNodeObjectAndInsertIntoAGroupTheList(listOfLabels, labelsGroup);
        drawListOfNodeObjectAndInsertIntoAGroupTheList(mapOfDotsAndGraphicalDots.values().stream().toList(), dotsGroup);
        drawListOfNodeObjectAndInsertIntoAGroupTheList(listOfGraphicalArches, archesGroup);

        setClickLineListenerOnArches();
        setMouseHoverArchesListener();

    }

    private void createGraphicalArchesFromBackendArches() {
        List<Arch> arches = actualMatch.getScoreboard().totalArches;
        listOfGraphicalArches = new ArrayList<>();
        arches.forEach(e -> listOfGraphicalArches.add(new GraphicalArch(mapOfDotsAndGraphicalDots.get(e.getFirstDot()), mapOfDotsAndGraphicalDots.get(e.getSecondDot()), e)));

        listOfGraphicalArches.forEach(e -> {
            if (e.isSelected()) {
                e.setOpacity(1);
            } else {
                e.setOpacity(0.2);
            }
        });
    }

    private void createNxMGridOfGraphicalDots() {
        double max = Math.max(columnSize, rowSize);
        double border = widthTablePane * 0.05;
        double distanceX = (widthTablePane - border * 2) / (max);
        double distanceY = (heightTablePane - border * 2) / (max);
        double circleSize = distanceX / 20;

        Scoreboard score = actualMatch.getScoreboard();
        Dot[][] dots = score.getDots();

        for (int i = 0; i < columnSize + 1; i++) {
            for (int j = 0; j < rowSize + 1; j++) {
                double tempX = border + (distanceX * i);
                double tempY = border + (distanceY * j);
                mapOfDotsAndGraphicalDots.put(dots[i][j], new Circle(tempX, tempY, circleSize));
            }
        }
        if (columnSize < rowSize) {
            mapOfDotsAndGraphicalDots.values().forEach(circle -> circle.setCenterX(circle.getCenterX() + widthTablePane / 2 - (columnSize + 1) * distanceX / 2));
        } else if (rowSize < columnSize) {
            mapOfDotsAndGraphicalDots.values().forEach(circle -> circle.setCenterY(circle.getCenterY() + heightTablePane / 2 - (rowSize + 1) * distanceY / 2));
        }
    }

    private void createGraphicalLabelsFromBackendBoxes() {
        listOfLabels = new ArrayList<>();
        Box[][] boxes = actualMatch.getScoreboard().getBoxes();
        Arrays.stream(boxes).forEach(line -> Arrays.stream(line).forEach(cell -> {
            Circle firstDot = mapOfDotsAndGraphicalDots.get(cell.getBoxVertexes()[0]);
            Circle thirdDot = mapOfDotsAndGraphicalDots.get(cell.getBoxVertexes()[2]);
            Circle[] firstAndThirdGraphicalDots = {firstDot, thirdDot};
            listOfLabels.add(new GraphicalBoxLabel(firstAndThirdGraphicalDots, cell, player1Token, player2Token));
        }));
    }

    private void drawGameField() {

        scoreboardAndLabelsPane.getChildren().clear();
        gameViewPane.getChildren().clear();

        double parentWidth = allWindowPane.getWidth();
        double parentHeight = allWindowPane.getHeight();

        double dimension = Math.min(parentHeight, parentWidth);


        widthTablePane = dimension * 0.8;
        heightTablePane = dimension * 0.8;
        double widthGameViewPane = dimension * 0.9;
        double heightGameViewPane = dimension * 0.9;


        gameViewPane.setPrefSize(widthGameViewPane, heightGameViewPane);
        gameViewPane.setLayoutX(parentWidth / 2 - widthGameViewPane / 2);
        gameViewPane.setLayoutY(parentHeight / 2 - heightGameViewPane / 2);

        exitButton.setPrefHeight(17);
        exitButton.setPrefWidth(widthGameViewPane / 3);
        exitButton.setLayoutX(parentWidth - exitButton.getPrefWidth());
        exitButton.setLayoutY(parentHeight - parentHeight * 0.05 - 17);

        exitButton.getStyleClass().add("exit");
        exitButton.setOnAction(e -> Platform.exit());

        backwardsButton.setPrefHeight(17);
        backwardsButton.setPrefWidth(widthGameViewPane / 3);
        backwardsButton.setLayoutX(parentWidth - backwardsButton.getPrefWidth());
        backwardsButton.setLayoutY(parentHeight - parentHeight * 0.05 - 57);

        backwardsButton.getStyleClass().add("backwards");
        backwardsButton.setOnAction(e -> newMatch());


        nameOfPlayerThatPlayTheTurn = new PlayerTurnSlider();
        nameOfPlayerThatPlayTheTurn.setLayoutX(0);
        nameOfPlayerThatPlayTheTurn.setLayoutY(0);
        nameOfPlayerThatPlayTheTurn.setPrefSize(widthGameViewPane, 17);
        nameOfPlayerThatPlayTheTurn.setAlignment(Pos.CENTER);


        totalPointsOfPlayer1 = new PointCounter(17, widthGameViewPane / 4, 0, heightGameViewPane - 17, player1Name, player1BackgroundColor);
        totalPointsOfPlayer2 = new PointCounter(17, widthGameViewPane / 4, widthGameViewPane * 3 / 4, heightGameViewPane - 17, player2Name, player2BackgroundColor);


        gameViewPane.getChildren().add(nameOfPlayerThatPlayTheTurn);
        gameViewPane.getChildren().add(totalPointsOfPlayer1);
        gameViewPane.getChildren().add(totalPointsOfPlayer2);

        gameViewPane.getChildren().add(scoreboardAndLabelsPane);

        scoreboardAndLabelsPane.setPrefSize(widthTablePane, heightTablePane);
        scoreboardAndLabelsPane.setLayoutX(widthGameViewPane / 2 - widthTablePane / 2);
        scoreboardAndLabelsPane.setLayoutY(heightGameViewPane / 2 - heightTablePane / 2);


    }

    private void setMouseHoverArchesListener() {
        listOfGraphicalArches.forEach(lin -> {
            if (!lin.isSelected()) {
                lin.setOnMouseEntered(ev -> scoreboardAndLabelsPane.getScene().setCursor(Cursor.HAND));
            }
        });
        listOfGraphicalArches.forEach(lin -> {
            if (!lin.isSelected()) {
                lin.setOnMouseExited(ev -> scoreboardAndLabelsPane.getScene().setCursor(Cursor.DEFAULT));
            }
        });
    }

    private void setClickLineListenerOnArches() {
        listOfGraphicalArches.forEach(lin -> lin.setOnMouseClicked(ev -> {
            actualMatch.playTurn(lin.getBackendArch());
            changeColorIfArchIsSelected();
            lin.setOnMouseEntered(e -> scoreboardAndLabelsPane.getScene().setCursor(Cursor.DEFAULT));
            listOfLabels.forEach(GraphicalBoxLabel::setBoxSelected);
            totalPointsOfPlayer1.setPoint(actualMatch.getScorePlayer1());
            totalPointsOfPlayer2.setPoint(actualMatch.getScorePlayer2());
            if (actualMatch.checkVictory() != null) {
                victoryPopup(actualMatch.checkVictory());
            }
            changePlayerNameOnTopLabel();
            lin.setOnMouseClicked(null);
        }));

    }

    private void changeColorIfArchIsSelected() {
        listOfGraphicalArches.forEach(lin -> {
            if (lin.isSelected()) {
                lin.setOpacity(1);
            } else {
                lin.setOpacity(0.2);
            }
        });
    }

    private void changePlayerNameOnTopLabel() {
        if (nameOfPlayerThatPlayTheTurn.playerName == null || !nameOfPlayerThatPlayTheTurn.playerName.equals(actualMatch.getPlayerTurn())) {
            if (actualMatch.getPlayerTurn().equals(player1Name)) {
                nameOfPlayerThatPlayTheTurn.setPlayerAndSlide(actualMatch.getPlayerTurn(), player1TextColor);

            } else {
                nameOfPlayerThatPlayTheTurn.setPlayerAndSlide(actualMatch.getPlayerTurn(), player2TextColor);
            }


        }
    }


    private void drawListOfNodeObjectAndInsertIntoAGroupTheList(List<? extends Node> graphicalList, Group graphicalGroup) {
        graphicalGroup.getChildren().clear();
        graphicalList.forEach(e -> graphicalGroup.getChildren().add(e));
        scoreboardAndLabelsPane.getChildren().add(graphicalGroup);
    }

    private void victoryPopup(String winner) {
        try {
            Pane popupPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("winnerPopup.fxml")));


            popupPane.setLayoutY(gameViewPane.getHeight() / 2 - popupPane.getPrefHeight() / 2);
            popupPane.setLayoutX(gameViewPane.getWidth() / 2 - popupPane.getPrefWidth() / 2);


            Color winnerColorText;

            Label player1Name = (Label) popupPane.lookup("#player1Name");
            player1Name.setText(this.player1Name);
            player1Name.setTextFill(player1TextColor);
            player1Name.getStyleClass().add("label-victoryPopup");
            Label player2Name = (Label) popupPane.lookup("#player2Name");
            player2Name.setText(this.player2Name);
            player2Name.setTextFill(player2TextColor);
            player2Name.getStyleClass().add("label-victoryPopup");


            Rectangle firstPlayerRectangle = (Rectangle) popupPane.lookup("#columnPlayer1");
            firstPlayerRectangle.setFill(player1BackgroundColor);
            Rectangle secondPlayerRectangle = (Rectangle) popupPane.lookup("#columnPlayer2");
            secondPlayerRectangle.setFill(player2BackgroundColor);
            firstPlayerRectangle.setHeight(0);
            secondPlayerRectangle.setHeight(0);

            Label labelPointsPlayer1 = (Label) popupPane.lookup("#player1Points");
            labelPointsPlayer1.setText(actualMatch.getScorePlayer1() + "");
            labelPointsPlayer1.setTextFill(player1TextColor);
            Label labelPointsPlayer2 = (Label) popupPane.lookup("#player2Points");
            labelPointsPlayer2.setText(actualMatch.getScorePlayer2() + "");
            labelPointsPlayer2.setTextFill(player2TextColor);

            Circle circlePlayer1 = (Circle) popupPane.lookup("#circlePlayer1");
            Circle circlePlayer2 = (Circle) popupPane.lookup("#circlePlayer2");

            String winnerTitle;
            if (winner.equals(this.player1Name)) {
                winnerColorText = player1TextColor;
                setRectanglesHeightInWinnerPopup(firstPlayerRectangle, secondPlayerRectangle, firstPlayerRectangle.getLayoutY(), 76, 46);
                winnerTitle = "HA VINTO " + winner.toUpperCase(Locale.ROOT);
            } else if (winner.equals(this.player2Name)) {
                winnerColorText = player2TextColor;
                setRectanglesHeightInWinnerPopup(secondPlayerRectangle, firstPlayerRectangle, firstPlayerRectangle.getLayoutY(), 76, 46);
                winnerTitle = "HA VINTO " + winner.toUpperCase(Locale.ROOT);
            } else {
                winnerColorText = Color.GREEN;
                setRectanglesHeightInWinnerPopup(secondPlayerRectangle, firstPlayerRectangle, firstPlayerRectangle.getLayoutY(), 60, 60);
                winnerTitle = winner.toUpperCase(Locale.ROOT);
            }

            circlePlayer1.setLayoutY(firstPlayerRectangle.getLayoutY() + firstPlayerRectangle.getHeight() / 2);
            circlePlayer2.setLayoutY(secondPlayerRectangle.getLayoutY() + secondPlayerRectangle.getHeight() / 2);


            double circle1Diameter = circlePlayer1.getRadius() * 2;
            double circle2Diameter = circlePlayer1.getRadius() * 2;

            labelPointsPlayer1.setLayoutY(circlePlayer1.getLayoutY() - circlePlayer2.getRadius());
            labelPointsPlayer1.setPrefSize(circle1Diameter, circle1Diameter);

            labelPointsPlayer2.setLayoutY(circlePlayer2.getLayoutY() - circlePlayer2.getRadius());
            labelPointsPlayer2.setPrefSize(circle2Diameter, circle2Diameter);

            Label winnerLabel = (Label) popupPane.lookup("#winnerTitle");
            winnerLabel.setText(winnerTitle);
            winnerLabel.setTextFill(winnerColorText);
            winnerLabel.getStyleClass().add("label-victoryPopup");

            Button newMatchButton = (Button) popupPane.lookup("#newMatch");
            newMatchButton.setOnMouseClicked(event -> newMatch());

            gameViewPane.getChildren().add(popupPane);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void setRectanglesHeightInWinnerPopup(Rectangle firstPlayerRectangle, Rectangle secondPlayerRectangle, double layoutY, int heightRectangle1, int heightRectangle2) {
        firstPlayerRectangle.setLayoutY(layoutY - heightRectangle1);
        secondPlayerRectangle.setLayoutY(secondPlayerRectangle.getLayoutY() - heightRectangle2);
        firstPlayerRectangle.setHeight(heightRectangle1);
        secondPlayerRectangle.setHeight(heightRectangle2);
    }


    public void newMatch() {
        FXMLLoader fxmlLoader = new FXMLLoader(GamePageController.class.getResource("startScene.fxml"));
        Stage stage = (Stage) gameViewPane.getScene().getWindow();

        try {
            Scene scene = new Scene(fxmlLoader.load());


            scene.getStylesheets().add(GamePageController.class.getResource("styleStartPage.css") + "");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
