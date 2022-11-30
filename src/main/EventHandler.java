package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class EventHandler extends Main implements ActionListener{
    List<String> modaFileBody = new LinkedList<>();
    List<String> modbFileBody = new LinkedList<>();
    List<String> modNames = new LinkedList<>();
    String newModFileName = "New Mod";

    public void actionPerformed(ActionEvent event) {
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

    public void getInternalFields() {

    }
}
