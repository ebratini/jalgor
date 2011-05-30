/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.uasd.jalgor.ui;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class LookAndFeelSelector {
    public enum LAF {
        NIMBUS, METAL, CDE_MOTIF, WINDOWS, CLASIC_WINDOWS
    }

    public static void setLookAndFeel(LAF laf) {
        String strLaf = "Windows";
        switch (laf) {
            case NIMBUS:
                strLaf = "Nimbus";
                break;
            case CDE_MOTIF:
                strLaf = "CDE/Motif";
                break;
            case CLASIC_WINDOWS:
                strLaf = "Windows Classic";
                break;
            case METAL:
                strLaf = "Metal";
                break;
            case WINDOWS:
                strLaf = "Windows";
                break;
        }

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (strLaf.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
            e.printStackTrace();
        }
    }

    public static StringBuilder getAvailableLAF() {
        // imprimiendo available laf
        StringBuilder sbAvailableLaf = new StringBuilder();
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            sbAvailableLaf.append(info.getName()).append("\n");
        }

        return sbAvailableLaf;
    }

    public static void printAvailableLAF() {
        System.out.println(getAvailableLAF());
    }
}
