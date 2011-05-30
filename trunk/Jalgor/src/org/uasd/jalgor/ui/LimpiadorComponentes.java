/*
 *  The MIT License
 * 
 *  Copyright 2011 Edwin Bratini <edwin.bratini@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.uasd.jalgor.ui;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public abstract class LimpiadorComponentes {

    public static void limpiar(Component comp) {
        if (comp instanceof JTextComponent) {
            ((JTextComponent) comp).setText("");
        } else if (comp instanceof JComboBox && ((JComboBox) comp).getItemCount() >= 1) {
            ((JComboBox) comp).setSelectedIndex(0);
        }
    }

    public static void limpiarComponentes(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextComponent || comp instanceof JComboBox) {
                limpiar(comp);
            } else if (comp instanceof Container) {
                limpiarComponentes((Container) comp);
            }
        }
    }

    public static void limpiarValidationMarkers(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().equalsIgnoreCase("*")) {
                //((JLabel) comp).setText("");
                ((JLabel) comp).setVisible(false);
            } else if (comp instanceof Container) {
                limpiarValidationMarkers((Container) comp);
            }
        }
    }
}
