
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class dbarangm extends javax.swing.JFrame {

    /**
     * Creates new form dbarangm
     */
    public dbarangm() {
        initComponents();
        load_table();     // Load data into your table (if applicable)
        this.nbarang = nbarang;
        datacombobox();
         nbarang.addActionListener(e -> getIdBarang());
    }

 public class Item {
        private int id;
        private String name;

        public Item(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
      public void datacombobox() {
        try {
            Connection conn = koneksi.configDB(); // Memanggil koneksi
            String query = "SELECT nama_barang FROM barang";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String namaBarang = rs.getString("nama_barang");
                nbarang.addItem(namaBarang);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private void getIdBarang() {
    String selectedNamaBarang = (String) nbarang.getSelectedItem();
    if (selectedNamaBarang != null) {
        try {
            Connection conn = koneksi.configDB();
            String query = "SELECT id_barang FROM barang WHERE nama_barang = ?";
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, selectedNamaBarang);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idBarang = rs.getInt("id_barang");
                // You can now use idBarang as needed
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


     private void kosong(){
        nbarang.setSelectedItem(null);
        stok.setText(null);
        idbarang.setText(null);
    }
    
    private void load_table(){
    // membuat tampilan model tabel
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id barang");
    model.addColumn("Nama");
    model.addColumn("Kode");
    model.addColumn("Harga Jual");
    model.addColumn("Jumlah");
    
    try{

        String sql = "select barangmasuk.id_bmasuk, barang.nama_barang, barang.kode_barang, barang.harga_jual,"
                + "barangmasuk.quantity FROM barangmasuk join barang on barangmasuk.id_barang = barang.id_barang";
        java.sql.Connection conn=(Connection)koneksi.configDB();
        java.sql.Statement stm=conn.createStatement();
        java.sql.ResultSet res=stm.executeQuery(sql);
        while(res.next()){
            model.addRow(new Object[]{res.getString(1),res.getString(2),
            res.getString(3),res.getString(4), res.getString(5)});
        }
        data_barang.setModel(model);
    }catch (Exception e){
}
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        stok = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        idbarang = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        nbarang = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        data_barang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Nama Barang");

        stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stokActionPerformed(evt);
            }
        });

        jLabel6.setText("Jumlah");

        idbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idbarangActionPerformed(evt);
            }
        });

        jButton2.setText("Tambah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Hapus");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        nbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nbarangActionPerformed(evt);
            }
        });

        data_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        data_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                data_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(data_barang);

        jLabel1.setText("<-Back");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(nbarang, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(stok, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(idbarang, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton4)
                            .addComponent(nbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(idbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stokActionPerformed

    private void idbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idbarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idbarangActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // Retrieve the selected nama_barang from the JComboBox
            String selectedNamaBarang = (String) nbarang.getSelectedItem();
            int idBarang = 0;

            if (selectedNamaBarang != null) {
                // Call getIdBarang method to get the id_barang based on selected nama_barang
                try {
                    Connection conn = koneksi.configDB();
                    String query = "SELECT id_barang FROM barang WHERE nama_barang = ?";
                    PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
                    stmt.setString(1, selectedNamaBarang);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        idBarang = rs.getInt("id_barang");
                    }

                    rs.close();
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item.");
                return; // Exit if no item is selected
            }

            // Continue with the original process of inserting data
            String sqlAnak = "INSERT INTO barangmasuk (id_barang, quantity) VALUES (?, ?)";
            Connection conn = koneksi.configDB();
            PreparedStatement pstAnak = (PreparedStatement) conn.prepareStatement(sqlAnak);

            // Set the id_barang in the PreparedStatement
            pstAnak.setInt(1, idBarang);

            // Set nilai untuk kolom stok
            pstAnak.setString(2, stok.getText());

            // Execute the update
            pstAnak.executeUpdate();
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        kosong();
        load_table();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       try {
    // Retrieve the selected nama_barang from the JComboBox
    String selectedNamaBarang = (String) nbarang.getSelectedItem();
    int idBarang = 0;

    if (selectedNamaBarang != null) {
        // Call getIdBarang method to get the id_barang based on selected nama_barang
        try {
            Connection conn = koneksi.configDB();
            String query = "SELECT id_barang FROM barang WHERE nama_barang = ?";
            PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query);
            stmt.setString(1, selectedNamaBarang);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idBarang = rs.getInt("id_barang");
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving id_barang: " + e.getMessage());
            return; // Exit if there's an error
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select an item.");
        return; // Exit if no item is selected
    }

    // Update the barangkeluar table using the retrieved id_barang
    String sql = "UPDATE barangmasuk SET id_barang = ?, quantity = ? WHERE id_bmasuk = ?";
    Connection conn = koneksi.configDB();
    PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);

    pst.setInt(1, idBarang); // Use the retrieved id_barang
    pst.setString(2, stok.getText()); // Set the stok value
    pst.setString(3, idbarang.getText()); // Use the existing id_bkeluar

    pst.executeUpdate();
    JOptionPane.showMessageDialog(null, "Data Berhasil di Update");

} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Perubahan Data Gagal: " + e.getMessage());
}

load_table();
kosong();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try{
            String sql = "delete from barangmasuk WHERE id_bmasuk='"+idbarang.getText()+"'";
            java.sql.Connection conn=(Connection)koneksi.configDB();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "Data Berhasil di Hapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        load_table();
        kosong();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void nbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nbarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nbarangActionPerformed

    private void data_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_barangMouseClicked
        int baris = data_barang.rowAtPoint(evt.getPoint());
       String idb =data_barang.getValueAt(baris, 4).toString();
        stok.setText(idb);
       String namabarang =data_barang.getValueAt(baris, 1).toString();
        nbarang.setSelectedItem(namabarang);
       String iddbarang =data_barang.getValueAt(baris, 0).toString();
       idbarang.setText(iddbarang);
    }//GEN-LAST:event_data_barangMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        this.dispose();
        new data().setVisible(true);
    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(dbarangm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dbarangm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dbarangm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dbarangm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dbarangm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable data_barang;
    private javax.swing.JTextField idbarang;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> nbarang;
    private javax.swing.JTextField stok;
    // End of variables declaration//GEN-END:variables
}
