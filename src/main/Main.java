package main;
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
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.ToDoubleBiFunction;
import java.awt.Toolkit;

public class Main {

	public JFrame preset;
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
					window.preset.setVisible(true);
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
		preset = new JFrame();
		preset.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/main/FKYoutubePatch.png")));
		preset.setTitle("Arma Modset Editor");
		preset.setResizable(false);
		preset.setBounds(100, 100, 410, 455);
		preset.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		preset.getContentPane().setLayout(new BoxLayout(preset.getContentPane(), BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		preset.getContentPane().add(panel);
		panel.setLayout(null);

		JTextPane txtpnModsetA = new JTextPane();
		txtpnModsetA.setText("No modset selected");
		txtpnModsetA.setEditable(false);
		txtpnModsetA.setBounds(20, 135, 280, 23);
		panel.add(txtpnModsetA);

		JTextPane txtpnModsetB = new JTextPane();
		txtpnModsetB.setText("No modset selected");
		txtpnModsetB.setEditable(false);
		txtpnModsetB.setBounds(20, 201, 280, 23);
		panel.add(txtpnModsetB);


		JRadioButton rdbtnMerge = new JRadioButton("Merge\r\n");
		rdbtnMerge.setToolTipText("The program will merge the two modsets imported. ");
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
		rdbtnSubstract.setToolTipText("The program will remove mods in modset A that apear in modset B.");
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
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 10, 82, 23);
		panel.add(lblNewLabel);



		JButton btnNewButton_1 = new JButton("Import Modset A");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modImport(modaFileBody, txtpnModsetA);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(10, 102, 159, 23);
		panel.add(btnNewButton_1);


		JButton btnNewButton_1_1 = new JButton("Import Modset B\r\n");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modImport(modbFileBody, txtpnModsetB);
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1_1.setBounds(10, 168, 159, 23);
		panel.add(btnNewButton_1_1);



		JButton btnNewButton = new JButton("Export Modlist");
		btnNewButton.setToolTipText("When exporting remember to add a file name and .html as file extension. (ModsetName.html) ");
		btnNewButton.setBounds(10, 303, 159, 38);
		panel.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblModsetNameto = new JLabel("Preset Name");
		lblModsetNameto.setBackground(Color.WHITE);
		lblModsetNameto.setForeground(Color.BLACK);
		lblModsetNameto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblModsetNameto.setBounds(10, 235, 82, 23);
		panel.add(lblModsetNameto);

		JFormattedTextField newModfileName = new JFormattedTextField();
		newModfileName.setToolTipText("Name of the modset that will apear in the Arma 3 launcher");
		newModfileName.setText("New Preset");
		newModfileName.setBounds(20, 263, 280, 23);
		panel.add(newModfileName);

		JLabel lblNewLabel_1 = new JLabel(new ImageIcon(AssetLoader.loadImage("background.jpg")));
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(0, 0, 398, 420);
		panel.add(lblNewLabel_1);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillList = true;
				System.out.println("Creating a new Mod File...");
				JFileChooser modNew = new JFileChooser();
				int saveResult = modNew.showSaveDialog(null);
				exit_method: if (saveResult == JFileChooser.APPROVE_OPTION) {
					if (modaFileBody.isEmpty() || modbFileBody.isEmpty()) {
						JOptionPane.showMessageDialog(preset, "To create a new Mod file you have to specify two Mod files" +
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
							InputStream templateInputStream = getClass().getResourceAsStream("template.txt");
							try {
								Scanner tmpScan = new Scanner(templateInputStream);
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
									}
									else if (tempLine.contains("<meta name=\"arma:PresetName\" content=\"\" />")) {
										System.out.println("New Modset name: " + newModfileName.getText());
										newModFile.write("<meta name=\"arma:PresetName\" content=\"" + newModfileName.getText() + "\" />" + "\n");
									}
									else {
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
							InputStream templateInputStream = getClass().getResourceAsStream("template.txt");
							try {
								Scanner tmpScan = new Scanner(templateInputStream);
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
									}
									else if (tempLine.contains("<meta name=\"arma:PresetName\" content=\"\" />")) {
										System.out.println("New Modset name: "+ newModfileName.getText());
										newModFile.write("<meta name=\"arma:PresetName\" content=\"" + newModfileName.getText() + "\" />" + "\n");
									}
									else {
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
							JOptionPane.showMessageDialog(preset, "You have not selected what option you want to pick!" +
									"\nPlease select \"merge\" or \"substract\" option and try again.");
						}
					} else {
						JOptionPane.showMessageDialog(preset, "To export Mod file You need to save file with .html or .txt file extension!" +
								"\nTry to export Mod file again!");
					}
				} else {
					JOptionPane.showMessageDialog(preset, "Directory hasn't been choosen!! File hasn't been created!");
				}
			}
		});



		DefaultListModel listModel = new DefaultListModel();

	}
	///PUT IN A CLASS
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
	///PUT IN A CLASS
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