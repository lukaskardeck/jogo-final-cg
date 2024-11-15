package manager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputManager extends KeyAdapter{

    public boolean key_player_up;
    public boolean key_player_right;
    public boolean key_player_down;
    public boolean key_player_left;

    public boolean shot;

    /* QUANDO A TECLA FOR PRESSIONADA */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> key_player_up = true;
            case KeyEvent.VK_A -> key_player_left = true;
            case KeyEvent.VK_S -> key_player_down = true;
            case KeyEvent.VK_D -> key_player_right = true;
            case KeyEvent.VK_SPACE -> shot = true;
        }
    }

    /* QUANDO A TECLA FOR SOLTA */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> key_player_up = false;
            case KeyEvent.VK_A -> key_player_left = false;
            case KeyEvent.VK_S -> key_player_down = false;
            case KeyEvent.VK_D -> key_player_right = false;
            case KeyEvent.VK_SPACE -> shot = false;
        }
    }

    
}
