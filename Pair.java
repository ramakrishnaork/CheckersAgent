
public class Pair {
    int x,y;
    

    Pair(int a, int b) {
        x = a;
        y = b;
    }

    public boolean equals(Object o) {
        if (o instanceof Pair) {
            Pair p = (Pair) o;
            return p.x == x && p.y == y;
        }
        return false;
    }

    public int hashCode() {
        return Integer.valueOf(x).hashCode() * 31 + Integer.valueOf(y).hashCode();
    }
}
