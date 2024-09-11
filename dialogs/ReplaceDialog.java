package dialogs;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import java.awt.FlowLayout;

public class ReplaceDialog extends JDialog {
    boolean exactMatching, matchWords, wrapAround;
    JTextArea textArea;
    JTextField findField, replaceField;
    public ReplaceDialog(JFrame frame, JTextArea textArea) {
        super(frame, "Find and Replace");
        setLayout(new FlowLayout());

        JLabel findLabel = new JLabel("Find: ");
        JLabel replaceLabel = new JLabel("Replace with: ");
        findField = new JTextField(20);
        replaceField = new JTextField(20);

        JButton find = new JButton("Find Next"),
        replace = new JButton("Replace"),
        replaceAll = new JButton("Replace All"),
        cancel = new JButton("Cancel");
        find.addActionListener(e -> {
            String rtf = findField.getText();
            FindAndReplace.find(textArea, rtf, exactMatching, wrapAround, false);
        });
        replace.addActionListener(e -> FindAndReplace.replace(textArea, replaceField.getText()));
        replaceAll.addActionListener(e -> FindAndReplace.replaceAll(textArea, findField.getText(), replaceField.getText(), exactMatching));
        cancel.addActionListener(e -> dispose());

        JCheckBox exactBox = new JCheckBox("Exact matching"),
        wrapBox = new JCheckBox("Wrap around");
        exactBox.addActionListener(e -> exactMatching = exactBox.isSelected());
        wrapBox.addActionListener(e -> wrapAround = wrapBox.isSelected());
        
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
        GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
        
        horizontal.addGroup(layout.createParallelGroup().addComponent(findLabel).addComponent(replaceLabel))
        .addGroup(
            layout.createParallelGroup().addComponent(findField).addComponent(replaceField).addGroup(layout.createSequentialGroup().addComponent(exactBox).addComponent(wrapBox))
        )
        .addGroup(layout.createParallelGroup().addComponent(find).addComponent(replace).addComponent(replaceAll).addComponent(cancel));
        layout.setHorizontalGroup(horizontal);
        vertical.addGroup(
            layout.createParallelGroup(Alignment.BASELINE).addComponent(findLabel).addComponent(findField).addComponent(find)
        )
        .addGroup(
            layout.createParallelGroup(Alignment.BASELINE).addComponent(replaceLabel).addComponent(replaceField).addComponent(replace)
        )
        .addGroup(
            layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(exactBox).addComponent(wrapBox)).addComponent(replaceAll)
        )
        .addGroup(layout.createParallelGroup().addComponent(cancel));
        layout.setVerticalGroup(vertical);

        add(panel);
    }
}
