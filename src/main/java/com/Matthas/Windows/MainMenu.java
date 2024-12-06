package Windows;

import DXFRead.DXFRead;
import Misc.MessageConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu extends JFrame{
    private JPanel panel1;
    private JButton buttonDXFRead;
    private JTextPane textPane1;
    private JTextPane textPane2;

    public MainMenu() {
        this.setTitle("DXFTool");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000,650);

        this.setLayout(new BorderLayout());
        ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("com/Matthas/Logo.png"));
        this.setIconImage(image.getImage());

        buttonDXFRead = new JButton("DXF Load");
        buttonDXFRead.setPreferredSize(new Dimension(200,30));
        textPane1 = new JTextPane();
        textPane2 = new JTextPane();
        panel1 = new JPanel();

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        textPane1.setEditable(false);
        textPane2.setEditable(false);
        textPane1.setPreferredSize(new Dimension(1000,400));
        textPane2.setPreferredSize(new Dimension(1000, 200));

        JScrollPane scrollPane1 = new JScrollPane(textPane1);
        JScrollPane scrollPane2 = new JScrollPane(textPane2);

        panel1.add(scrollPane1);
        panel1.add(scrollPane2);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(buttonDXFRead);

        this.add(buttonPanel, BorderLayout.NORTH);
        //this.add(button1, BorderLayout.WEST);
        this.add(panel1, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
        this.setVisible(true);

        MessageConsole console = new MessageConsole(textPane1);
        console.redirectOut();
        console.redirectErr();


        //DXFRead
        buttonDXFRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> new DXFRead(MainMenu.this)).start();
                //DXFRead dxfRead = new DXFRead(MainMenu.this);

            }
        });
    }
    public void writeInfo(String text) {
        SwingUtilities.invokeLater(() -> {
            textPane1.setText(textPane1.getText() + text + "\n");
            textPane1.revalidate();
            textPane1.repaint();
        });
    }
    public void updateTextPane1(String text) {
        SwingUtilities.invokeLater(() -> textPane1.setText(text));
    }

    public void updateTextPane2(String text) {
        SwingUtilities.invokeLater(() -> textPane2.setText(text));
    }

}
