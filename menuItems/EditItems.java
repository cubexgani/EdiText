package menuItems;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

public enum EditItems {
    CUT("Cut", KeyEvent.VK_U, KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK), new DefaultEditorKit.CutAction()),
    COPY("Copy", KeyEvent.VK_C, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK), new DefaultEditorKit.CopyAction()),
    PASTE("Paste", KeyEvent.VK_P, KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK), new DefaultEditorKit.PasteAction()),
    DELETE("Delete", KeyEvent.VK_D, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)),
    FIND("Find", KeyEvent.VK_F, KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK)),
    REPLACE("Replace", KeyEvent.VK_R, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK)),
    UNDO("Undo", KeyEvent.VK_N, KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK)),
    SELECT_ALL("Select All", KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));

    public String name;
    public int mnemonic;
    public KeyStroke keyStroke;
    public Action action;
    EditItems(String name, int mnemonic, KeyStroke keyStroke, Action action) {
        this.name = name;
        this.mnemonic = mnemonic;
        this.keyStroke = keyStroke;
        this.action = action;
    }
    EditItems(String name, int mnemonic, KeyStroke keyStroke) {
        this(name, mnemonic, keyStroke, null);
    }
}
