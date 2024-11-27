package utils;

public class Resource {
    private static Resource singleton = null;

    public long currentTime;
    public long oldTime;
    public double deltaTime;

    private Resource() {
    }

    public static Resource getInstance() {
        if (singleton == null) {
            singleton = new Resource();
        }

        return singleton;
    }
}
