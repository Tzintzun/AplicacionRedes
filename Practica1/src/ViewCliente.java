import java.io.File;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Diana Paola
 */
public class ViewCliente extends javax.swing.JFrame {
    private Cliente cliente = new Cliente();
    private File[] archivos = new File[0];
    /**
     * Creates new form ViewCliente
     */
    public ViewCliente() {
        initComponents();
    }
    
    public void mostrarArchivos(File[] f1,String tabulacion){
        
        for(File f: f1){
            if(f.isDirectory()){
                this.jTextAreaFilesSelected.append(tabulacion+f.getName()+"\\ \n");
                mostrarArchivos(f.listFiles(),tabulacion+"  ");
            }else{
                this.jTextAreaFilesSelected.append(tabulacion+f.getName()+"\n");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonSelectFiles = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaFilesSelected = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButtonSelectFilesServer = new javax.swing.JButton();
        jButtonSendFiles = new javax.swing.JButton();
        jButtonDeleteFiles = new javax.swing.JButton();
        jButtonCreateDirectory = new javax.swing.JButton();
        jButtonDownloadFiles = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Drive simulator ");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel2.setText("Drive simulator");

        jButtonSelectFiles.setText("Seleccionar archivos...");
        jButtonSelectFiles.setToolTipText("");
        jButtonSelectFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectFilesActionPerformed(evt);
            }
        });

        jTextAreaFilesSelected.setColumns(20);
        jTextAreaFilesSelected.setRows(5);
        jScrollPane1.setViewportView(jTextAreaFilesSelected);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButtonSelectFilesServer.setText("Seleccionar archivos del servidor");

        jButtonSendFiles.setText("Subir archivos");
        jButtonSendFiles.setToolTipText("");
        jButtonSendFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendFilesActionPerformed(evt);
            }
        });

        jButtonDeleteFiles.setText("Eliminar archivos");
        jButtonDeleteFiles.setToolTipText("");

        jButtonCreateDirectory.setText("Crear nueva carpeta");

        jButtonDownloadFiles.setText("Descargar archivos de servidor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jButtonSelectFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonSelectFilesServer)
                .addGap(55, 55, 55))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jButtonCreateDirectory)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonDownloadFiles)
                        .addGap(24, 24, 24)))
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(204, 204, 204))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jButtonSendFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonDeleteFiles)
                .addGap(88, 88, 88))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSelectFiles)
                    .addComponent(jButtonSelectFilesServer))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonDeleteFiles)
                    .addComponent(jButtonSendFiles))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCreateDirectory)
                    .addComponent(jButtonDownloadFiles))
                .addGap(48, 48, 48))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSelectFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectFilesActionPerformed
        JFileChooser jf = new JFileChooser();
        jf.setMultiSelectionEnabled(true);
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int r = jf.showOpenDialog(null);
           
        if(r == JFileChooser.APPROVE_OPTION){
            File[] f = jf.getSelectedFiles();
            this.archivos=f;
            this.jTextAreaFilesSelected.setText("");
            mostrarArchivos(f,"");
            //cliente.enviar_archivos(f);
            
        }
    }//GEN-LAST:event_jButtonSelectFilesActionPerformed

    private void jButtonSendFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendFilesActionPerformed
        if(archivos.length == 0){
            this.jTextAreaFilesSelected.setText(null);
            this.jTextAreaFilesSelected.setText("No hay archivos seleccionados para enviar");
        }else{
            cliente.enviar_archivos(archivos);
            archivos=new File[0];
            System.gc();
        }
    }//GEN-LAST:event_jButtonSendFilesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewCliente().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCreateDirectory;
    private javax.swing.JButton jButtonDeleteFiles;
    private javax.swing.JButton jButtonDownloadFiles;
    private javax.swing.JButton jButtonSelectFiles;
    private javax.swing.JButton jButtonSelectFilesServer;
    private javax.swing.JButton jButtonSendFiles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextAreaFilesSelected;
    // End of variables declaration//GEN-END:variables
}
