import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private final int Size = 320;
    private final int DotSize = 16;
    private final int AllDots  = 20;
    private Image Dot;
    private Image Apple;
    private int AppleX;
    private int AppleY;
    private int [] X = new int[AllDots];
    private int [] Y = new int[AllDots];
    private int Dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean InGame = true;

    public  GameField() {

    setBackground(Color.black);
    LoadImages();
    initGame();
    addKeyListener(new FieldKeyListener());
    setFocusable(true);

    }

    public void initGame() {
        Dots = 3;
        for (int i = 0; i < Dots; i++){
            X[i] = 48 - i*DotSize;
            Y[i] = 48;
        }
        timer = new Timer(250,this);
        timer.start();
        createApple();
    }

    public void createApple() {
        AppleX = new Random().nextInt(20)*DotSize;
        AppleY = new Random().nextInt(20)*DotSize;
    }

    public void LoadImages() {
        ImageIcon iia = new ImageIcon("Apple.png");
        Apple = iia.getImage();
        ImageIcon iid = new ImageIcon("Dot.png");
        Dot = iid.getImage();



    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(InGame){
            Graphics2D g2d = (Graphics2D) g;
            Ellipse2D.Double circle = new Ellipse2D.Double(AppleX,AppleY,16,16);
            /* g.drawImage(Apple,AppleX,AppleY,this); */
            g2d.setColor(Color.RED);
            g2d.fill(circle);
            for (int i = 0; i < Dots; i++){
                g.drawImage(Dot,X[i],Y[i],this);
            }
        } else {
            String str  = "GAME OVER";
            Font f = new Font("", 14, Font.BOLD);
            g.setColor(Color.white);

            g.drawString(str,125,Size/2);
        }
    }

    public  void move(){
        for (int i=Dots; i > 0; i--) {
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }
        if(left){X[0]-=DotSize;}
        if(right){X[0]+=DotSize;}
        if(down){Y[0]+=DotSize;}
        if(up){Y[0]-=DotSize;}
    }

    public void checkApple(){
        if(X[0]==AppleX && Y[0]==AppleY){
            Dots++;
            createApple();
        }
    }

    public void checkCollision(){
        for (int i = Dots; i>0; i--) {
            if (i > 4 && X[0] == X[i] && Y[0] == Y[i])
                InGame = false;
        }
        if (X[0]>Size || Y[0]>Size || X[0]<0 || Y[0]<0)
            InGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(InGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;

            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;

            }
            if(key == KeyEvent.VK_UP && !down){
                left = false;
                up = true;
                right = false;

            }
            if(key == KeyEvent.VK_DOWN && !up){
                left = false;
                down = true;
                right = false;

            }

          /*  if(key == KeyEvent.VK_0 ){
                MainWindow mw = new MainWindow();
            }   */
        }
    }
}
