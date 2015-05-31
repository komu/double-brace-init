# Double Brace Initialization

Every now and then one bumps into the [Double Brace Initialization](http://c2.com/cgi/wiki?DoubleBraceInitialization)
pattern in Java:

```java
Foo foo = new Foo() {{
    name = "baz";
    value = 42;
    addChild(new Foo() {{
        name = "child 1";
        value = 42;
    }});
}};
```

If you are knowledgeable in the semantics of Java and have thought about the implications of this code, you
probably understand that this is a Bad Idea. If not, perhaps studying the following programs will produce
enlightenment:

  - [LinkedList.java](src/doublebraceinit/LinkedList.java) implements a singly linked list without tail pointers
    visible in the source code. Extra credits for having two methods with same method code, but different semantics.
  - [Credentials.java](src/doublebraceinit/Credentials.java) sneakily leaks password while seemingly protecting it.
  - [MemoryLeak.java](src/doublebraceinit/MemoryLeak.java) does some batch processing and would have no problem
    completing if it were not for double brace initialization.
  - [DebugLogging.java](src/doublebraceinit/DebugLogging.java) implements a program that tries to provide useful
    information in `toString()`, only to find out that double brace initialization messes it up.
  - [MagicWand.java](src/doublebraceinit/MagicWand.java) implements magical maps.
  - [Serialization.java](src/doublebraceinit/Serialization.java) fails at serializing a seemingly serializable class.

While your program might be immune to these (and other) problems right now, are you really willing to make a system
more complex at a global level just to save a few keystrokes locally?

## A sane alternative

If you are on Java 8, you could consider this:

```java
public static <T> T init(T value, Consumer<T> consumer) {
    consumer.accept(value);
    return value;
}

...

Foo foo = init(new Foo(), foo -> {
    foo.name = "baz";
    foo.value = 42;
    
    foo.addChild(init(new Foo(), child1 -> {
        child1.name = "child 1";
        child1.value = 42;
    }));
});
```

Of course this will still create some extra bloat when compared to basic initialization code, but it will
let you nest the structure nicely and will not mess up your Foo-instances.
