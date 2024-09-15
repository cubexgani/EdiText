package dialogs;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AboutDialog extends JDialog {
    JLabel label;
    public AboutDialog() {
        setBounds(25, 100, 500, 300);
        ImageIcon icon = new ImageIcon("text editor.png");
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
        icon.setImage(img);
        label = new JLabel(icon);
        setIconImage(img);
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBounds(50, 50, 200, 200);
        JTextArea area = new JTextArea(readFile("dialogs\\aboutText.txt"));
        area.setFont(new Font("Arial", Font.PLAIN, 14));
        area.setEditable(false);
        area.setOpaque(false);
        panel.setPreferredSize(getSize());
        
        
        panel.add(label);
        panel.add(area);
        add(panel);
    }
    private String readFile(String pathName) {
        try (Scanner sc = new Scanner(new File(pathName))) {
            StringBuilder builder = new StringBuilder("\u00a9 ");
            while (sc.hasNextLine()) {
                builder.append(sc.nextLine()).append("\n");
            }
            return builder.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Where did you keep aboutText.txt");
        }
        return null;
    }
}
