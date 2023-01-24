package units.progettosdm.backendclass;

import org.junit.jupiter.api.Test;
import units.progettosdm.projectExceptions.BadArchDeclarationException;
import units.progettosdm.projectExceptions.BadDotDeclarationException;
import units.progettosdm.projectExceptions.SelectArchAlreadySelectedException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoxTest {

    @Test
    void closingBox() throws BadDotDeclarationException, BadArchDeclarationException, SelectArchAlreadySelectedException {
        Arch[] arch = new Arch[4];
        Dot[] dots = new Dot[4];

        dots[0] = new Dot(0, 0);
        dots[1] = new Dot(0, 1);
        dots[2] = new Dot(1, 0);
        dots[3] = new Dot(1, 1);

        arch[0] = new Arch(dots[0], dots[1]);
        arch[0].setArchSelected();
        assertTrue(arch[0].getArchStatus());


        arch[1] = new Arch(dots[1], dots[3]);
        arch[1].setArchSelected();
        assertTrue(arch[1].getArchStatus());

        arch[2] = new Arch(dots[3], dots[2]);
        arch[2].setArchSelected();
        assertTrue(arch[2].getArchStatus());

        arch[3] = new Arch(dots[2], dots[0]);
        arch[3].setArchSelected();
        assertTrue(arch[3].getArchStatus());

        Box box = new Box(dots);
        box.setArches(arch);
        assertTrue(box.checkClosedBox());

    }
}
