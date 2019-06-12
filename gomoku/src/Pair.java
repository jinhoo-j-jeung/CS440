public class Pair {
    private int row;
    private int col;

    public Pair(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col){
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Pair))
        {
            return false;
        }

        Pair com_obj = (Pair) obj;

        return (this.row == com_obj.getRow() && this.col == com_obj.getCol());

    }
}
