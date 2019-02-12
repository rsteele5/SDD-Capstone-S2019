import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

class MyCanvas extends JComponent {
    private PhysicsVector position = new PhysicsVector(0,400);        //Position of object
    private PhysicsVector velocity = new PhysicsVector(0, 0);         //Velocity of object
    private PhysicsVector acceleration = new PhysicsVector(0,0);      //Acceleration of object
    private final PhysicsVector GRAVITY = new PhysicsVector(0, 0.1);  //Represents gravity acting on an object

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        URL urlToImage = this.getClass().getResource("/images/temp.jpg");
        BufferedImage img1 = null;
        try {
            img1 = ImageIO.read(urlToImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(img1, (int) position.x, (int) position.y , this);

        update(g2);
        repaint();
    }

    public void update(Graphics g) {
        //This is where the movement is being calculated
        acceleration.applyForce(GRAVITY);
        acceleration.add(acceleration.calculateFriction(velocity));
        velocity.add(acceleration);
        position.add(velocity);

        //Handles screen collision
        if (position.x > 400){
            position.x = 400;
        } else if (position.x < 0) {
            position.x = 0;
        }
        if (position.y > 400){
            position.y = 400;
        }else if (position.y < 0) {
            position.y = 0;
        }

        //Reset acceleration after at the end of each update
        acceleration.mult(0);
    }

    public void movement(Set<Character> pressed) {
        for(char key: pressed) {
            if (key == 'w') {
                acceleration.applyForce(new PhysicsVector(0,-5));
                repaint();
            }
            if (key == 'a') {
                velocity.applyForce(new PhysicsVector(-2,0));
                repaint();
            }
            if (key == 's') {
                velocity.applyForce(new PhysicsVector(0,2));
                repaint();
            }
            if (key == 'd') {
                velocity.applyForce(new PhysicsVector(2,0));
                repaint();
            }
            velocity.normalize();
            repaint();
        }
    }
}
