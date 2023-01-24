package units.progettosdm.backendclass;

import units.progettosdm.projectExceptions.BadDotDeclarationException;

import java.util.Arrays;

public class Dot {
    final private int[] dotIndexes = new int[2];

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dot dot = (Dot) o;

        return Arrays.equals(dotIndexes, dot.dotIndexes);
    }

    public Dot(int x, int y) throws BadDotDeclarationException {
        if ((x < 0) || (y < 0)) {
            throw new BadDotDeclarationException("Dots cannot have negative coordinates");
        }
        this.dotIndexes[0] = x;
        this.dotIndexes[1] = y;
    }

    public int[] getDotIndexes() {
        return dotIndexes;
    }

    public double calculateDistanceBetweenDots(Dot dot) {
        return Math.sqrt(Math.pow((this.getDotIndexes()[0] - dot.getDotIndexes()[0]), 2) + Math.pow((this.getDotIndexes()[1] - dot.getDotIndexes()[1]), 2));
    }

    @Override
    public String toString() {
        return "(" + dotIndexes[0] + ", " + dotIndexes[1] + ")";
    }
}
