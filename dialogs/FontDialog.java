package dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FontDialog extends JDialog {
    JComboBox<String> fontNames, fontStyles;
    JComboBox<Integer> fontSizes;
    JTextField sampleText;
    String[] styles;
    String[] fonts;
    Integer[] defaultSizes;

    public FontDialog(JFrame frame, JTextArea textArea) {
        super(frame, "Font Dialog");
        Font defaultFont = textArea.getFont();
        String defaultFontName = defaultFont.getFamily();
        setResizable(false);
        setLayout(new FlowLayout());
        JPanel fontStuff = new JPanel();
        GroupLayout layout = new GroupLayout(fontStuff);
        fontStuff.setLayout(layout);
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setSize(new Dimension(getWidth(), 15));
        //creating the labels
        JLabel fontNameLabel = new JLabel("Font Families");
        JLabel fontSizeLabel = new JLabel("Font sizes");
        JLabel fontStylesLabel = new JLabel("Font styles");
        JLabel sampleLabel = new JLabel("Sample text:");
        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        //setting up the font drop down
        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontNames = new JComboBox<>(fonts);
        fontNames.setEditable(true);
        fontNames.setSelectedItem(defaultFontName);
        System.out.println(defaultFontName);
        //setting up the size drop down
        defaultSizes = new Integer[] {8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72};
        fontSizes = new JComboBox<>(defaultSizes);
        fontSizes.setEditable(true);
        fontSizes.setSelectedItem(textArea.getFont().getSize());
        //setting up the styles menu
        styles = new String[] {"Bold", "Italic", "Plain", "Bold Italic"};
        fontStyles = new JComboBox<>(styles);
        
        //Sample text area time
        sampleText = new JTextField("AaBbXxYyZz");
        sampleText.setPreferredSize(new Dimension(150, 50));
        sampleText.setFont(Font.decode(fontNames.getSelectedItem().toString()).deriveFont(getTheStyle(fontStyles.getSelectedItem().toString()), Integer.parseInt(fontSizes.getSelectedItem().toString())));
        sampleText.setOpaque(false);
        sampleText.setEditable(false);

        GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
        GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        horizontal.addGroup(
            layout.createParallelGroup()
            .addComponent(fontNameLabel).addComponent(fontNames)
            .addComponent(fontStylesLabel).addComponent(fontStyles)
            );
        horizontal.addGroup(
            layout.createParallelGroup()
            .addComponent(fontSizeLabel).addComponent(fontSizes).addComponent(sampleLabel).addComponent(sampleText)
            );
        layout.setHorizontalGroup(horizontal);
        vertical.addGroup(
            layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(fontNameLabel).addComponent(fontSizeLabel)
            );
        vertical.addGroup(
            layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(fontNames).addComponent(fontSizes)
            );
        vertical.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(fontStylesLabel).addComponent(sampleLabel));
        vertical.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(fontStyles).addComponent(sampleText));
        layout.setVerticalGroup(vertical);
        add(fontStuff);

        panel.add(errorLabel);
        add(panel);

        JButton apply = new JButton("Apply");
        add(apply);
        JButton okay = new JButton("OK");
        add(okay);
        JButton cancel = new JButton("Cancel");
        add(cancel);

        //it's listening time
        fontNames.addItemListener(e -> {
            String selectedFont = fontNames.getSelectedItem().toString();
            if(Arrays.binarySearch(fonts, selectedFont) < 0) {
                errorLabel.setText("The specified font name does not exist!");
                return;
            }
            sampleText.setFont(Font.decode(selectedFont).deriveFont(getTheStyle(fontStyles.getSelectedItem().toString()), Float.parseFloat(fontSizes.getSelectedItem().toString())));
        });
        fontSizes.addItemListener(e -> {
            float selectedSize = Float.parseFloat(fontSizes.getSelectedItem().toString());
            if(selectedSize < 0) {
                errorLabel.setText("Negative font size is not allowed.");
                return;
            }
            else if(selectedSize > 72.0) {
                errorLabel.setText("Font size is too big.");
                return;
            }
            sampleText.setFont(sampleText.getFont().deriveFont(selectedSize));
        });
        fontStyles.addItemListener(e -> sampleText.setFont(sampleText.getFont().deriveFont(getTheStyle(fontStyles.getSelectedItem().toString()))));
        //action listeners
        apply.addActionListener(e -> textArea.setFont(getNewFont()));
        okay.addActionListener(e -> {
            if(!sampleText.getFont().equals(textArea.getFont())) textArea.setFont(getNewFont());
            dispose();
        });
        cancel.addActionListener(e -> {
            textArea.setFont(defaultFont);
            dispose();
        });
    }
    int getTheStyle(String choice) {
        if (choice.equals(styles[0])) return Font.BOLD;
        else if (choice.equals(styles[1])) return Font.ITALIC;
        else if (choice.equals(styles[2])) return Font.PLAIN;
        else return Font.BOLD + Font.ITALIC;
    }
    Font getNewFont() {
        return sampleText.getFont();
    }
}
