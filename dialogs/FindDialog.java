package dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

public class FindDialog extends JDialog implements ActionListener {
    boolean upwards, exactMatching, matchWords, wrapAround;
    JTextArea textArea;
    JTextField findField;
    public FindDialog(JFrame frame, JTextArea textArea) {
        super(frame, "Find");

        setLayout(new FlowLayout());
        this.textArea = textArea;

        JLabel findLabel = new JLabel("Find: ");  //this won't be needed outside the class
        
        findField = new JTextField(20);
        findField.addActionListener(this);
        JButton findNext = new JButton("Find Next");
        JButton cancel = new JButton("Cancel");
        
        JCheckBox dirnBox = new JCheckBox("Upwards");
        dirnBox.addActionListener(e -> upwards = dirnBox.isSelected());
        JCheckBox exactBox = new JCheckBox("Exact matching");
        exactBox.addActionListener(e -> exactMatching = exactBox.isSelected());
        JCheckBox wordsBox = new JCheckBox("Match words");
        wordsBox.addActionListener(e -> matchWords = wordsBox.isSelected());
        JCheckBox wrapBox = new JCheckBox("Wrap around");
        wrapBox.addActionListener(e -> wrapAround = wrapBox.isSelected());

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
        GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
        
        horizontal.addGroup(
            layout.createParallelGroup().addComponent(findLabel)
            );
        horizontal.addGroup(
            layout.createParallelGroup()
            .addComponent(findField)
            .addGroup(layout.createSequentialGroup()
            .addComponent(dirnBox).addComponent(exactBox))  //the checkboxes are arranged sequentially like in FlowLayout
            .addGroup(layout.createSequentialGroup()
            .addComponent(wordsBox).addComponent(wrapBox))
        );
        horizontal.addGroup(
            layout.createParallelGroup().addComponent(findNext).addComponent(cancel)
        );
        vertical.addGroup(
            layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(findLabel).addComponent(findField)
            .addComponent(findNext)
        );
        vertical.addGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createParallelGroup(Alignment.BASELINE)    //the checkboxes share a common baseline
            .addComponent(dirnBox).addComponent(exactBox))
            .addComponent(cancel)   //the cancel button doesn't
        );
        vertical.addGroup(
            layout.createParallelGroup(Alignment.BASELINE).addComponent(wordsBox).addComponent(wrapBox)
        );
        layout.setHorizontalGroup(horizontal);
        layout.setVerticalGroup(vertical);
        add(panel);

        findNext.addActionListener(this);   //felt like inserting the actionlistener in the current object for increased code readability
        cancel.addActionListener(e -> dispose());

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String rtf = findField.getText();
        if (matchWords) FindAndReplace.findWords(textArea, rtf, exactMatching, wrapAround, upwards);
        else FindAndReplace.find(textArea, rtf, exactMatching, wrapAround, upwards);
    }
}