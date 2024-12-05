package utils;

import java.awt.Color;

public class Resource {
    private static Resource singleton = null;

    // public long currentTime;
    // public long oldTime;
    public double deltaTime;
    public double timeGame;

    private double r, g, b;
    private boolean restartColor;
    public double factor;

    private Resource() {
        restartColor = true;
        r = 255;
        g = 0;
        b = 0;
        factor = 0.1;
    }

    public static Resource getInstance() {
        if (singleton == null) {
            singleton = new Resource();
        }

        return singleton;
    }

    public Color colorTransition() {
        double changeFactor = 0.1;
        if (r > 0.0 && g < 255.0 && restartColor) {
            r -= changeFactor;
            g += changeFactor;
            b = 0.0;
        } else if (g > 0.0 && b < 255.0) {
            r = 0.0;
            g -= changeFactor;
            b += changeFactor;
        } else if (r < 255.0 && b > 0.0) {
            r += changeFactor;
            g = 0.0;
            b -= changeFactor;
            restartColor = false;
            if (r >= 255.0 || b <= 0.0) {
                r = 255.0;
                g = 0.0;
                b = 0.0;
                restartColor = true;
            }
        }

        // System.out.println("r=" + r + ", g=" + g + ", b=" + b);

        return new Color((int) r, (int) g, (int) b);
    }
}
