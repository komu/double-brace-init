package doublebraceinit;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

public class MagicWand {

    private static final int MAGIC_NUMBER = 4;

    public void wave(Object o) {
        // It's magic!
    }

    public Map<String, ?> toMap() {
        try {
            Map<String, Object> result = new TreeMap<>();
            for (Field field : getClass().getDeclaredFields()) {
                result.put(field.getName().substring(MAGIC_NUMBER), field.get(this));
            }
            return result;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String foo = "first value";
        String bar = "second value";
        String baz = "third value";

        MagicWand wand = new MagicWand() {{
            wave(foo);
            wave(bar);
            wave(baz);
        }};

        System.out.println(wand.toMap());
    }
}
