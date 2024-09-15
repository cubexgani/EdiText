import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicButtonUI;


public class TabComponent extends JPanel {
    JLabel tebNaem;
    String title;
    FileUtils fUtils;
    JLabel statusLabel;
    TabButton button;
    TexTabbedPane tabbedPane;
    TabComponent(TexTabbedPane tabbedPane, int index) {
        super(new FlowLayout());
        this.tabbedPane = tabbedPane;
        setOpaque(false);

        tebNaem = new JLabel();
        setTitle(tabbedPane.getTitleAt(index));
        add(tebNaem);
        add(button = new TabButton(tabbedPane, index));
    }
    void setTitle(String str) {
        title = str;
        tebNaem.setText(adjustTitleString(str));
    }

    String adjustTitleString(String title) {
        //keep the fixed title length of 10, but keep the extension
        String fileName = title.substring(0, title.lastIndexOf("."));
        int length = fileName.length();
        String extension = title.substring(title.lastIndexOf("."));
        if(length > 10) return fileName.substring(0, 9) + "..." + extension;
        else if (length < 10) return title + " ".repeat(10 - length);
        return title;
    }
    String getModifiedTitle() {
        String modifiedTitle = title.substring(2);
        if(title.startsWith("* ")) return modifiedTitle;
        else return title;
    }
    private class TabButton extends JButton {
        TabButton(TexTabbedPane tabbedPane, int index) {
            setPreferredSize(new Dimension(17, 17));
            setUI(new BasicButtonUI());
            setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            Color original = getBackground();
            addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(184, 207, 229));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(original);
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    close();
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
                }
            });
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(6, 6, 11, 11);
            g2d.drawLine(11, 6, 6, 11);
        }
    }
    boolean close() {
        statusLabel.setText(" ");
        button.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        int ind = tabbedPane.indexOfTab(title);
        tabbedPane.setSelectedIndex(ind);
        if(title.startsWith("* ")) {
            int option = JOptionPane.showConfirmDialog(tabbedPane, "Do you want to save changes to " + getModifiedTitle() + "?", "Save the file", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if(option == JOptionPane.YES_OPTION) {
                try {
                    fUtils.save(getModifiedTitle(), tabbedPane.getTextArea(ind));
                }
                catch(IOException exception) {
                    System.out.println("Couldn't save the file while closing it.");
                }
                fUtils.removeFromDir(fUtils.fileName);
            }
            else if(option == JOptionPane.NO_OPTION) {
                button.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
                statusLabel.setText(" ");
                tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
                if (tabbedPane.getTabCount() == 0) {
                    statusLabel.setText("Click File > New Tab or hit Ctrl+T to start editing!");
                }
            }
            else {
                button.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
                return true;    //cancelled
            }
        }
        else {
            fUtils.removeFromDir(getModifiedTitle());
            tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
        }
        if (tabbedPane.getTabCount() == 0) {
            statusLabel.setText("Click File > New Tab or hit Ctrl+T to start editing!");
        }
        return false;   //not cancelled
    }
}
