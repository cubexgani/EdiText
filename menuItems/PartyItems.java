package menuItems;

import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public enum PartyItems {
    START_THE_PARTY("Start the party", KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK)),
    RESUME_THE_PARTY("Resume the party", KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.ALT_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK)),
    PAUSE_THE_PARTY("Pause the party", KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.ALT_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK)),
    STOP_THE_PARTY("Stop the party", KeyEvent.VK_T, KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.ALT_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
    
    public String name;
    public int mnemonic;
    public KeyStroke keyStroke;
    PartyItems(String name, int mnemonic, KeyStroke keyStroke) {
        this.name = name;
        this.mnemonic = mnemonic;
        this.keyStroke = keyStroke;
    }
}
