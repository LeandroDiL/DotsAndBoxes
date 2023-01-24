package units.progettosdm.backendclass;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadBoardSizeDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
    @Test
    void correctNumberOfArches() throws BadBoardSizeDeclarationException {
        int n = 2;
        Scoreboard board = new Scoreboard(n, n);
        board.createAllArches();
        assertEquals((n + 1) * 2 * n, board.totalArches.size());
    }

    @Test
    void notNullInitializationOfBoxes() throws BadBoardSizeDeclarationException {
        int n = 2;
        Scoreboard board = new Scoreboard(n, n);
        board.createAllArches();
        board.createAllBoxes();
        assertNotNull(board.getBoxes());
    }

    @Test
    void notNullScoreboardInitialization() throws BadBoardSizeDeclarationException {
        Scoreboard board = new Scoreboard(2, 2);
        board.createAllArches();
        board.createAllBoxes();
        assertNotNull(board);
    }

    @Test
    void selectArchTest() throws BadDotDeclarationException, BadArchDeclarationException, BadBoardSizeDeclarationException {
        Scoreboard board = new Scoreboard(2, 2);
        board.createAllArches();
        board.createAllBoxes();
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(0, 1);
        board.selectArch(new Arch(dot1, dot2));
        int index = board.totalArches.indexOf(new Arch(dot1, dot2));
        assertTrue(board.totalArches.get(index).selected);

    }

    @Test
    void closedBoxAndGivePointsTest() throws BadDotDeclarationException, BadArchDeclarationException, BadBoardSizeDeclarationException {
        Scoreboard board = new Scoreboard(2, 2);
        board.createAllArches();
        board.createAllBoxes();
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(0, 1);
        Dot dot3 = new Dot(1, 1);
        Dot dot4 = new Dot(1, 0);
        board.selectArch(new Arch(dot1, dot2));
        assertEquals(0, board.checkClosedBoxAndGivePoints("Mario", 1));
        board.selectArch(new Arch(dot2, dot3));
        assertEquals(0, board.checkClosedBoxAndGivePoints("Leandro", 2));
        board.selectArch(new Arch(dot4, dot3));
        assertEquals(0, board.checkClosedBoxAndGivePoints("Mario", 1));
        board.selectArch(new Arch(dot1, dot4));

        Box box = board.getBoxes()[0][0];
        box.getArches();
        assertEquals(1, board.checkClosedBoxAndGivePoints("Leandro", 2));
    }

    @Test
    void checkDoublePoint() throws BadArchDeclarationException, BadDotDeclarationException, BadBoardSizeDeclarationException {
        Scoreboard scoreboard = new Scoreboard(2, 2);
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(0, 1);
        Dot dot3 = new Dot(0, 2);
        Dot dot4 = new Dot(1, 0);
        Dot dot5 = new Dot(1, 1);
        Dot dot6 = new Dot(1, 2);
        List<Arch> arches = new ArrayList<>();
        arches.add(new Arch(dot1, dot2));
        arches.add(new Arch(dot5, dot6));
        arches.add(new Arch(dot6, dot3));
        arches.add(new Arch(dot2, dot3));
        arches.add(new Arch(dot1, dot4));
        arches.add(new Arch(dot4, dot5));
        arches.add(new Arch(dot2, dot5));
        for (Arch arch : arches) {
            scoreboard.selectArch(arch);
        }
        assertEquals(2, scoreboard.checkClosedBoxAndGivePoints("A", 1));
    }

    @ParameterizedTest
    @CsvSource(
            {"0,-1", "2,1", "1,2"}
    )
    void boardInizializationIsCorrect(int x, int y) {
        BadBoardSizeDeclarationException badBoardDeclarationException = assertThrows(BadBoardSizeDeclarationException.class, () -> new Scoreboard(x, y));
        String expectedMessage = "Cannot create a board with a size less than 2";
        String actualMessage = badBoardDeclarationException.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}