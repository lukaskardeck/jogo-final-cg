package manager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputManager extends KeyAdapter {

    public boolean escape;
    public boolean enter;
    public boolean p;

    public boolean key_player_up;
    public boolean key_player_right;
    public boolean key_player_down;
    public boolean key_player_left;

    public boolean shot;

    /* QUANDO A TECLA FOR PRESSIONADA */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE: escape = true; break;
            case KeyEvent.VK_ENTER: enter = true; break;
            case KeyEvent.VK_P: p = true; break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W: key_player_up = true; break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: key_player_left = true; break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S: key_player_down = true; break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: key_player_right = true; break;
            case KeyEvent.VK_SPACE: shot = true; break;
        }
    }

    /* QUANDO A TECLA FOR SOLTA */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W: key_player_up = false; break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: key_player_left = false; break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S: key_player_down = false; break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: key_player_right = false; break;
            case KeyEvent.VK_SPACE: shot = false; break;
        }
    }

}
