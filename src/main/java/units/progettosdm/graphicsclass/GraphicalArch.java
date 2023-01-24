package units.progettosdm.graphicsclass;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import units.progettosdm.backendclass.Arch;

class GraphicalArch extends Rectangle {

    private final String direction;
    private final Arch backendArch;

    public GraphicalArch(Circle dot1, Circle dot2, Arch backendArch) {
        this.backendArch = backendArch;
        if (dot1.getCenterX() == dot2.getCenterX()) {
            setLayoutX(dot1.getCenterX() - dot1.getRadius() / 2);
            setLayoutY(dot1.getCenterY() + dot1.getRadius() * 2);
            setHeight(dot2.getCenterY() - dot1.getCenterY() - dot1.getRadius() * 4);
            setWidth(dot1.getRadius());
            this.direction = "vertical";

        } else {

            setLayoutX(dot1.getCenterX() + dot1.getRadius() * 2);
            setLayoutY(dot1.getCenterY() - dot1.getRadius() / 2);
            setHeight(dot1.getRadius());
            setWidth(dot2.getCenterX() - dot1.getCenterX() - dot1.getRadius() * 4);
            this.direction = "horizontal";
        }
    }

    @Override
    public String toString() {
        return "GraphicalArch{" +
                "xStart=" + getLayoutX() +
                ", yStart=" + getLayoutY() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",direction=" + direction +
                '}';
    }

    public Arch getBackendArch() {
        return backendArch;
    }

    public boolean isSelected() {
        return backendArch.getArchStatus();
    }
}
