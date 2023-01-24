package units.progettosdm.backendclass;

import units.progettosdm.projectExceptions.BadBoardSizeDeclarationException;


public class Game {
    private final String playerName1;
    private int scorePlayer1;

    private final String playerName2;
    private int scorePlayer2;

    private String playerTurn;
    private final Scoreboard scoreboard;

    public Game(int n, int m, String playerName1, String playerName2) throws BadBoardSizeDeclarationException {
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        playerTurn = throwCoin();
        this.scoreboard = new Scoreboard(n, m);
    }

    public void playTurn(Arch arch) {
        scoreboard.selectArch(arch);
        int scorePoint = scoreboard.checkClosedBoxAndGivePoints(playerTurn, playerTurn.equals(playerName1) ? 1 : 2);
        if (scorePoint > 0) {
            if (playerTurn.equals(playerName1)) {
                scorePlayer1 += scorePoint;
            } else {
                scorePlayer2 += scorePoint;
            }
        }
        if (scorePoint == 0) {
            if (playerTurn.equals(playerName1)) {
                playerTurn = playerName2;
            } else {
                playerTurn = playerName1;
            }
        }
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public String throwCoin() {
        if (Math.random() < 0.5) {
            return playerName1;
        }
        return playerName2;
    }

    public String checkVictory() {
        if ((scorePlayer1 == scorePlayer2) && ((scorePlayer1 + scorePlayer2) == scoreboard.boardWidthSize * scoreboard.boardHeightSize)) {
            return "Pareggio";
        } else if (scorePlayer1 > ((scoreboard.boardWidthSize * scoreboard.boardHeightSize) / 2)) {
            return playerName1;
        } else if (scorePlayer2 > ((scoreboard.boardWidthSize * scoreboard.boardHeightSize) / 2)) {
            return playerName2;
        } else {
            return null;
        }
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public int[] getScoreboardSize() {
        return new int[]{scoreboard.boardWidthSize, scoreboard.boardHeightSize};
    }
}