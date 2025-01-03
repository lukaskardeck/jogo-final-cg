package utils;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Constants {

    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;
    public static final int FPS = 60;
    public static final int TIME_PER_FRAME = (int)((1_000.0 / FPS));
    public static final int NUM_SHOTS = 20;
    // public static final int NUM_ENEMY = 10;

    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    
    public static final float PLAYER_VELOCITY_MODULE = 5;
    // public static final float ENEMY_VELOCITY_MODULE = 2;
    public static final float SCENE_WIDTH = 1024;
    public static final float SCENES_VELOCITY_MODULE = 2;

    public static final int MAX_HEIGHT_TERRAIN = 140;
}
