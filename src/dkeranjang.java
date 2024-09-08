import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class dkeranjang extends javax.swing.JFrame {

    /**
     * Creates new form dkeranjang
     */
    public dkeranjang() {
        initComponents();
        load_table();
        this.nbarang = nbarang;
        datacombobox();
         nbarang.addActionListener(e -> getIdBarang());
         this.kodekeranjang = kodekeranjang;
        datacombobox1();

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

public void datacombobox1() {
        try {
            Connection conn = koneksi.configDB(); // Memanggil koneksi
            String query = "SELECT kode_keranjang FROM keranjang GROUP BY kode_keranjang";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String namaBarang = rs.getString("kode_keranjang");
                kodekeranjang.addItem(namaBarang);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


private void kosong(){
        nbarang.setSelectedItem(null);
        quantitas.setText(null);
        id_barang.setText(null);

    }
        private void load_table(){
    // membuat tampilan model tabel
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id Keranjang");
    model.addColumn("Nama Barang");
    model.addColumn("Kode Keranjang");
    model.addColumn("Quantitas");
    
    try{

        String sql = "select keranjang.id_Keranjang, barang.nama_barang, keranjang.kode_keranjang, keranjang.quantity FROM keranjang join barang on keranjang.id_barang = barang.id_barang";
        java.sql.Connection conn=(Connection)koneksi.configDB();
        java.sql.Statement stm=conn.createStatement();
        java.sql.ResultSet res=stm.executeQuery(sql);
        while(res.next()){
            model.addRow(new Object[]{res.getString(1),res.getString(2),
            res.getString(3),res.getString(4)});
        }
        data_barang.setModel(model);
    }catch (Exception e){
}
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        data_barang = new javax.swing.JTable();
        nbarang = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        quantitas = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        id_barang = new javax.swing.JTextField();
        kodekeranjang = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jLabel1.setText("Nama Barang");

        jLabel2.setText("Quantitas");

        jLabel3.setText("Kode Keranjang");

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

        jLabel4.setText("<-Back");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(nbarang, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(quantitas, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(kodekeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nbarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quantitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton3)
                        .addComponent(jButton4)
                        .addComponent(kodekeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(26, 26, 26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(id_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            String sqlAnak = "INSERT INTO Keranjang (id_barang, quantity, kode_keranjang) VALUES (?, ?, ?)";
            Connection conn = koneksi.configDB();
            PreparedStatement pstAnak = (PreparedStatement) conn.prepareStatement(sqlAnak);

            // Set the id_barang in the PreparedStatement
            pstAnak.setInt(1, idBarang);

            // Set nilai untuk kolom stok
            pstAnak.setString(2, quantitas.getText());
            pstAnak.setString(3, (String)kodekeranjang.getSelectedItem());

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
            String sql = "UPDATE keranjang SET id_barang = ?, quantity = ?, kode_keranjang = ? WHERE id_keranjang = ?";
            Connection conn = koneksi.configDB();
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);

            pst.setInt(1, idBarang); // Use the retrieved id_barang
            pst.setString(2, quantitas.getText()); // Set the stok value
            pst.setString(3, (String)kodekeranjang.getSelectedItem()); // Set the stok value
            pst.setString(4, id_barang.getText()); // Use the existing id_bkeluar

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
            String sql = "delete from keranjang WHERE id_keranjang='"+id_barang.getText()+"'";
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

    private void data_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_barangMouseClicked
        int baris = data_barang.rowAtPoint(evt.getPoint());
       String idb =data_barang.getValueAt(baris, 3).toString();
        quantitas.setText(idb);
        String kodek =data_barang.getValueAt(baris, 2).toString();
        kodekeranjang.setSelectedItem(kodek);
       String namabarang =data_barang.getValueAt(baris, 1).toString();
        nbarang.setSelectedItem(namabarang);
       String iddbarang =data_barang.getValueAt(baris, 0).toString();
       id_barang.setText(iddbarang);
    }//GEN-LAST:event_data_barangMouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        this.dispose();
        new data().setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

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
            java.util.logging.Logger.getLogger(dkeranjang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dkeranjang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dkeranjang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dkeranjang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dkeranjang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable data_barang;
    private javax.swing.JTextField id_barang;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> kodekeranjang;
    private javax.swing.JComboBox<String> nbarang;
    private javax.swing.JTextField quantitas;
    // End of variables declaration//GEN-END:variables
}
