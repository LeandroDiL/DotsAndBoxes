package units.progettosdm.backendclass;

import org.junit.jupiter.api.Test;
import units.progettosdm.projectExceptions.BadDotDeclarationException;

import static org.junit.jupiter.api.Assertions.*;

class DotTest {

    @Test
    void dotInizializationIsCorrect() {
        try {
            Dot dot = new Dot(2, 3);
            assertArrayEquals(new int[]{2, 3}, dot.getDotIndexes());
        } catch (BadDotDeclarationException e) {
            e.printStackTrace();
        }

    }

    @Test
    void dotIsEqualWorkProperly() throws BadDotDeclarationException {
        Dot dot1 = new Dot(2, 3);
        Dot dot2 = new Dot(2, 3);
        Dot dot3 = new Dot(3, 3);

        assertEquals(dot1, dot2);
        assertEquals(dot2, dot1);
        assertNotEquals(dot2, dot3);
    }

    @Test
    void dotCannotBeNegativeException() {
        int x = -1;
        int y = 0;
        BadDotDeclarationException badDotDeclarationException = assertThrows(BadDotDeclarationException.class, () -> new Dot(x, y));

        String expectedMessage = "Dots cannot have negative coordinates";
        String actualMessage = badDotDeclarationException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void dotToStringWorkProperly() throws BadDotDeclarationException {
        Dot dot = new Dot(1, 0);
        String expectedOutput = "(1, 0)";
        assertEquals(expectedOutput, dot.toString());
    }

    @Test
    void calculationOfDotDistanceWorkProperly() throws BadDotDeclarationException {
        Dot dot1 = new Dot(0, 0);
        Dot dot2 = new Dot(1, 1);
        assertEquals(Math.sqrt(2), dot1.calculateDistanceBetweenDots(dot2));
    }
}
