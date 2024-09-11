package dialogs;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

class FindAndReplace {
    /**
     * This method finds occurrences of a string in a file.
     * @param textArea The area where the find operation is being conducted
     * @param rtf The string to be found
     * @param exactMatching If exact matching is enabled
     * @param wrapAround If wrap around is enabled
     * @param upwards If upward searching is enabled
     */
    static void find(JTextArea textArea, String rtf, boolean exactMatching, boolean wrapAround, boolean upwards) {
        boolean wasSelected = false;    //the ultimate cheat to see if a string identical to rtf was selected before this function is called or not. The cool thing is that it can give rise to a lot of weird behaviour.
        if(upwards) {
            String selText = textArea.getSelectedText();
            if (selText != null && isSubstringMatching(selText, rtf, exactMatching)) wasSelected = true;
            if (textArea.getText().substring(textArea.getCaretPosition()).equals("") || !(textArea.getCaretPosition() < rtf.length())) textArea.setCaretPosition(textArea.getCaretPosition() - rtf.length());
        }
        System.out.println("RTF: " + rtf);
        String text = textArea.getText();
        System.out.println("Text length: " + text.length());
        int begin, start, end;
        if (upwards && textArea.getCaretPosition() < rtf.length()) begin = -1; //don't even think of entering the for loop
        else if (upwards && wasSelected) {
            System.out.println("Some sussy baka is selected OMGGG");
            begin = textArea.getCaretPosition() - rtf.length() - 1;
        }
        else begin = textArea.getCaretPosition();
        start = end = 0;
        String str = "";
        for(
            //I really had no choice other than write this bhasphemy. Sure, it's not readable, but it does the job.
            int i = begin;  //Loop starts from current cursor position
            upwards ? (i >= 0) : (i < text.length() - rtf.length());  //Depending upon the value of upwards, the condition of i will be checked. We all know that the second expression of a for loop structure should have a boolean value, so we take advantage of that and use a ternary operator which reduces code readability by ten fold.
            i += (upwards ? -1 : 1) //Depending upon the value of upwards, the value of i will be modified at the end of every loop. The third expression can be any kind of statement, but many people follow the general convention of putting an expression to increment/decrement the loop counter.
        ) {
            if (upwards && i + rtf.length() > text.length()) {
                i = text.length() - rtf.length();
                continue;
            }
            if(isFirstLetterMatching(exactMatching, text.charAt(i), rtf.charAt(0))) {
                start = i;
                end = start + rtf.length();
                str = text.substring(start, end);
                if(isSubstringMatching(str, rtf, exactMatching)) {
                    System.out.println("Woke");
                    pogger(rtf, start, end, str, begin, textArea.getCaretPosition(), "ZAMN");
                    textArea.select(start, end);
                    return;
                }
            }
        }
        pogger(rtf, start, end, str, begin, textArea.getCaretPosition(), "ZAD");
        System.out.println("Done.");
        if (wrapAround) {
            textArea.setCaretPosition(0);
        }
        else JOptionPane.showMessageDialog(null, "No more occurrences found.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * As the name suggests, this method finds occurences of a string as standalone words.
     * @param textArea The area where the search is being conducted
     * @param rtf The string to be found
     * @param exactMatching If exact matching is enabled
     * @param wrapAround If wrap around is enabled
     * @param upwards If upward searching is enabled
     */
    static void findWords(JTextArea textArea, String rtf, boolean exactMatching, boolean wrapAround, boolean upwards) {
        //Every word has only letters in it. Nothing else.
        for(char x : rtf.toCharArray()) {
            if(!Character.isAlphabetic(x)) {
                JOptionPane.showMessageDialog(null, "Matching words requires word input.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        /*
         * Basic grammar has it that words are separated by spaces, tabs and newlines.
         * Here we are going to go by that assumption and parse each word separately.
         */
        String separators = " \n\t";
        String text = textArea.getText() + " ";
        int begin, start, end, possibleEnd;
        begin = textArea.getCaretPosition();
        start = end = possibleEnd = 0;
        String str = "";
        for (
            int i = begin;
            upwards ? (i > rtf.length()) : (i < text.length() - rtf.length());
            i += (upwards ? -1 : 1)
            ) {
            if (text.charAt(i) == ' ') continue;
            if (isFirstLetterMatching(exactMatching, text.charAt(i), rtf.charAt(0))) {
                System.out.println("First phase " + i);
                start = i;
                possibleEnd = start + rtf.length();
                if(separators.indexOf(text.charAt(possibleEnd)) != -1) 
                    end = possibleEnd;
                else continue;
                System.out.println("meguface " + end);
                str = text.substring(start, end);
                if(isSubstringMatching(str, rtf, exactMatching)) {
                    System.out.println("Woke");
                    pogger(rtf, start, end, str, begin, textArea.getCaretPosition(), "ZAMN");
                    textArea.select(start, end);
                    return;
                }
            }
        }
        pogger(rtf, start, end, str, begin, textArea.getCaretPosition(), "ZAD");
        System.out.println("Done.");
        if (wrapAround) textArea.setCaretPosition(0);
        else JOptionPane.showMessageDialog(null, "No more occurrences found.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    private static boolean isFirstLetterMatching(boolean exactMatching, char textChar, char rtfChar) {
        return exactMatching ? textChar == rtfChar : Character.toLowerCase(textChar) == Character.toLowerCase(rtfChar);
    }
    private static boolean isSubstringMatching(String str, String rtf, boolean exactMatching) {
        return exactMatching ? str.equals(rtf) : str.equalsIgnoreCase(rtf);
    }
    private static void pogger(String rtf, int start, int end, String str, int begin, int caretPos, String statussy) {
        System.out.println(
            "Start: " + start + "\n" +
            "End: " + end + "\n" +
            "str: \"" + str + "\"\n" +
            "Begin: " + begin + "\n" +
            "Caret position: " + caretPos + "\n" +
            "Status: " + statussy + "\n"
        );
    }
    public static void replace(JTextArea textArea, String replacement) {
        textArea.replaceSelection(replacement);
    }
    private static String getRegStr(String str) {
        String regStr = "";
        String regexLiterals = "\\?+*[-]^$|{}";
        for (int i = 0; i < str.length(); i++) {
            char curChar = str.charAt(i);
            if (regexLiterals.indexOf(curChar) != -1) regStr += "\\" + curChar;
            else regStr += curChar;
        }
        return regStr;
    }
    public static void replaceAll(JTextArea textArea, String toBeReplaced, String replacement, boolean exactMatching) {
        String text = textArea.getText();
        if (exactMatching)
            textArea.setText(text.replace(text, replacement));
        else 
            textArea.setText(text.replaceAll("(?i:" + getRegStr(toBeReplaced) + ")", replacement));
    }
}
