import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.ToDoubleBiFunction;

public class Main {

    private JFrame frame;
    private JTextField textField;
    boolean merging;
    boolean substracting;
    String newModsetFileName;
    boolean fillList = false;

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
        List<String> modNames = new LinkedList<>();
        String newModFileName = "New Mod";
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


        JRadioButton rdbtnMerge = new JRadioButton("Merge\r\n");
        rdbtnMerge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                substracting = false;
                merging = true;
            }
        });
        rdbtnMerge.setFont(new Font("Tahoma", Font.BOLD, 12));
        rdbtnMerge.setBounds(20, 39, 149, 23);
        panel.add(rdbtnMerge);

        JRadioButton rdbtnSubstract = new JRadioButton("Substract");
        rdbtnSubstract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                merging = false;
                substracting = true;
            }
        });
        rdbtnSubstract.setFont(new Font("Tahoma", Font.BOLD, 12));
        rdbtnSubstract.setBounds(20, 64, 149, 23);
        panel.add(rdbtnSubstract);

        ButtonGroup group = new ButtonGroup();
        group.add(rdbtnMerge);
        group.add(rdbtnSubstract);

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
                modImport(modaFileBody, txtpnModsetA);
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNewButton_1.setBounds(10, 166, 131, 23);
        panel.add(btnNewButton_1);


        JButton btnNewButton_1_1 = new JButton("Import Modset B\r\n");
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modImport(modbFileBody, txtpnModsetB);
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
                newModsetFileName = textField.getText();
                fillList = true;
                if (newModsetFileName.equals("")) {
                    newModsetFileName = "DefaultModFile";
                }
                //newModFileName = newModsetFileName;
                System.out.println("Creating a new Mod File...");
                JFileChooser modNew = new JFileChooser();
                int saveResult = modNew.showSaveDialog(null);
                exit_method: if (saveResult == JFileChooser.APPROVE_OPTION) {
                    if (modaFileBody.isEmpty() || modbFileBody.isEmpty()) {
                        System.out.println("To create a new Mod file you have to specify two Mod files" +
                                " - Mod A and Mod B!");
                        break exit_method;
                    }
                    String newFilePath = modNew.getSelectedFile().getAbsolutePath();
                    System.out.println(saveResult);
                    System.out.println(newFilePath);
                    if (newFilePath.contains(".txt") || newFilePath.contains(".html")) {
                        if (merging) {
                            List<String> newModFileBody = new LinkedList<>();
                            String tempLine;
                            File templateFile = new File("./resources/template.txt");
                            try {
                                Scanner tmpScan = new Scanner(templateFile);
                                FileWriter newModFile = new FileWriter(modNew.getSelectedFile().getAbsolutePath());
                                while (tmpScan.hasNext()) {
                                    tempLine = tmpScan.nextLine();
                                    if (tempLine.contains("<table>")) {
                                        newModFile.write(tempLine + "\n");
                                        for (int i = 0; i < modaFileBody.size(); i++) {
                                            i = modBodyPrinter(modaFileBody, newModFile, tempLine, i);
                                        }
                                        for (int i = 0; i < modbFileBody.size(); i++) {
                                            if (modaFileBody.contains(modbFileBody.get(i))) {
                                                System.out.println("Removing duplicate: " + modbFileBody.get(i).
                                                        substring(38, modbFileBody.get(i++).length() - 5));
                                            } else {
                                                i = modBodyPrinter(modbFileBody, newModFile, tempLine, i);
                                            }
                                        }
                                    } else {
                                        newModFile.write(tempLine + "\n");

                                    }
                                }
                                newModFile.close();
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else if (substracting) {
                            List<String> newModFileBody = new LinkedList<>();
                            String tempLine;
                            File templateFile = new File("./resources/template.txt");
                            try {
                                Scanner tmpScan = new Scanner(templateFile);
                                FileWriter newModFile = new FileWriter(modNew.getSelectedFile().getAbsolutePath());
                                while (tmpScan.hasNext()) {
                                    tempLine = tmpScan.nextLine();
                                    if (tempLine.contains("<table>")) {
                                        newModFile.write(tempLine + "\n");
                                        for (int i = 0; i < modaFileBody.size(); i++) {
                                            if (modbFileBody.contains(modaFileBody.get(i))) {
                                                System.out.println("Removing Mod B file: " + modaFileBody.get(i).
                                                        substring(38, modaFileBody.get(i++).length() - 5));
                                            } else {
                                                i = modBodyPrinter(modaFileBody, newModFile, tempLine, i);
                                            }
                                        }
                                    } else {
                                        newModFile.write(tempLine + "\n");
                                    }
                                }
                                newModFile.close();
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            System.out.println("You have not selected what option you want to pick!" +
                                    "\nPlease select \"merge\" or \"substract\" option and try again.");
                        }
                    } else {
                        System.out.println("To export Mod file You need to save file with .html or .txt file extension!" +
                                "\nTry to export Mod file again!");
                    }
                } else {
                    System.out.println("Directory hasn't been choosen!! File hasn't been created!");
                }
            }
        });

        JPanel panel_1 = new JPanel();
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        DefaultListModel listModel = new DefaultListModel();

        JList list = new JList();
        list.setBorder(new LineBorder(new Color(0, 0, 0)));
        list.setBounds(10, 73, 411, 399);
        panel_1.add(list);
        list.setModel(listModel);


        JLabel lblNewLabel_1 = new JLabel(newModFileName + " body overview:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(160, 10, 350, 35);
        panel_1.add(lblNewLabel_1);
    }

    private int modBodyPrinter(List<String> modAorBBody, FileWriter newModFile, String tempLine, int i) throws IOException {
        newModFile.write("        <tr data-type=\"ModContainer\">");
        newModFile.write(modAorBBody.get(i++) + "\n");
        newModFile.write("          <td>");
        newModFile.write("            <span class=\"from-steam\">Steam</span>");
        newModFile.write("          </td>");
        newModFile.write("           <td>");
        newModFile.write(modAorBBody.get(i) + "\n");
        newModFile.write("          </td>");
        newModFile.write("        </tr>");
        return i;
    }

    private void modImport(List<String> modFileBody, JTextPane txtpnModsetB) {
        JFileChooser fromModFile = new JFileChooser();
        int openResult = fromModFile.showOpenDialog(null);
        if (openResult == JFileChooser.APPROVE_OPTION) {
            modFileBody.clear();
            txtpnModsetB.setText(fromModFile.getSelectedFile().getAbsolutePath());
            File modaFile = new File(fromModFile.getSelectedFile().getAbsolutePath());
            System.out.println(modaFile);
            try {
                Scanner scan = new Scanner(modaFile);
                String tempScan;
                while (scan.hasNext()) {
                    tempScan = scan.nextLine();
                    if (tempScan.contains("<td data-type=\"DisplayName\">") || tempScan.contains("<a href=")) {
                        System.out.println(tempScan);
                        modFileBody.add(tempScan);
                    }
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            System.out.println("Captured Mod body:");
            modFileBody.forEach(System.out::println);
        }
    }
}
