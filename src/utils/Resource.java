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
        double changeFactor = 0.08;
        double minBrightness = 100.0;
    
        if (r > minBrightness && g < 255.0 && restartColor) {
            r -= changeFactor;
            g += changeFactor;
            b = minBrightness;
        } else if (g > minBrightness && b < 255.0) {
            r = minBrightness;
            g -= changeFactor;
            b += changeFactor;
        } else if (r < 255.0 && b > minBrightness) {
            r += changeFactor;
            g = minBrightness;
            b -= changeFactor;
            restartColor = false;
    
            if (r >= 255.0 || b <= minBrightness) {
                r = 255.0;
                g = minBrightness;
                b = minBrightness;
                restartColor = true;
            }
        }
    
        // Limita os valores para garantir o brilho necessário
        r = Math.max(r, minBrightness);
        g = Math.max(g, minBrightness);
        b = Math.max(b, minBrightness);
    
        return new Color((int) r, (int) g, (int) b);
    }
    
}
