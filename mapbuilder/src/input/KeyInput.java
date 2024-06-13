package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    boolean[] keys;
    private final int[] keyCodes = {48, 49, 50, 51, 52, 53 , 54, 55, 56, 57};

    public KeyInput() {
        keys = new boolean[256];
    }

    public boolean isPressed(int keyCode){
        return keys[keyCode];
    }

    public boolean isClicked(int keyCode) {
        for (int i = 0; i < keyCodes.length; i++) {
            if (keyCode == keyCodes[i]) {
                return keys[keyCodes[i]];
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

        for (int i=0;i<keyCodes.length; i++) {
            if (e.getKeyCode() == keyCodes[i]) {
                keys[keyCodes[i]] = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;

        for (int i=0;i<keyCodes.length; i++) {
            if (e.getKeyCode() == keyCodes[i]) {
                keys[keyCodes[i]] = false;
            }
        }
    }
}
