import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.Timer;

import dialogs.*;
import menuItems.*;

public class TextMenu extends JMenuBar {
    JMenu file, format, edit, help;
    JTextArea textArea;
    TexTabbedPane tabbedPane;
    JFrame frame;
    FileUtils fUtils;
    int cou = 0;
    JLabel statusLabel;

    TextMenu(JFrame frame, TexTabbedPane content, JLabel statusLabel) {
        this.frame = frame;
        tabbedPane = content;
        this.statusLabel = statusLabel;
        fUtils = new FileUtils(frame);
        //File menu
        file = new JMenu("File");
        for(FileItems item : FileItems.values()) {
            JMenuItem menuItem;
            file.add(menuItem = new JMenuItem(item.name, item.mnemonic));
			menuItem.setAccelerator(item.keyStroke);
            menuItem.addActionListener(e -> {
                String command = e.getActionCommand().toUpperCase().replace(" ", "_");
                FileItems fiItem = FileItems.valueOf(command);
                try {
                    switch (fiItem) {
                        case NEW_TAB:
                            tabbedPane.addTextTab("Untitled " + ++cou + ".txt", "");
                            tabbedPane.setFileUtils(fUtils);
                            tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(tabbedPane.title));
                            statusLabel.setText("Opened new tab");
                            tabbedPane.setStatusLabel(statusLabel);
                            tabbedPane.startListening();
                            break;
                        case CLOSE_THE_CURRENT_TAB:
                            if(tabbedPane.getTabCount() == 0) return;
                            ((TabComponent) tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex())).close();
                            break;
                        case OPEN:
                            File curFile = fUtils.open();
                            if(curFile == null) return;
                            String fileName = curFile.getName();
                            tabbedPane.addTextTab(fileName, fUtils.read(curFile));
                            tabbedPane.setFileUtils(fUtils);
                            tabbedPane.setSelectedIndex(tabbedPane.indexOfTab(fileName));
                            fUtils.addToDir(fileName, curFile);
                            System.out.println(fUtils.showMap());
                            statusLabel.setText("Opened " + tabbedPane.getModifiedTitleAt(tabbedPane.getSelectedIndex()));
                            tabbedPane.setStatusLabel(statusLabel);
                            tabbedPane.startListening();
                            break;
                        
                        case SAVE:
                            setTextArea();
                            if(textArea == null) return;
                            boolean isSaved = fUtils.save(tabbedPane.getModifiedTitleAt(tabbedPane.getSelectedIndex()), textArea);
                            System.out.println("File name in fUtils: " + fUtils.fileName);
                            statusLabel.setText(" ");
                            if(isSaved) {
                                tabbedPane.stopListening();
                                if(!fUtils.fileName.equals("")) tabbedPane.setTitle(fUtils.fileName, tabbedPane.getSelectedIndex());
                                else tabbedPane.setTitle(tabbedPane.getModifiedTitleAt(tabbedPane.getSelectedIndex()), tabbedPane.getSelectedIndex());
                                System.out.println("getTitleAt() -> " + tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
                                System.out.println(fUtils.showMap());
                                statusLabel.setText("Saved " + tabbedPane.getModifiedTitleAt(tabbedPane.getSelectedIndex()));
                                tabbedPane.startListening();
                            }
                            System.out.println("Mirror mirror above the wall tell me what's the title: " + tabbedPane.getModifiedTitleAt(tabbedPane.getSelectedIndex()));
                            break;
                        
                        case SAVE_AS:
                            setTextArea();
                            if(textArea == null) return;
                            boolean savedAs = fUtils.saveAs(textArea);
                            System.out.println(fUtils.showMap());
                            
                            statusLabel.setText(" ");
                            if(savedAs) {
                                tabbedPane.stopListening();
                                tabbedPane.setTitle(fUtils.fileName, tabbedPane.getSelectedIndex());
                                statusLabel.setText("Saved " + tabbedPane.getModifiedTitleAt(tabbedPane.getSelectedIndex()));
                                tabbedPane.startListening();
                            }
                            break;
                        
                        case PRINT:
                            setTextArea();
                            if (textArea == null) return;
                            boolean isPrinting = textArea.print();
                            if(isPrinting) statusLabel.setText("Printing...");
                            break;
                        
                        case EXIT: 
                            close();
                            break;
                        default:
                            break;
                    }
                }
                catch(IOException exception) {
                    statusLabel.setText("Some file stuff went wrong: " + exception.getMessage());
                }
                catch(PrinterException exception) {
                    statusLabel.setText("Something's wrong with the printer.");
                }
            });
        }
        file.setMnemonic(KeyEvent.VK_F);
        add(file);

        //Format menu
        format = new JMenu("Format");
        for(FormatItems item : FormatItems.values()) {
            JMenuItem menuItem;
            format.add(menuItem = new JMenuItem(item.name, item.mnemonic));
			menuItem.setAccelerator(item.keyStroke);
            menuItem.addActionListener(e -> {
                String command = e.getActionCommand().toUpperCase().replace(" ", "_");
                FormatItems foItem = FormatItems.valueOf(command);
                switch(foItem) {
                    case TOGGLE_WORD_WRAP:
                        setTextArea();
                        if(textArea == null) return;
                        textArea.setLineWrap(!textArea.getLineWrap());
                    textArea.setWrapStyleWord(!textArea.getWrapStyleWord());
                    break;
                    case FONTS:
                        setTextArea();
                        if(textArea == null) return;
                        System.out.println("Cool tabs" + tabbedPane.getTabCount());
                        FontDialog fDialog = new FontDialog(frame, textArea);
                        fDialog.setSize(450, 350);
                        fDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        fDialog.setVisible(true);
                    break;
                }
            });
        }
        format.setMnemonic(KeyEvent.VK_M);
        add(format);

        //Edit menu
        edit = new JMenu("Edit");
        for (EditItems item: EditItems.values()) {
            JMenuItem menuItem;
            edit.add(menuItem = new JMenuItem());
            if(item.action != null) menuItem.setAction(item.action);
            else menuItem.addActionListener(e -> {
                String command = e.getActionCommand().toUpperCase().replace(' ', '_');
                EditItems edItem = EditItems.valueOf(command);
                switch (edItem) {
                    case DELETE:
                        setTextArea();
                        if(textArea == null) return;
                        textArea.replaceSelection("");
                        break;
                    case FIND:
                        setTextArea();
                        if(textArea == null) return;
                        FindDialog find = new FindDialog(frame, textArea);
                        find.setSize(450, 200);
                        find.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        find.setVisible(true);
                        break;
                    case REPLACE:
                        setTextArea();
                        if(textArea == null) return;
                        ReplaceDialog replace = new ReplaceDialog(frame, textArea);
                        replace.setSize(450, 200);
                        replace.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        replace.setVisible(true);
                        break;
                    case UNDO:
                        int selIn = tabbedPane.getSelectedIndex();
                        System.out.println("The current tab: " + selIn);
                        tabbedPane.undoAt(selIn);
                        break;
                    case SELECT_ALL:
                        setTextArea();
                        if (textArea == null) return;
                        textArea.selectAll();
                    default:
                        break;
                }
            });
            menuItem.setAccelerator(item.keyStroke);
            menuItem.setText(item.name);
            menuItem.setMnemonic(item.mnemonic);
        }
        edit.setMnemonic(KeyEvent.VK_E);
        add(edit);

        //Help menu (with no help)
        help = new JMenu("Help");
        for(HelpItems item: HelpItems.values()) {
            JMenuItem menuItem;
            help.add(menuItem = new JMenuItem(item.name, item.mnemonic));
            menuItem.setAccelerator(item.keyStroke);
            menuItem.addActionListener(e -> {
               String command = e.getActionCommand().toUpperCase();
               HelpItems heItem =  HelpItems.valueOf(command);
               switch(heItem) {
                    case ABOUT:
                        AboutDialog dialog = new AboutDialog();
                        dialog.setSize(750, 300);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        dialog.setVisible(true);
                        statusLabel.setText(" ");
                        break;
               }
            });
        }
        help.setMnemonic(KeyEvent.VK_H);
        add(help);
        
        Color frameBg = frame.getContentPane().getBackground();
        Timer colo = new Timer(75, e -> frame.getContentPane().setBackground(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255))));
        JMenu party = new JMenu("Party");
        for(PartyItems item: PartyItems.values()) {
            JMenuItem menuItem;
            party.add(menuItem = new JMenuItem(item.name, item.mnemonic));
            menuItem.setAccelerator(item.keyStroke);
            if (!menuItem.getText().equals("Start the party")) menuItem.setEnabled(false); //disables every menu item except start the party
            menuItem.addActionListener(e -> {
                JMenuItem currItem = (JMenuItem) e.getSource();
                String command = e.getActionCommand().toUpperCase().replace(' ', '_');
                PartyItems partyItem = PartyItems.valueOf(command);
                switch (partyItem) {
                    case START_THE_PARTY:
                        currItem.setEnabled(false); //disables the start menu item
                        for (int i = 2; i <= 3; i++) party.getItem(i).setEnabled(true); //enables all other menu items
                        colo.start();
                        break;
                    case RESUME_THE_PARTY:
                        currItem.setEnabled(false); //disables resume
                        colo.start();
                        break;
                    case PAUSE_THE_PARTY:
                        currItem.setEnabled(false); //disables pause
                        party.getItem(1).setEnabled(true); //enables resume
                        colo.stop();
                        break;
                    case STOP_THE_PARTY:
                        currItem.setEnabled(false); //disables stop
                        party.getItem(1).setEnabled(false); //disables resume
                        party.getItem(2).setEnabled(false); //disables pause
                        party.getItem(0).setEnabled(true);  //enables start
                        colo.stop();
                        frame.getContentPane().setBackground(frameBg);
                        break;
                }
            });
        }
        party.setMnemonic(KeyEvent.VK_P);
        add(party);
    }
    public void setTextArea() {
        try{
            textArea = tabbedPane.getTextArea(tabbedPane.getSelectedIndex());
        }
        catch(Exception e) {
            textArea = null;
            statusLabel.setText(e.getMessage());
        }
    }
    public void close() {
        int cou = tabbedPane.getTabCount();
        System.out.println("\n\nwild open tabs brother: " + cou);
        while(tabbedPane.getTabCount() > 0) {
            boolean cancelled = ((TabComponent) tabbedPane.getTabComponentAt(0)).close();
            if(cancelled) break;
        }
        System.out.println("after the closes: " + (cou = tabbedPane.getTabCount()));
        if (tabbedPane.getTabCount() == 0) frame.dispose();
    }
}