package menuItems;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public enum HelpItems {
    ABOUT("About", KeyEvent.VK_A, KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK));
    
    public String name;
    public int mnemonic;
    public KeyStroke keyStroke;
    HelpItems(String name, int mnemonic, KeyStroke keyStroke) {
    this.name = name;
    this.mnemonic = mnemonic;
    this.keyStroke = keyStroke;
    }
    
}
