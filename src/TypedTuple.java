import java.io.Serializable;
public class TypedTuple<L, M, R> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected L left;
    protected M middle;
    protected R right;

    protected TypedTuple() {
        // Default constructor for serialization
    }

    public TypedTuple(L inLeft, R inRight) {
        left = inLeft;
        right = inRight;
    }

    public TypedTuple(L inLeft, M middle, R inRight) {
        left = inLeft;
        right = inRight;
    }

    public L getLeft() {
        return left;
    }

    public M getMiddle() {
        return middle;
    }

    public R getRight() {
        return right;
    }




}