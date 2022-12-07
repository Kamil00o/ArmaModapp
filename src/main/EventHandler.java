package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class EventHandler implements ActionListener {
    List<String> modaFileBody;
    List<String> modbFileBody;
    JFrame preset;
    boolean fillList;
    JFormattedTextField newModfileName;
    boolean merging;
    boolean substracting;
    DataContainer dataContainer;

    public EventHandler(boolean fillList, JFormattedTextField newModfileName, JFrame preset, List<String> modaFileBody, List<String> modbFileBody, DataContainer dataContainer) {
       this.preset = preset;
       this.newModfileName = newModfileName;
       this.dataContainer = dataContainer;
       this.fillList = fillList;
       this.modaFileBody = modaFileBody;
       this.modbFileBody = modbFileBody;
    }

    public void actionPerformed(ActionEvent event) {
        this.substracting = dataContainer.getDataSubstracting();
        this.merging = dataContainer.getDataMerge();

        fillList = true;
        System.out.println("Creating a new Mod File...");
        JFileChooser modNew = new JFileChooser();
        int saveResult = modNew.showSaveDialog(null);
        exit_method:
        if (saveResult == JFileChooser.APPROVE_OPTION) {
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
                            } else if (tempLine.contains("<meta name=\"arma:PresetName\" content=\"\" />")) {
                                System.out.println("New Modset name: " + newModfileName.getText());
                                newModFile.write("<meta name=\"arma:PresetName\" content=\"" + newModfileName.getText() + "\" />" + "\n");
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
                            } else if (tempLine.contains("<meta name=\"arma:PresetName\" content=\"\" />")) {
                                System.out.println("New Modset name: " + newModfileName.getText());
                                newModFile.write("<meta name=\"arma:PresetName\" content=\"" + newModfileName.getText() + "\" />" + "\n");
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

    public void getInternalFields() {

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
}
