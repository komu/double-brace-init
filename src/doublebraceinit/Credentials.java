package doublebraceinit;

/**
 * Leaky credentials.
 */
public class Credentials {

    protected String username;
    protected String password;

    public Credentials withoutPassword() {
        // Hide the password, but store a hidden reference to original.
        String user = username;
        return new Credentials() {{
            username = user;
            password = "********";
        }};
    }

    public static void peek(Credentials credentials) throws Exception {
        Credentials parent = (Credentials) credentials.getClass().getDeclaredField("this$0").get(credentials);
        System.out.printf("visible credentials: %s/%s\n", credentials.username, credentials.password);
        System.out.printf("recovered credentials: %s/%s\n", parent.username, parent.password);
    }

    public static void main(String[] args) throws Exception {
        Credentials credentials = new Credentials() {{
            username = "user";
            password = "hunter42";
        }};

        peek(credentials.withoutPassword());
    }
}
