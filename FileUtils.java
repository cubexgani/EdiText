import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class FileUtils {
    JFileChooser fileChooser;
    JFrame frame;
    Map<String, File> fileMap;
    String fileName;

    FileUtils(JFrame frame) {
        this.frame = frame;
        fileChooser = new JFileChooser();
        fileMap = new HashMap<>();
    }
    File open() {
        int status = fileChooser.showOpenDialog(frame);
        if(status == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
    String read(File f) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine()).append("\n");
            }
        }
        return sb.toString();
    }

    void addToDir(String fileName, File file) {
        fileMap.put(fileName, file);
    }
    void removeFromDir(String fileName) {
        if (fileMap.containsKey(fileName)) {
            fileMap.remove(fileName);
        }
    }

    boolean saveAs(JTextArea textArea) throws IOException {
        int status = fileChooser.showSaveDialog(frame);
        if(status == JFileChooser.APPROVE_OPTION) {
            File curFile = fileChooser.getSelectedFile();
            String filePath = curFile.getAbsolutePath();
            write(filePath, textArea.getText());
            addToDir(curFile.getName(), new File(filePath));
            fileName = curFile.getName();
            return true;
        }
        return false;
    }
    private void write(String path, String text) throws IOException {
        FileWriter fw = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.write(text);
        pw.close();
        bw.close();
        fw.close();
    }
    boolean save(String title, JTextArea textArea) throws IOException {
        fileName = "";
        if(fileMap.containsKey(title)) {
            write(fileMap.get(title).getAbsolutePath(), textArea.getText());
            return true;
        }
        else {
            return saveAs(textArea);
        }
    }
    String showMap() {
        String strRep = fileMap.toString();
        int length = strRep.length();
        String[] files = strRep.substring(1, length - 1).split(",\\s");
        return "{\n" + String.join(",\n", files) + "\n}";
    }
}
