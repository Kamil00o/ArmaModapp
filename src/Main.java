import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private JFrame frame;
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Main() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        List<String> modaFileBody = new LinkedList<>();
        List<String> modbFileBody = new LinkedList<>();
        frame = new JFrame();
        frame.setBounds(100, 100, 886, 519);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JTextPane txtpnModsetA = new JTextPane();
        txtpnModsetA.setText("No modset selected");
        txtpnModsetA.setEditable(false);
        txtpnModsetA.setBounds(20, 199, 280, 23);
        panel.add(txtpnModsetA);

        JTextPane txtpnModsetB = new JTextPane();
        txtpnModsetB.setText("No modset selected");
        txtpnModsetB.setEditable(false);
        txtpnModsetB.setBounds(20, 265, 280, 23);
        panel.add(txtpnModsetB);


        JRadioButton rdbtnNewRadioButton = new JRadioButton("Merge\r\n");
        rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        rdbtnNewRadioButton.setBounds(20, 39, 149, 23);
        panel.add(rdbtnNewRadioButton);

        JRadioButton rdbtnSubstract = new JRadioButton("Substract");
        rdbtnSubstract.setFont(new Font("Tahoma", Font.BOLD, 12));
        rdbtnSubstract.setBounds(20, 64, 149, 23);
        panel.add(rdbtnSubstract);

        JLabel lblNewLabel = new JLabel("Select mode");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel.setBounds(10, 10, 82, 23);
        panel.add(lblNewLabel);

        JLabel lblNewModsetName = new JLabel("New modset name");
        lblNewModsetName.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewModsetName.setBounds(10, 93, 135, 23);
        panel.add(lblNewModsetName);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.BOLD, 12));
        textField.setBounds(20, 126, 149, 30);
        panel.add(textField);
        textField.setColumns(10);

        JButton btnNewButton_1 = new JButton("Import Modset A");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser modA = new JFileChooser();
                int openResult = modA.showOpenDialog(null);
                if (openResult == JFileChooser.APPROVE_OPTION) {
                    txtpnModsetA.setText(modA.getSelectedFile().getAbsolutePath());
                    File modaFile = new File(modA.getSelectedFile().getAbsolutePath());
                    System.out.println(modaFile);
                    try {
                        Scanner scan = new Scanner(modaFile);
                        String tempScan;
                        while (scan.hasNext()) {
                            tempScan = scan.nextLine();
                            if (tempScan.contains("<tr data-type=\"ModContainer\">")) {
                                for (int i = 0; i <= 8; i++) {
                                    if (i == 0) {
                                        System.out.println("READ INSIDE THE IF: " + tempScan);
                                        modaFileBody.add(tempScan);
                                    } else {
                                        System.out.println("READ INSIDE THE IF: " + scan.nextLine());
                                        modaFileBody.add(scan.nextLine());
                                    }
                                }
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    modaFileBody.forEach(System.out::println);
                }
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNewButton_1.setBounds(10, 166, 131, 23);
        panel.add(btnNewButton_1);


        JButton btnNewButton_1_1 = new JButton("Import Modset B\r\n");
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //modB.showSaveDialog(null);
                //txtpnModsetB.setText(modB.getSelectedFile().getAbsolutePath());
                JFileChooser modB = new JFileChooser();
                int openResult = modB.showOpenDialog(null);
                if (openResult == JFileChooser.APPROVE_OPTION) {
                    txtpnModsetB.setText(modB.getSelectedFile().getAbsolutePath());
                    File modaFile = new File(modB.getSelectedFile().getAbsolutePath());
                    System.out.println(modaFile);
                    try {
                        Scanner scan = new Scanner(modaFile);
                        String tempScan;
                        while (scan.hasNext()) {
                            tempScan = scan.nextLine();
                            if (tempScan.contains("<tr data-type=\"ModContainer\">")) {
                                for (int i = 0; i <= 8; i++) {
                                    if (i == 0) {
                                        System.out.println("READ INSIDE THE IF: " + tempScan);
                                        modbFileBody.add(tempScan);
                                    } else {
                                        String tempScan2 = scan.nextLine();
                                        System.out.println("READ INSIDE THE IF: " + tempScan2);
                                        modbFileBody.add(tempScan2);
                                    }
                                }
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    modbFileBody.forEach(System.out::println);
                }
            }
        });
        btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNewButton_1_1.setBounds(10, 232, 131, 23);
        panel.add(btnNewButton_1_1);



        JButton btnNewButton = new JButton("Export Modlist");
        btnNewButton.setBounds(10, 298, 159, 38);
        panel.add(btnNewButton);
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser modNew = new JFileChooser();
                modNew.showSaveDialog(null);
            }
        });

        JPanel panel_1 = new JPanel();
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        JList list = new JList();
        list.setBorder(new LineBorder(new Color(0, 0, 0)));
        list.setBounds(10, 73, 411, 399);
        panel_1.add(list);

        JLabel lblNewLabel_1 = new JLabel("New Modlist");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(178, 10, 82, 35);
        panel_1.add(lblNewLabel_1);
    }
}
