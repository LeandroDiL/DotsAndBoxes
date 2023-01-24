package units.progettosdm.backendclass;

import org.junit.jupiter.api.Test;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadBoardSizeDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayMatchTest {
    @Test
    void playMatchDraw() throws BadDotDeclarationException, BadBoardSizeDeclarationException {
        String playerName1 = "A ";
        String playerName2 = "B ";
        Game newGame = new Game(2, 2, playerName1, playerName2);
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(0, 1);
        Dot dot3 = new Dot(0, 2);
        Dot dot4 = new Dot(1, 0);
        Dot dot5 = new Dot(1, 1);
        Dot dot6 = new Dot(1, 2);
        Dot dot7 = new Dot(2, 0);
        Dot dot8 = new Dot(2, 1);
        Dot dot9 = new Dot(2, 2);

        List<Arch> arch = new ArrayList<>();
        try {
            arch.add(new Arch(dot1, dot2));
            arch.add(new Arch(dot6, dot3));
            arch.add(new Arch(dot4, dot7));
            arch.add(new Arch(dot7, dot8));
            arch.add(new Arch(dot6, dot9));
            arch.add(new Arch(dot2, dot3));
            arch.add(new Arch(dot1, dot4));
            arch.add(new Arch(dot4, dot5));
            arch.add(new Arch(dot2, dot5));
            arch.add(new Arch(dot5, dot8));
            arch.add(new Arch(dot8, dot9));
            arch.add(new Arch(dot5, dot6));

        } catch (BadArchDeclarationException e) {
            e.printStackTrace();
        }
        arch.forEach(newGame::playTurn);
        assertEquals("Pareggio", newGame.checkVictory());
    }

    @Test
    void playMatchAWin() throws BadDotDeclarationException, BadBoardSizeDeclarationException {
        String playerName1 = "A ";
        String playerName2 = "B ";
        Game newGame = new Game(2, 2, playerName1, playerName2);
        newGame.setPlayerTurn(playerName2);
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(0, 1);
        Dot dot3 = new Dot(0, 2);
        Dot dot4 = new Dot(1, 0);
        Dot dot5 = new Dot(1, 1);
        Dot dot6 = new Dot(1, 2);
        Dot dot7 = new Dot(2, 0);
        Dot dot8 = new Dot(2, 1);
        Dot dot9 = new Dot(2, 2);

        List<Arch> arch = new ArrayList<>();
        try {
            arch.add(new Arch(dot1, dot2));
            arch.add(new Arch(dot6, dot3));
            arch.add(new Arch(dot4, dot7));
            arch.add(new Arch(dot7, dot8));
            arch.add(new Arch(dot6, dot9));
            arch.add(new Arch(dot2, dot3));
            arch.add(new Arch(dot5, dot8));
            arch.add(new Arch(dot8, dot9));
            arch.add(new Arch(dot5, dot6));
            arch.add(new Arch(dot1, dot4));
            arch.add(new Arch(dot2, dot5));
            arch.add(new Arch(dot4, dot5));

        } catch (BadArchDeclarationException e) {
            e.printStackTrace();
        }
        arch.forEach(newGame::playTurn);
        assertEquals(playerName1, newGame.checkVictory());
    }

    @Test
    void playMatchBWin() throws BadDotDeclarationException, BadBoardSizeDeclarationException {
        String playerName1 = "A ";
        String playerName2 = "B ";
        Game newGame = new Game(2, 2, playerName1, playerName2);
        newGame.setPlayerTurn(playerName1);
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(0, 1);
        Dot dot3 = new Dot(0, 2);
        Dot dot4 = new Dot(1, 0);
        Dot dot5 = new Dot(1, 1);
        Dot dot6 = new Dot(1, 2);
        Dot dot7 = new Dot(2, 0);
        Dot dot8 = new Dot(2, 1);
        Dot dot9 = new Dot(2, 2);

        List<Arch> arch = new ArrayList<>();
        try {
            arch.add(new Arch(dot1, dot2));
            arch.add(new Arch(dot5, dot6));
            arch.add(new Arch(dot6, dot3));
            arch.add(new Arch(dot4, dot7));
            arch.add(new Arch(dot7, dot8));
            arch.add(new Arch(dot6, dot9));
            arch.add(new Arch(dot2, dot3));
            arch.add(new Arch(dot1, dot4));
            arch.add(new Arch(dot4, dot5));
            arch.add(new Arch(dot2, dot5));
            arch.add(new Arch(dot5, dot8));
            arch.add(new Arch(dot8, dot9));

        } catch (BadArchDeclarationException e) {
            e.printStackTrace();
        }
        arch.forEach(newGame::playTurn);
        assertEquals(playerName2, newGame.checkVictory());
    }
}
