public class Stone {
    private int player;
    private Pair position;

    public Stone(int row, int col, int player) {
        position = new Pair(row, col);
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setPosition(Pair position) {
        this.position = position;
    }

    public Pair getPosition() {
        return position;
    }
}
