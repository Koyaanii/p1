import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setState(getState());
        setSize(640,640);
        setLocation(400,400);
        add(new GameSnake());
        setVisible(true);
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.setResizable(false);
    }
}
