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
 * JalgorAbout.java
 *
 * Created on 05/30/2011, 12:07:08 PM
 */

package org.uasd.jalgor.ui;

import java.awt.Toolkit;

/**
 *
 * @author Edwin Bratini <edwin.bratini@gmail.com>
 */
public class JalgorAbout extends javax.swing.JDialog {

    /** Creates new form JalgorAbout */
    public JalgorAbout(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAppLogo = new javax.swing.JLabel();
        javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
        javax.swing.JLabel appDescLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel appDescLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel lblAuthorTtl = new javax.swing.JLabel();
        javax.swing.JLabel lblContributorsTtl = new javax.swing.JLabel();
        javax.swing.JLabel lblAuthor = new javax.swing.JLabel();
        javax.swing.JLabel lblContributors = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        javax.swing.JLabel lblLicenseTtl = new javax.swing.JLabel();
        javax.swing.JLabel lblLicense = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel lblLicenseTtl1 = new javax.swing.JLabel();
        javax.swing.JLabel lblAuthor1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About: Jalgor");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/images/traductor_165x165.png")));
        setResizable(false);

        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize()+4));
        appTitleLabel.setText("Jalgor");

        appDescLabel2.setText("<html>Basic algorithm interpreter that produce .cpp files ready to compile.</html>");

        appDescLabel1.setText("Algorithm Interpreter");

        lblAuthorTtl.setFont(lblAuthorTtl.getFont().deriveFont(lblAuthorTtl.getFont().getStyle() | java.awt.Font.BOLD));
        lblAuthorTtl.setText("Author:");

        lblContributorsTtl.setFont(lblContributorsTtl.getFont().deriveFont(lblContributorsTtl.getFont().getStyle() | java.awt.Font.BOLD));
        lblContributorsTtl.setText("Contributors:");

        lblAuthor.setText("Edwin Bratini <edwin.bratini@gmail.com>");

        lblContributors.setText("<html>Rafael Recio &lt;rafaelreciog@gmail.com&gt;<br>Eury Martinez &lt;eury.martinez@gmail.com&gt;</html>");

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        lblLicenseTtl.setFont(lblLicenseTtl.getFont().deriveFont(lblLicenseTtl.getFont().getStyle() | java.awt.Font.BOLD));
        lblLicenseTtl.setText("License:");

        lblLicense.setText("<html>MIT License<br>http://www.opensource.org/licenses/mit-license.php</html>");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/traductor_165x165.png"))); // NOI18N

        lblLicenseTtl1.setFont(lblLicenseTtl1.getFont().deriveFont(lblLicenseTtl1.getFont().getStyle() | java.awt.Font.BOLD));
        lblLicenseTtl1.setText("Repo:");

        lblAuthor1.setText("http://code.google.com/p/jalgor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAppLogo)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAuthorTtl)
                                    .addComponent(lblContributorsTtl)
                                    .addComponent(lblLicenseTtl)
                                    .addComponent(lblLicenseTtl1))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblLicense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblContributors, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                    .addComponent(lblAuthor, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                    .addComponent(lblAuthor1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)))
                            .addComponent(appDescLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(appTitleLabel)
                            .addComponent(appDescLabel1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(489, Short.MAX_VALUE)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(appTitleLabel)
                        .addGap(1, 1, 1)
                        .addComponent(appDescLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(appDescLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAuthorTtl)
                            .addComponent(lblAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContributorsTtl)
                            .addComponent(lblContributors, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLicense, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLicenseTtl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAuthor1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLicenseTtl1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAppLogo)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JalgorAbout dialog = new JalgorAbout(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAppLogo;
    // End of variables declaration//GEN-END:variables

}
