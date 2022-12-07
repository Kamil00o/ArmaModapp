package main;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class DataContainer {
    public boolean dataMerge;
    public boolean dataSubstracting;

    DataContainer() {

    }

    public void setDataMerge(boolean varState) {
        this.dataMerge = varState;
    }

    public void setDataSubstracting(boolean varState) {
        this.dataSubstracting = varState;
    }

    public boolean getDataMerge() {
        return this.dataMerge;
    }

    public boolean getDataSubstracting() {
        return this.dataSubstracting;
    }

    public void modImport(List<String> modFileBody, JTextPane txtpnModsetB) {
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
