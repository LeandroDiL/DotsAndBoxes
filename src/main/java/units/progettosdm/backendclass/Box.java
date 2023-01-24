package units.progettosdm.backendclass;


public class Box {

    private Arch[] arches = new Arch[4];

    private String playerBox;
    private String boxCharacter;
    private final Dot[] boxVertexes = new Dot[4];

    public Box(Dot[] vertexes) {
        System.arraycopy(vertexes, 0, this.boxVertexes, 0, vertexes.length);
        playerBox = null;
    }

    public void setArches(Arch[] arches) {
        this.arches = arches;
    }

    public Arch[] getArches() {
        return arches;
    }

    public String getPlayerBox() {
        return playerBox;
    }

    public boolean checkClosedBox() {
        for (Arch arch : arches) {
            if (!arch.getArchStatus()) return false;
        }
        return true;
    }

    public void setPlayerBoxAndSetBoxCharacter(String playerBox, int playerNumber) {
        this.playerBox = playerBox;
        boxCharacter = playerNumber == 1 ? "B" : "R";
    }

    public boolean getArchStatusByIndex(int i) {
        return arches[i].getArchStatus();
    }

    public Dot[] getBoxVertexes() {
        return boxVertexes;
    }

    public String getBoxCharacter() {
        return boxCharacter;
    }


}
