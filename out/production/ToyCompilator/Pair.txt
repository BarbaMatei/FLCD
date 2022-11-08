import java.util.Objects;

public class Pair<F, S> {
    private final F _first;
    private final S _second;

    public F GetFirst() {
        return this._first;
    }

    public S GetSecond() {
        return this._second;
    }

    public Pair(F first, S second) {
        this._first = first;
        this._second = second;
    }

    @Override
    public String toString() {
        return "(" + this._first.toString() + ", " + this._second.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return _first.equals(pair._first) && _second.equals(pair._second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_first, _second);
    }
}