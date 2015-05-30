package doublebraceinit;

/**
 * Singly linked list without tail pointers.
 */
public class LinkedList<T> {

    public T value;

    /**
     * Create a list containing of single item.
     */
    public static <T> LinkedList<T> singletonList(T v) {
        return new LinkedList<T>() {{
            value = v;
        }};
    }

    /**
     * Returns a list formed by appending an element to front of this list.
     */
    public LinkedList<T> cons(T v) {
        // Note that the code is same as in the method above.
        // Double brace initialization gives us superior code reuse.
        return new LinkedList<T>() {{
            value = v;
        }};
    }

    @SuppressWarnings("unchecked")
    public LinkedList<T> tail() {
        try {
            return (LinkedList<T>) getClass().getDeclaredField("this$0").get(this);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        LinkedList<String> node = LinkedList.singletonList("foo");
        node = node.cons("bar");
        node = node.cons("baz");
        node = node.cons("quux");

        for (LinkedList<String> n = node; n != null; n = n.tail())
            System.out.println(n.value);
    }
}
