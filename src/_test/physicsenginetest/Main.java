import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
public class Main {
    public static void main(String[] args){
        MyCanvas c = new MyCanvas();
        JFrame window = new JFrame();

        window.addKeyListener(new KeyListener() {

            private final Set<Character> pressed = new HashSet<Character>();
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                pressed.add(e.getKeyChar());
                c.movement(pressed);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressed.remove(e.getKeyChar());
            }
        });

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 600, 600);
        window.getContentPane().add(c);
        window.setVisible(true);
    }
}