package units.progettosdm.backendclass;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import static org.junit.jupiter.api.Assertions.*;

class ArchTest {
    Dot dot1;
    Dot dot2;

    @Test
    void SelectArch() throws BadDotDeclarationException, BadArchDeclarationException, SelectArchAlreadySelectedException {
        dot1 = new Dot(0, 0);
        dot2 = new Dot(1, 0);
        Arch arch;
        arch = new Arch(dot1, dot2);
        arch.setArchSelected();
        assertTrue(arch.getArchStatus());
    }

    @ParameterizedTest
    @CsvSource(
            {"0,0,0,0",
                    "1,1,1,1",
                    "2,2,2,2"}
    )
    void ArchNodeMustBeDifferent(int a1, int b1, int a2, int b2) throws BadDotDeclarationException {
        dot1 = new Dot(a1, b1);
        dot2 = new Dot(a2, b2);

        BadArchDeclarationException badArchDeclarationException = assertThrows(BadArchDeclarationException.class, () -> new Arch(dot1, dot2));

        String expectedMessage = "Same node connection";
        String actualMessage = badArchDeclarationException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void CannotSelectAnAlreadySelectedArch() throws BadDotDeclarationException, BadArchDeclarationException {
        dot1 = new Dot(2, 0);
        dot2 = new Dot(2, 1);

        Arch arch = new Arch(dot1, dot2);
        SelectArchAlreadySelectedException selectArchAlreadySelectedException = assertThrows(SelectArchAlreadySelectedException.class, () -> {
                    arch.setArchSelected();
                    arch.setArchSelected();
                }
        );

        String expectedMessage = "Cannot select an Arch that is already selected";
        String actualMessage = selectArchAlreadySelectedException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void archToStringWorkProperly() throws BadDotDeclarationException, BadArchDeclarationException {
        dot1 = new Dot(1, 0);
        dot2 = new Dot(0, 0);
        Arch arch = new Arch(dot1, dot2);
        String expectedOutput = "[(1, 0), (0, 0), false]";
        assertEquals(expectedOutput, arch.toString());
    }

    @Test
    void archIsEqualsWorkProperly() throws BadArchDeclarationException, BadDotDeclarationException {
        dot1 = new Dot(1, 0);
        dot2 = new Dot(0, 0);
        Arch arch1 = new Arch(dot1, dot2);
        Arch arch2 = new Arch(dot2, dot1);
        assertEquals(arch1, arch2);
    }
}