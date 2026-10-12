import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Info extends JFrame implements ActionListener {
    JLabel label;
    JButton button;
    JFrame Info = new JFrame();

    public Info() {
        this.setSize(new Dimension(800, 500));
        this.getContentPane().setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        ImageIcon icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());



        Container container = Box.createHorizontalBox();
        container.add(Box.createHorizontalStrut(200));
        ImageIcon instruction = new ImageIcon("instructions.png");
        label = new JLabel(instruction);

        button = new JButton("            BACK            ");
        button.setBackground(Color.white);
        button.setFocusable(false);
        button.setBounds(100,100,100,100);
        button.addActionListener(this);

        container.add(label);
        container.add(button);
        this.add(container);
    }

    public static void main(String[] args) {
        new Info().setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource()== button) {
            new Welcome();
            this.dispose();
        }
    }

}
