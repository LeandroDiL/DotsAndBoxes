package units.progettosdm.backendclass;

import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadBoardSizeDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    public List<Arch> totalArches = new ArrayList<>();
    public int boardWidthSize;
    public int boardHeightSize;

    private final Box[][] boxes;
    private final Dot[][] dots;

    public Scoreboard(int widthSize, int heightSize) throws BadBoardSizeDeclarationException {
        if ((widthSize < 2) || (heightSize < 2)) {
            throw new BadBoardSizeDeclarationException("Cannot create a board with a size less than 2");
        }
        this.boardWidthSize = widthSize;
        this.boardHeightSize = heightSize;
        boxes = new Box[widthSize][heightSize];
        dots = new Dot[widthSize + 1][heightSize + 1];

        createAllDots();
        createAllArches();
        createAllBoxes();
    }

    public void selectArch(Arch selectedArch) {
        try {
            int index = totalArches.indexOf(selectedArch);
            totalArches.get(index).setArchSelected();
        } catch (SelectArchAlreadySelectedException e) {
            System.out.println(e.getMessage());
        }
    }

    public int checkClosedBoxAndGivePoints(String playerName, int playerNumber) {
        int count = 0;
        for (Box[] box : boxes) {
            for (Box value : box) {
                if (value.checkClosedBox() && value.getPlayerBox() == null) {
                    count++;
                    value.setPlayerBoxAndSetBoxCharacter(playerName, playerNumber);
                }
            }
        }
        return count;
    }

    public void createAllDots() {
        for (int i = 0; i < boardWidthSize + 1; i++) {
            for (int j = 0; j < boardHeightSize + 1; j++) {
                try {
                    dots[i][j] = new Dot(i, j);
                } catch (BadDotDeclarationException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void createAllBoxes() {
        for (int i = 0; i < boardWidthSize; i++) {
            for (int j = 0; j < boardHeightSize; j++) {
                Dot[] boxVertexes = new Dot[4];

                boxVertexes[0] = dots[i][j];
                boxVertexes[1] = dots[i + 1][j];
                boxVertexes[2] = dots[i + 1][j + 1];
                boxVertexes[3] = dots[i][j + 1];

                boxes[i][j] = new Box(boxVertexes);


                Arch[] tempArches = new Arch[4];
                for (int k = 0; k < tempArches.length; k++) {
                    try {
                        tempArches[k] = new Arch(boxVertexes[k], boxVertexes[(k + 1) % tempArches.length]);
                    } catch (BadArchDeclarationException e) {
                        System.out.println(e.getMessage());
                    }
                }

                for (int k = 0; k < tempArches.length; k++) {
                    int index = totalArches.indexOf(tempArches[k]);
                    tempArches[k] = totalArches.get(index);
                }
                boxes[i][j].setArches(tempArches);
            }
        }

    }

    public void createAllArches() {
        totalArches = new ArrayList<>();
        for (int i = 0; i < boardWidthSize + 1; i++) {
            for (int j = 0; j < boardHeightSize + 1; j++) {

                try {
                    if (i + 1 < boardWidthSize + 1) {
                        totalArches.add(new Arch(dots[i][j], dots[i + 1][j]));
                    }
                    if (j + 1 < boardHeightSize + 1) {
                        totalArches.add(new Arch(dots[i][j], dots[i][j + 1]));
                    }
                } catch (BadArchDeclarationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBoxesToString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < boardWidthSize; i++) {
            for (int j = 0; j < boardHeightSize; j++) {
                output.append(boxes[i][j].checkClosedBox() ? boxes[i][j].getPlayerBox() : "O ");
            }
            output.append("\n");
        }
        return output.toString();
    }


    @Override
    public String toString() {
        return "Scoreboard{" + "\n" +
                "totalArches=" + totalArches + "\n" +
                "gridSize=" + boardWidthSize + "x" + boardHeightSize + "\n" +
                "boxes=" + "\n" + getBoxesToString() + "\n" +
                '}';
    }

    public Box[][] getBoxes() {
        return boxes;
    }

    public Dot[][] getDots() {
        return dots;
    }
}
