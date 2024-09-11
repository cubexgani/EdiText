import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditorWindow extends JFrame {
    TextMenu menu;
    JLabel statusLabel;
    EditorWindow() {
        super("EdiText");
        setIconImage(new ImageIcon("src\\text editor.png").getImage());
        setLayout(new BorderLayout());
        TexTabbedPane tabbedPane = new TexTabbedPane();
        statusLabel = new JLabel("Click File > New Tab or hit Ctrl+T to start editing!");
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setSize(getWidth(), 10);
        panel.add(statusLabel);
        add(menu = new TextMenu(this, tabbedPane, statusLabel), BorderLayout.NORTH);
        add(tabbedPane);
        add(panel, BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menu.close();
                
            }
        });
    }
}
