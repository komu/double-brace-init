package doublebraceinit;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialization {

    private MyClass getObject() {
        // Even though MyClass is declared as Serializable, our returned
        // class is not Serializable because it references the unserializable
        // parent class.
        //
        // If the parent class were serializable, we'd end up writing it as well,
        // which is probably not what we want. Furthermore, the serialization format
        // would be very fragile because changes to parent class or this inner class
        // definition would cause incompatibilities.
        return new MyClass() {{
            name = "foo";
        }};
    }

    public static class MyClass implements Serializable {
        String name;
    }

    public static void main(String[] args) throws Exception {
        Serialization dbi = new Serialization();

        ObjectOutputStream oo = new ObjectOutputStream(new ByteArrayOutputStream());
        oo.writeObject(dbi.getObject());
    }
}
