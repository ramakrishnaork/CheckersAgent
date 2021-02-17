
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

class Move {
    public int rowInitial;
    public int rowStart;
    public int rowEnd;
    public int colInitial;
    public int colStart;
    public int colEnd;

    public ArrayList<Integer> rowCaptured;
    public ArrayList<Integer> colCaptured;

    public ArrayList<Integer> rowVisited;
    public ArrayList<Integer> colVisited;

    public Set<Pair> squaresCaptured;
    public int[][] state;

    public Move(int rowStart, int colStart, int rowEnd, int colEnd, int[][] state) {
        this.rowStart = rowStart;
        this.colStart = colStart;
        this.rowEnd = rowEnd;
        this.colEnd = colEnd;
        this.state = state;
        this.rowCaptured = new ArrayList<Integer>();
        this.colCaptured = new ArrayList<Integer>();
        this.rowVisited = new ArrayList<Integer>();
        this.colVisited = new ArrayList<Integer>();
        this.squaresCaptured = new HashSet<Pair>();

    }

    
    public Move(Move move) {
        this.rowInitial = move.rowInitial;
        this.colInitial = move.colInitial;
        this.rowStart = move.rowStart;
        this.colStart = move.colStart;
        this.rowEnd = move.rowEnd;
        this.colEnd = move.colEnd;
        this.rowVisited = new ArrayList<Integer>(move.rowVisited);
        this.colVisited = new ArrayList<Integer>(move.colVisited);
        this.rowCaptured = new ArrayList<Integer>(move.rowCaptured);
        this.colCaptured = new ArrayList<Integer>(move.colCaptured);
        this.squaresCaptured = new HashSet<>(move.squaresCaptured);
        this.state = move.state;
    }
}
