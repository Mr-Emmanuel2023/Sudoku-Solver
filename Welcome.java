import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Welcome extends JFrame implements ActionListener {

    JButton button;
    JButton button2;
    JLabel label;
    JLabel label2;
    JFrame Welcome = new JFrame();
    public Welcome() {

        this.setSize(new Dimension(800, 500));
        this.getContentPane().setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        ImageIcon icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());


        Container container = Box.createVerticalBox();

        label = new JLabel("Welcome to...");
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setFont(new Font("Comic Sans MS", 1, 20));


        label2= new JLabel("Sudoku Solver");
        label2.setAlignmentX(CENTER_ALIGNMENT);
        label2.setFont(new Font("Comic Sans MS", 1, 40));

        button = new JButton("            PLAY            ");
//        button.setLocation(500,700 );
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setBackground(Color.white);
        button.setBounds(100,100,100,100);
        button.setFocusable(false);
        button.addActionListener(this);

        button2 = new JButton("            INFO            ");
//        button2.setLocation(500,700 );
        button2.setAlignmentX(CENTER_ALIGNMENT);
        button2.setBackground(Color.white);
        button2.setBounds(100,100,100,100);
        button2.setFocusable(false);
        button2.addActionListener(this);

        container.add(Box.createVerticalStrut(150));
        container.add(label);
        container.add(label2);
        container.add(Box.createVerticalStrut(100));
        container.add(button);
        container.add(Box.createVerticalStrut(30));
        container.add(button2);
        this.add(container);
        this.setVisible(true);
//        this.getContentPane().setForeground(Color.white);

    }

    public static void main(String[] args) {

        new Welcome();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == button) {
            new SudokuGrid();
            this.dispose();
        }

        if(e.getSource() == button2) {
            new Info();
            this.dispose();
        }

    }
}
