package menuItems;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public enum FormatItems {
    TOGGLE_WORD_WRAP("Toggle word wrap", KeyEvent.VK_W, KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.ALT_DOWN_MASK)),
    FONTS("Fonts", KeyEvent.VK_F, KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));

    public String name;
    public int mnemonic;
    public KeyStroke keyStroke;
    FormatItems(String name, int mnemonic, KeyStroke keyStroke) {
        this.keyStroke = keyStroke;
        this.name = name;
        this.mnemonic = mnemonic;
    }
}
