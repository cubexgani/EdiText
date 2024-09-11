package menuItems;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public enum FileItems {
    NEW_TAB("New tab", KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK)),
    CLOSE_THE_CURRENT_TAB("Close the current tab", KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK)),
    OPEN("Open", KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK)),
    SAVE("Save", KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK)),
    SAVE_AS("Save as", KeyEvent.VK_A, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK)),
    PRINT("Print", KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK)),
    EXIT("Exit", KeyEvent.VK_E, KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));

    public String name;
    public int mnemonic;
    public KeyStroke keyStroke;
    FileItems(String name, int mnemonic, KeyStroke keyStroke) {
        this.name = name;
        this.mnemonic = mnemonic;
        this.keyStroke = keyStroke;
    }
    String getName() {
        return name;
    }
}
