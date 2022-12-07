package main;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.awt.Toolkit;

public class Main {

	public JFrame preset;
	boolean fillList = false;
	JFormattedTextField newModfileName;

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
		DataContainer dataContainer = new DataContainer();
		initialize(dataContainer);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(DataContainer dataContainer) {
		List<String> modaFileBody = new LinkedList<>();
		List<String> modbFileBody = new LinkedList<>();
		preset = new JFrame();
		preset.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("FKYoutubePatch.png")));
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
				dataContainer.setDataMerge(true);
				dataContainer.setDataSubstracting(false);
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
				dataContainer.setDataMerge(false);
				dataContainer.setDataSubstracting(true);
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
				dataContainer.modImport(modaFileBody, txtpnModsetA);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(10, 102, 159, 23);
		panel.add(btnNewButton_1);


		JButton btnNewButton_1_1 = new JButton("Import Modset B\r\n");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataContainer.modImport(modbFileBody, txtpnModsetB);
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

		newModfileName = new JFormattedTextField();
		newModfileName.setToolTipText("Name of the modset that will apear in the Arma 3 launcher");
		newModfileName.setText("New Preset");
		newModfileName.setBounds(20, 263, 280, 23);
		panel.add(newModfileName);

		JLabel lblNewLabel_1 = new JLabel(new ImageIcon(AssetLoader.loadImage("background.jpg")));
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(0, 0, 398, 420);
		panel.add(lblNewLabel_1);

		btnNewButton.addActionListener(new EventHandler(fillList, newModfileName, preset, modaFileBody, modbFileBody, dataContainer) {
			@Override
			public void actionPerformed(ActionEvent e) {
				super.actionPerformed(e);
			}
		});
	}
}