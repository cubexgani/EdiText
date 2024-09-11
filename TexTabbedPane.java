import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TexTabbedPane extends JTabbedPane {
    EdiTextArea textArea;
    JScrollPane scrollPane;
    String title;
    TabComponent tabComponent;
    JFrame frame;
    JLabel statusLabel;
    String prevText;

    public void addTextTab(String title, String content) {
        if (indexOfTab(title) != -1) {
            setSelectedIndex(indexOfTab(title));
            return;
        }
        textArea = new EdiTextArea(content);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        scrollPane = new JScrollPane(textArea);
        addTab(title, scrollPane);
        this.title = title;
        insertComponent(indexOfTab(title));
        System.out.println("OMG " + indexOfTab(title));
        System.out.println("Presenting the title of the tab: " + title);
    }
    void insertComponent(int index) {
        setTabComponentAt(index, new TabComponent(this, index));
        System.out.println("Proceeds to insert tab component at " + index);
    }
    void setText(String content) {
        textArea.setText(content);
    }
    void setTitle(String title, int index) {
        setTitleAt(index, title);
        System.out.println("Mirror mirror above the wall tell me what's the title: " + title);
        if(title == null) return;
        tabComponent = (TabComponent) getTabComponentAt(index);
        tabComponent.setTitle(title);
        this.title = title;
    }
    void undoAt(int index) {
        EdiTextArea tArea = getTextArea(index);
        if(tArea == null) return;
        getTextArea(index).undo();
    }
    
    EdiTextArea getTextArea(int index) {
        /*get textarea from currently selected tab
        The hierarchy of a tab goes like this:
        Tab -> JScrollPane -> JViewport -> JTextArea*/
        if(index == -1) throw new RuntimeException("Text area not found."); 
        Component parent = getComponentAt(index);
        if(parent instanceof JScrollPane scrollPane) {
            Component child = scrollPane.getComponent(0);
            if(child instanceof JViewport viewport) {
                Component grandchild = viewport.getComponent(0);
                if(grandchild instanceof EdiTextArea textArea) return textArea;
            }
        }
        throw new RuntimeException("The selected tab is not a text tab.");
    }
    void startListening() {
        textArea.startListening();
    }
    void stopListening() {
        textArea.stopListening();
    }
    public String getModifiedTitleAt(int index) {
        tabComponent = (TabComponent) getTabComponentAt(index);
        String titleInTheTab = tabComponent.getModifiedTitle();
        System.out.println("Tab component title: " + titleInTheTab);
        return titleInTheTab;
    }
    void setFileUtils(FileUtils fUtils) {
        System.out.println("fUtils being null is " + fUtils == null);
        tabComponent = (TabComponent) getTabComponentAt(indexOfTab(title));
        tabComponent.fUtils = fUtils;
    }
    void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
        tabComponent = (TabComponent) getTabComponentAt(indexOfTab(title));
        tabComponent.statusLabel = statusLabel;
    }

    public class EdiTextArea extends JTextArea {
        ArrayList<String> states;
        TextAreaListener tAreaListener;
        EdiTextArea(String content) {
            super(content);
            states = new ArrayList<>();
            states.add(content);
            tAreaListener = new TextAreaListener();
        }
        void startListening() {
            getDocument().addDocumentListener(tAreaListener);
        }
        void stopListening() {
            getDocument().removeDocumentListener(tAreaListener);
        }
        void undo() {
            System.out.println("\nMy man undid");
            if (states.size() <= 1) return;
            stopListening();
            System.out.println("The states list I'm talking about: " + states.toString());
            int prevIndex = states.size() - 2;
            System.out.println("The previous index in this states list: " + prevIndex + "\n");
            setText(states.get(prevIndex));
            states.remove(prevIndex);
            startListening();
        }
        int i = 0;
        class TextAreaListener implements DocumentListener {
            @Override
            public void insertUpdate(DocumentEvent e) {
                modifyTitle();
                addState();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                modifyTitle();
                addState();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
            
        }
        private void addState() {
            System.out.println("\nMe adding the state: ");
            System.out.println("Ticking: " + ++i);
            prevText = getText();
            if (states.contains(prevText)) return;
            System.out.println("Text right now: \"" + prevText + "\"");
            System.out.println(states.toString());
            states.add(prevText);
            if(states.size() > 0) System.out.println("New state added: " + states.get(states.size() - 1));
            if(states.size() == 20) states.remove(0);
        }
        private void modifyTitle() {
            setTitle("* " + getModifiedTitleAt(getSelectedIndex()), getSelectedIndex());
            System.out.println("Getting the title: " + getModifiedTitleAt(getSelectedIndex()));
            statusLabel.setText("Editing...");
        }
    }
}
