package com.Matthas.windows;

import com.Matthas.exportTools.csvExport.CSVExport;
import com.Matthas.cadObjects.CADObjects;
import com.Matthas.dxfRead.DXFReader;
import com.Matthas.misc.MessageConsole;
import com.Matthas.dxfRead.DXFDrawing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu extends JFrame{
    private JPanel panel1;
    private JButton buttonDXFRead;
    private JButton buttonExportCSV;
    private JTextPane textPane1;

    private String[] aryLines;
    private CADObjects cad;
    private DXFDrawing dxf;
    private String file_name;

    public MainMenu() {
        this.setTitle("DXFTool");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 650);

        this.setLayout(new BorderLayout());
        ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("Icons/Logo.png"));
        this.setIconImage(image.getImage());

        //button for DXF Load
        buttonDXFRead = new JButton("DXF Load");
        buttonDXFRead.setPreferredSize(new Dimension(200, 30));

        //button for Load Objects
        buttonExportCSV = new JButton("Export to CSV");
        buttonExportCSV.setPreferredSize(new Dimension(200, 30));


        textPane1 = new JTextPane();
        panel1 = new JPanel();

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        textPane1.setEditable(false);
        textPane1.setPreferredSize(new Dimension(1000, 600));
        JScrollPane scrollPane1 = new JScrollPane(textPane1);
        panel1.add(scrollPane1);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(buttonDXFRead);
        buttonPanel.add(buttonExportCSV);

        this.add(buttonPanel, BorderLayout.NORTH);
        //this.add(button1, BorderLayout.WEST);
        this.add(panel1, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
        this.setVisible(true);

        MessageConsole console = new MessageConsole(textPane1);
        console.redirectOut();
        console.redirectErr();


        //DXFReader
        buttonDXFRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new Thread(() -> new DXFReader(MainMenu.this)).start();
                //DXFReader com.Matthas.dxfRead = new DXFReader(MainMenu.this);
                new Thread(() -> {
                    DXFReader dxfReader = new DXFReader(MainMenu.this);
                    // Initialize the variables with data from DXFReader
                    file_name = dxfReader.getFile_name();
                    dxf = dxfReader.getDXFDrawing();
                    cad = dxfReader.getCad();
                    aryLines = dxfReader.getAryLines();
                }).start();
            }
        });

        //Export CSV
        buttonExportCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    //CSVExport csvExport = new CSVExport(cad, dxf);
                    CSVExport.createCSV(cad, dxf);
                }).start();
                //do nothing
                System.out.println("this button does nothing :) ... for now");
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
}