package doublebraceinit;

/**
 * Double brace init is bad for your logging.
 */
public final class DebugLogging {

    static class Example {
        String name;

        @Override
        public String toString() {
            return String.format("I'm a %s named %s.", getClass().getName(), name);
        }
    }

    public static void main(String[] args) {
        Example foo = new Example();
        foo.name = "foo";

        // Let's make a class with totally useless name for logging.
        Example bar = new Example() {{ name = "bar"; }};

        System.out.println(foo);
        System.out.println(bar);
    }
}
