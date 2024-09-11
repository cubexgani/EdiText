import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        EditorWindow window = new EditorWindow();
        window.setSize(1280, 720);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setVisible(true);
        
    }
}
