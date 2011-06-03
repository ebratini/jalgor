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

/*
 * JalgorGM.java
 *
 * Created on May 29, 2011, 6:08:14 PM
 */
package org.uasd.jalgor.ui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.uasd.jalgor.business.InterpreterError;
import org.uasd.jalgor.business.InvalidFileNameException;
import org.uasd.jalgor.business.JalgorInterpreter;
import org.uasd.jalgor.model.FileManager;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class JalgorGM extends javax.swing.JFrame {

    /** Creates new form JalgorGM */
    public JalgorGM() {
        setLocationByPlatform(true);
        initComponents();
        setLafActionHandler();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrLaf = new javax.swing.ButtonGroup();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        pnlBotonesAccion = new javax.swing.JPanel();
        btnCompilar = new javax.swing.JButton();
        btnIndentar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtaSalidaJalgor = new javax.swing.JTextArea();
        lblSalidaJalgor = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        pnlSourceFile = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaSourceFile = new javax.swing.JTextArea();
        txtSourceFilePath = new javax.swing.JTextField();
        btnBuscarSrcFilePath = new javax.swing.JButton();
        pnlOutFile = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaOutFile = new javax.swing.JTextArea();
        txtOutFilePath = new javax.swing.JTextField();
        btnBuscarOutFilePath = new javax.swing.JButton();
        mnuMenuBar = new javax.swing.JMenuBar();
        mnuJalgor = new javax.swing.JMenu();
        mnuCompilar = new javax.swing.JMenuItem();
        mnuIndentar = new javax.swing.JMenuItem();
        mnuLimpiarCampos = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuSalir = new javax.swing.JMenuItem();
        mnuLaf = new javax.swing.JMenu();
        rdbWindowsLaf = new javax.swing.JRadioButtonMenuItem();
        rdbNimbusLaf = new javax.swing.JRadioButtonMenuItem();
        rdbMetalLaf = new javax.swing.JRadioButtonMenuItem();
        mnuInfo = new javax.swing.JMenu();
        mnuAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jalgor: Algorithm Interpreter");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/images/traductor_165x165.png")));
        setResizable(false);

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 814, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel))
                .addGap(3, 3, 3))
        );

        pnlBotonesAccion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCompilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/traductor_55x55.png"))); // NOI18N
        btnCompilar.setToolTipText("Compilar Archivo Fuente");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        btnIndentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/code-indent_35x35.png"))); // NOI18N
        btnIndentar.setToolTipText("Indentar Salida");
        btnIndentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndentarActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/escoba_35x35.png"))); // NOI18N
        btnLimpiar.setToolTipText("Limpiar Campos");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/salir2_35x35.png"))); // NOI18N
        btnSalir.setToolTipText("Salir de Aplicacion");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBotonesAccionLayout = new javax.swing.GroupLayout(pnlBotonesAccion);
        pnlBotonesAccion.setLayout(pnlBotonesAccionLayout);
        pnlBotonesAccionLayout.setHorizontalGroup(
            pnlBotonesAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesAccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBotonesAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBotonesAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, Short.MAX_VALUE)
                        .addComponent(btnIndentar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, Short.MAX_VALUE))
                    .addComponent(btnLimpiar, 0, 0, Short.MAX_VALUE)
                    .addComponent(btnSalir, 0, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBotonesAccionLayout.setVerticalGroup(
            pnlBotonesAccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesAccionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIndentar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(340, Short.MAX_VALUE))
        );

        jtaSalidaJalgor.setColumns(20);
        jtaSalidaJalgor.setEditable(false);
        jtaSalidaJalgor.setRows(5);
        jScrollPane3.setViewportView(jtaSalidaJalgor);

        lblSalidaJalgor.setText("Salida");

        jSplitPane1.setResizeWeight(1.0);

        pnlSourceFile.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlSourceFile.setPreferredSize(new java.awt.Dimension(347, 348));

        jtaSourceFile.setColumns(20);
        jtaSourceFile.setRows(5);
        jScrollPane1.setViewportView(jtaSourceFile);

        txtSourceFilePath.setText("path archivo fuente");

        btnBuscarSrcFilePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/searchglass_vflip.gif"))); // NOI18N
        btnBuscarSrcFilePath.setBorder(null);
        btnBuscarSrcFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarSrcFilePathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSourceFileLayout = new javax.swing.GroupLayout(pnlSourceFile);
        pnlSourceFile.setLayout(pnlSourceFileLayout);
        pnlSourceFileLayout.setHorizontalGroup(
            pnlSourceFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSourceFileLayout.createSequentialGroup()
                .addComponent(txtSourceFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarSrcFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
        );
        pnlSourceFileLayout.setVerticalGroup(
            pnlSourceFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSourceFileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSourceFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarSrcFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSourceFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(pnlSourceFile);

        pnlOutFile.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlOutFile.setPreferredSize(new java.awt.Dimension(372, 350));

        jtaOutFile.setColumns(20);
        jtaOutFile.setRows(5);
        jScrollPane2.setViewportView(jtaOutFile);

        txtOutFilePath.setText("path archivo salida");
        txtOutFilePath.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOutFilePathKeyPressed(evt);
            }
        });

        btnBuscarOutFilePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/searchglass_vflip.gif"))); // NOI18N
        btnBuscarOutFilePath.setBorder(null);
        btnBuscarOutFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarOutFilePathActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlOutFileLayout = new javax.swing.GroupLayout(pnlOutFile);
        pnlOutFile.setLayout(pnlOutFileLayout);
        pnlOutFileLayout.setHorizontalGroup(
            pnlOutFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOutFileLayout.createSequentialGroup()
                .addComponent(txtOutFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarOutFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
        );
        pnlOutFileLayout.setVerticalGroup(
            pnlOutFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOutFileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOutFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarOutFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOutFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(pnlOutFile);

        mnuJalgor.setText("Jalgor");

        mnuCompilar.setText("Compilar");
        mnuCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCompilarActionPerformed(evt);
            }
        });
        mnuJalgor.add(mnuCompilar);

        mnuIndentar.setText("Indentar Salida");
        mnuIndentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuIndentarActionPerformed(evt);
            }
        });
        mnuJalgor.add(mnuIndentar);

        mnuLimpiarCampos.setText("Limpiar Campos");
        mnuLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLimpiarCamposActionPerformed(evt);
            }
        });
        mnuJalgor.add(mnuLimpiarCampos);
        mnuJalgor.add(jSeparator1);

        mnuSalir.setText("Salir");
        mnuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalirActionPerformed(evt);
            }
        });
        mnuJalgor.add(mnuSalir);

        mnuMenuBar.add(mnuJalgor);

        mnuLaf.setText("L&F");

        bgrLaf.add(rdbWindowsLaf);
        rdbWindowsLaf.setSelected(true);
        rdbWindowsLaf.setText("Windows");
        rdbWindowsLaf.setName("windows"); // NOI18N
        mnuLaf.add(rdbWindowsLaf);

        bgrLaf.add(rdbNimbusLaf);
        rdbNimbusLaf.setText("Nimbus");
        rdbNimbusLaf.setName("nimbus"); // NOI18N
        mnuLaf.add(rdbNimbusLaf);

        bgrLaf.add(rdbMetalLaf);
        rdbMetalLaf.setText("Metal");
        rdbMetalLaf.setName("metal"); // NOI18N
        mnuLaf.add(rdbMetalLaf);

        mnuMenuBar.add(mnuLaf);

        mnuInfo.setText("?");

        mnuAbout.setText("About");
        mnuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAboutActionPerformed(evt);
            }
        });
        mnuInfo.add(mnuAbout);

        mnuMenuBar.add(mnuInfo);

        setJMenuBar(mnuMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSalidaJalgor)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.LEADING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBotonesAccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSalidaJalgor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlBotonesAccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarOutFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarOutFilePathActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("archivo cpp [*.cpp]", "cpp"));

        chooser.setSelectedFile(new File("out.cpp"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            txtOutFilePath.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_btnBuscarOutFilePathActionPerformed

    private void mnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_mnuSalirActionPerformed

    private void mnuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAboutActionPerformed
        // TODO add your handling code here:
        JalgorAbout ja = new JalgorAbout(this, true);
        ja.setLocationRelativeTo(this);
        ja.setVisible(true);
    }//GEN-LAST:event_mnuAboutActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void mnuLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLimpiarCamposActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_mnuLimpiarCamposActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        // TODO add your handling code here:
        compilar(txtSourceFilePath.getText(), txtOutFilePath.getText());
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void setLafActionHandler() {
        ActionListener lafActionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LookAndFeelSelector.setLookAndFeel(LookAndFeelSelector.LAF.valueOf(e.getActionCommand().toUpperCase()));
                SwingUtilities.updateComponentTreeUI(JalgorGM.this);
            }
        };
        rdbMetalLaf.addActionListener(lafActionListener);
        rdbNimbusLaf.addActionListener(lafActionListener);
        rdbWindowsLaf.addActionListener(lafActionListener);
    }

    private void btnBuscarSrcFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarSrcFilePathActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("archivo algor [*.algor]", "algor"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            txtSourceFilePath.setText(chooser.getSelectedFile().getAbsolutePath());
            jtaSourceFile.setText(FileManager.loadFile(new File(txtSourceFilePath.getText())).toString());
        }
}//GEN-LAST:event_btnBuscarSrcFilePathActionPerformed

    private void mnuCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCompilarActionPerformed
        // TODO add your handling code here:
        compilar(txtSourceFilePath.getText(), txtOutFilePath.getText());
    }//GEN-LAST:event_mnuCompilarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtOutFilePathKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOutFilePathKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnCompilar.doClick();
        }
    }//GEN-LAST:event_txtOutFilePathKeyPressed

    private void mnuIndentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuIndentarActionPerformed
        // TODO add your handling code here:
        indentarSalida("java");
    }//GEN-LAST:event_mnuIndentarActionPerformed

    private void btnIndentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndentarActionPerformed
        // TODO add your handling code here:
        indentarSalida("java");
    }//GEN-LAST:event_btnIndentarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new JalgorGM().setVisible(true);
            }
        });
    }

    private void limpiar() {
        txtSourceFilePath.setText("path archivo fuente");
        txtOutFilePath.setText("path archivo salida");
        jtaSourceFile.setText("");
        jtaOutFile.setText("");
        jtaSalidaJalgor.setText("");
    }

    private void indentarSalida(final String style) {
        if (txtOutFilePath.getText().length() < 1) {
            jtaSalidaJalgor.setText("indique ruta archivo a indentar\n");
            return;
        }
        if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
            jtaSalidaJalgor.setText("code styler solo probado para windows\n");
            return;
        }
        new Thread() {

            @Override
            public void run() {
                String styler = null;
                String pathFileToStyle = txtOutFilePath.getText();
                File outFile = new File(pathFileToStyle);

                if (!outFile.exists()) {
                    jtaSalidaJalgor.setText("no existe tal archivo\n");
                    return;
                }

                String osPath = System.getProperty("java.library.path");
                if (osPath.toLowerCase().contains("astyle")) {
                    styler = "astyle.exe";
                } else {
                    try {
                        styler = getClass().getResource("/resources/utils/astyle/bin/AStyle.exe").toURI().getPath();
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(JalgorGM.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (styler == null) {
                    jtaSalidaJalgor.setText("code styler no encontrado\n");
                    return;
                }
                
                try {
                    Runtime.getRuntime().exec(String.format("%s --style=%s -p -H \"%s\"", styler, style, pathFileToStyle)).waitFor();
                    jtaOutFile.setText(FileManager.loadFile(new File(txtOutFilePath.getText())).toString());
                } catch (InterruptedException ex) {
                    Logger.getLogger(JalgorGM.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ioe) {
                    Logger.getLogger(JalgorGM.class.getName()).log(Level.SEVERE, null, ioe);
                }
            }
        }.start();
    }

    private void compilar(String sourceFilePath, String outFilePath) {
        try {
            jtaSourceFile.setText(FileManager.loadFile(new File(txtSourceFilePath.getText())).toString());
            jtaOutFile.setText("");
            
            JalgorInterpreter ji = new JalgorInterpreter(sourceFilePath, outFilePath);
            ji.start();
            if (ji.getErrores().size() > 0 || ji.hayErrorEnLineaCodigo()) {
                StringBuilder sbErrores = new StringBuilder();
                for (InterpreterError ie : ji.getErrores()) {
                    sbErrores.append(ie.getMensaje()).append(System.getProperty("line.separator"));
                }
                if (sbErrores.length() > 0) {
                    sbErrores.append(System.getProperty("line.separator"));
                }
                sbErrores.append(ji.getCodeLineErrors());
                jtaSalidaJalgor.setText(sbErrores.toString());
                return;
            }
            jtaOutFile.setText(FileManager.loadFile(new File(outFilePath)).toString());
            jtaSalidaJalgor.setText("Success!");
        } catch (InvalidFileNameException ex) {
            Logger.getLogger(JalgorGM.class.getName()).log(Level.SEVERE, null, ex);
            jtaSalidaJalgor.setText(ex.getMessage());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrLaf;
    private javax.swing.JButton btnBuscarOutFilePath;
    private javax.swing.JButton btnBuscarSrcFilePath;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnIndentar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextArea jtaOutFile;
    private javax.swing.JTextArea jtaSalidaJalgor;
    private javax.swing.JTextArea jtaSourceFile;
    private javax.swing.JLabel lblSalidaJalgor;
    private javax.swing.JMenuItem mnuAbout;
    private javax.swing.JMenuItem mnuCompilar;
    private javax.swing.JMenuItem mnuIndentar;
    private javax.swing.JMenu mnuInfo;
    private javax.swing.JMenu mnuJalgor;
    private javax.swing.JMenu mnuLaf;
    private javax.swing.JMenuItem mnuLimpiarCampos;
    private javax.swing.JMenuBar mnuMenuBar;
    private javax.swing.JMenuItem mnuSalir;
    private javax.swing.JPanel pnlBotonesAccion;
    private javax.swing.JPanel pnlOutFile;
    private javax.swing.JPanel pnlSourceFile;
    private javax.swing.JRadioButtonMenuItem rdbMetalLaf;
    private javax.swing.JRadioButtonMenuItem rdbNimbusLaf;
    private javax.swing.JRadioButtonMenuItem rdbWindowsLaf;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JTextField txtOutFilePath;
    private javax.swing.JTextField txtSourceFilePath;
    // End of variables declaration//GEN-END:variables
}
