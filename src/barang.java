import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class barang extends JFrame {
    private Cart cart;
    private JPanel cartPanel;
    private JLabel cartLabel;
    private Connection conn;
    private int iduser;

    public barang() {
        // Set the modern FlatLaf look and feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        cart = new Cart(); // Initialize the cart
        try {
            // Initialize database connection
            conn = koneksi.configDB();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        iduser = usersession.get_id(); // Get user ID

        initUI();
        loadBarangData();
    }

    private void initUI() {
        setTitle("Barang Display");
        setSize(1000, 600); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panel with grid layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columns, dynamic rows
        panel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        // Cart panel at the bottom
        cartPanel = new JPanel();
        cartPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        cartLabel = new JLabel("Cart: 0 items, Total: Rp 0");
        JButton viewCartButton = new JButton("View Cart");
        JButton payButton = new JButton("Pay");

        viewCartButton.addActionListener(e -> showCartDetails());
        payButton.addActionListener(e -> {
            processPayment();
        });

        cartPanel.add(cartLabel);
        cartPanel.add(viewCartButton);
        cartPanel.add(payButton);

        add(cartPanel, BorderLayout.SOUTH);
    }

    private void loadBarangData() {
        try {
            JPanel panel = (JPanel) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM barang");
            while (rs.next()) {
                JPanel card = createBarangCard(
                        rs.getString("nama_barang"),
                        rs.getDouble("harga_jual"),
                        rs.getInt("stok"),
                        rs.getString("foto")
                );
                panel.add(card);
            }
            // Refresh panel to display changes
            panel.revalidate();
            panel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

private JPanel createBarangCard(String namaBarang, double harga, int stok, String fotoFileName) {
    int min = stok > 0 ? 1 : 0;
    int max = stok;
    int value = stok > 0 ? min : 0;

    SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, 1);

    JPanel card = new JPanel();
    card.setLayout(new BorderLayout()); // Change layout to BorderLayout
    card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
    card.setPreferredSize(new Dimension(200, 400));
    card.setMaximumSize(new Dimension(200, 400));

    // Image
    JLabel imageLabel = new JLabel("No Image", JLabel.CENTER);
    String imagePath = "/photobarang/" + fotoFileName;
    java.net.URL imageURL = getClass().getResource(imagePath);

    if (imageURL != null) {
        ImageIcon imageIcon = new ImageIcon(imageURL);
        Image img = imageIcon.getImage();
        
        // Set the width to match the card's width and calculate the height based on aspect ratio
        int cardWidth = 320; // Fixed width for the card
        double aspectRatio = (double) img.getWidth(null) / img.getHeight(null);
        int scaledWidth = cardWidth;
        int scaledHeight = (int) (scaledWidth / aspectRatio);

        Image scaledImg = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImg));
        imageLabel.setText(null);

        // Set preferred size of the image label to match the card's width and calculated height
        imageLabel.setPreferredSize(new Dimension(cardWidth, scaledHeight));
        imageLabel.setMaximumSize(new Dimension(cardWidth, scaledHeight));
    } else {
        // If the image couldn't be loaded, set a default size for the image label
        imageLabel.setPreferredSize(new Dimension(200, 150));
        imageLabel.setMaximumSize(new Dimension(200, 150));
    }

    // Add imageLabel to the card using BorderLayout.NORTH
    card.add(imageLabel, BorderLayout.NORTH);

    // Details
    JPanel detailsPanel = new JPanel();
    detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
    detailsPanel.add(new JLabel("Nama: " + namaBarang));
    detailsPanel.add(new JLabel("Harga: Rp " + harga));
    detailsPanel.add(new JLabel("Stok: " + stok));
    card.add(detailsPanel, BorderLayout.CENTER);

    // Quantity Spinner and Buy Button
    JSpinner quantitySpinner = new JSpinner(model);
    JButton buyButton = new JButton("Beli");

    if (stok <= 0) {
        quantitySpinner.setEnabled(false);
        buyButton.setEnabled(false);
        buyButton.setText("Stok Habis");
    } else {
        buyButton.addActionListener(e -> {
            int quantity = (Integer) quantitySpinner.getValue();
            cart.addItem(namaBarang, harga, quantity);
            updateCartLabel();
            JOptionPane.showMessageDialog(this, "Added " + quantity + " of " + namaBarang + " to cart.");
        });
    }

    JPanel controlPanel = new JPanel();
    controlPanel.setLayout(new FlowLayout());
    controlPanel.add(quantitySpinner);
    controlPanel.add(buyButton);
    card.add(controlPanel, BorderLayout.SOUTH);

    return card;
}



    private void updateCartLabel() {
        cartLabel.setText("Cart: " + cart.getTotalItems() + " items, Total: Rp " + cart.getTotalPrice());
    }

    private void showCartDetails() {
        StringBuilder cartDetails = new StringBuilder();
        double grandTotal = 0;
        for (Map.Entry<String, CartItem> entry : cart.getItems().entrySet()) {
            CartItem item = entry.getValue();
            double itemTotal = item.getTotalPrice();
            cartDetails.append(String.format("%s - %d x Total Rp %.2f\n", item.getName(), item.getQuantity(), itemTotal));
            grandTotal += itemTotal;
        }
        cartDetails.append(String.format("\nGrand Total: Rp %.2f", grandTotal));
        JOptionPane.showMessageDialog(this, cartDetails.toString(), "Cart Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void processPayment() {
    if (cart.getTotalItems() > 0) {
        double totalAmount = cart.getTotalPrice();

        // Create a panel for the payment dialog
        JPanel paymentPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        paymentPanel.add(new JLabel("Total Amount:"));
        JTextField totalAmountField = new JTextField(String.format("Rp %.2f", totalAmount));
        totalAmountField.setEditable(false);
        paymentPanel.add(totalAmountField);

        paymentPanel.add(new JLabel("Amount Paid:"));
        JTextField amountPaidField = new JTextField();
        paymentPanel.add(amountPaidField);

        paymentPanel.add(new JLabel("Change:"));
        JTextField changeField = new JTextField();
        changeField.setEditable(false);
        paymentPanel.add(changeField);

        // Add a document listener to update change dynamically
        amountPaidField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateChange();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateChange();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateChange();
            }

            private void updateChange() {
                try {
                    double amountPaid = Double.parseDouble(amountPaidField.getText().replace("Rp ", "").replace(",", ""));
                    double change = amountPaid - totalAmount;
                    changeField.setText(String.format("Rp %.2f", change));
                } catch (NumberFormatException e) {
                    changeField.setText("Rp 0.00");
                }
            }
        });

        // Create a dialog to display payment information
        int option = JOptionPane.showConfirmDialog(this, paymentPanel, "Payment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                double amountPaid = Double.parseDouble(amountPaidField.getText().replace("Rp ", "").replace(",", ""));
                double change = amountPaid - totalAmount;
                if (amountPaid < totalAmount) {
                    JOptionPane.showMessageDialog(this, "Amount paid is less than the total amount. Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Generate transaction number and cart code
                String noTransaksi = generateTransactionNumber();
                String kodeKeranjang = getNextCartCode();
                
                // Insert data into the database
                insertData(noTransaksi, kodeKeranjang, totalAmount);

                JOptionPane.showMessageDialog(this, String.format("Payment processed successfully.\nChange: Rp %.2f", change));
                cart = new Cart(); // Clear cart after payment
                updateCartLabel(); // Update cart label
                
                loadBarangData(); // Reload the data to reflect the updated stock
                this.dispose();
                new barang().setVisible(true);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number for the amount paid.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Your cart is empty.");
    }
}

private String generateTransactionNumber() throws SQLException {
    String sql = "SELECT no_transaksi FROM transaksi ORDER BY no_transaksi DESC LIMIT 1";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
            String lastTransactionNumber = rs.getString("no_transaksi");
            System.out.println("Last Transaction Number from DB: " + lastTransactionNumber); // Debugging output
            
            int lastNumber;
            try {
                lastNumber = Integer.parseInt(lastTransactionNumber.substring(4));
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                // Handle unexpected format
                System.err.println("Error parsing transaction number: " + e.getMessage());
                lastNumber = 0; // Fallback to default value
            }
            
            String newTransactionNumber = String.format("CTP-%d", lastNumber + 1);
            System.out.println("Generated Transaction Number: " + newTransactionNumber); // Debugging output
            return newTransactionNumber;
        } else {
            String newTransactionNumber = "CTP-1";
            System.out.println("Generated Transaction Number (no records): " + newTransactionNumber); // Debugging output
            return newTransactionNumber;
        }
    }
}

private String getNextCartCode() throws SQLException {
    String sql = "SELECT kode_keranjang FROM keranjang ORDER BY kode_keranjang DESC LIMIT 1";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        if (rs.next()) {
            String lastCartCode = rs.getString("kode_keranjang");
            int lastNumber = Integer.parseInt(lastCartCode.substring(4));
            String newCartCode = String.format("CPP-%04d", lastNumber + 1);
            System.out.println("Generated Cart Code: " + newCartCode); // Debugging output
            return newCartCode;
        } else {
            String newCartCode = "CPP-0001";
            System.out.println("Generated Cart Code: " + newCartCode); // Debugging output
            return newCartCode;
        }
    }
}



    private void insertData(String noTransaksi, String kodeKeranjang, double total) throws SQLException {
        // SQL queries
        String insertTransaksiSQL = "INSERT INTO transaksi (no_transaksi, kode_keranjang, tanggal, create_at, create_by, deletet) VALUES (?, ?, NOW(), NOW(), ?, '0')";
        String insertNotaSQL = "INSERT INTO nota (nomor_transaksi, tanggal_transaksi, jumlah_transaksi, create_at, create_by, `delete`) VALUES (?, NOW(), ?, NOW(), ?, '0')";
        String insertBarangKeluarSQL = "INSERT INTO barangkeluar (id_barang, jumlah, `delete`, tanggal, create_at, create_by, kode_keranjang) VALUES (?, ?, '0', NOW(), NOW(), ?, ?)";
        String insertKeranjangSQL = "INSERT INTO keranjang (id_user, id_barang, quantity, kode_keranjang, create_at, create_by, status, deletek) VALUES (?, ?, ?, ?, NOW(), ?, 'pending', '0')";

        try (PreparedStatement pstmtTransaksi = conn.prepareStatement(insertTransaksiSQL);
             PreparedStatement pstmtNota = conn.prepareStatement(insertNotaSQL);
             PreparedStatement pstmtBarangKeluar = conn.prepareStatement(insertBarangKeluarSQL);
             PreparedStatement pstmtKeranjang = conn.prepareStatement(insertKeranjangSQL)) {

            // Insert into transaksi
            pstmtTransaksi.setString(1, noTransaksi);
            pstmtTransaksi.setString(2, kodeKeranjang);
            pstmtTransaksi.setInt(3, iduser); // Use iduser
            pstmtTransaksi.executeUpdate();

            // Insert into nota
            pstmtNota.setString(1, noTransaksi);
            pstmtNota.setDouble(2, total);
            pstmtNota.setInt(3, iduser); // Use iduser
            pstmtNota.executeUpdate();

            // Insert into barangkeluar and keranjang
            for (Map.Entry<String, CartItem> entry : cart.getItems().entrySet()) {
                CartItem item = entry.getValue();
                int idBarang = getIdBarangFromDatabase(item.getName()); // Get id_barang

                if (idBarang != -1) {
                    // Insert into barangkeluar
                    pstmtBarangKeluar.setInt(1, idBarang);
                    pstmtBarangKeluar.setInt(2, item.getQuantity());
                    pstmtBarangKeluar.setInt(3, iduser); // Use iduser
                    pstmtBarangKeluar.setString(4, kodeKeranjang);
                    pstmtBarangKeluar.executeUpdate();

                    // Insert into keranjang
                    pstmtKeranjang.setInt(1, iduser);
                    pstmtKeranjang.setInt(2, idBarang);
                    pstmtKeranjang.setInt(3, item.getQuantity());
                    pstmtKeranjang.setString(4, kodeKeranjang);
                    pstmtKeranjang.setInt(5, iduser); // Use iduser
                    pstmtKeranjang.addBatch(); // Add to batch
                } else {
                    JOptionPane.showMessageDialog(null, "Barang dengan nama " + item.getName() + " tidak ditemukan di database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Execute batch insertion for keranjang
            pstmtKeranjang.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

private int getIdBarangFromDatabase(String namaBarang) throws SQLException {
    String getIdBarangSQL = "SELECT id_barang FROM barang WHERE nama_barang = ?";
    try (PreparedStatement pstmtGetIdBarang = conn.prepareStatement(getIdBarangSQL)) {
        pstmtGetIdBarang.setString(1, namaBarang);
        ResultSet rs = pstmtGetIdBarang.executeQuery();
        if (rs.next()) {
            return rs.getInt("id_barang");
        } else {
            return -1; // Item not found
        }
    }
}



    class Cart {
        private final Map<String, CartItem> items;
        private double totalPrice;

        public Cart() {
            items = new HashMap<>();
            totalPrice = 0;
        }

        public void addItem(String name, double price, int quantity) {
            if (items.containsKey(name)) {
                CartItem item = items.get(name);
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                items.put(name, new CartItem(name, price, quantity));
            }
            totalPrice += price * quantity;
        }

        public int getTotalItems() {
            return items.values().stream().mapToInt(CartItem::getQuantity).sum();
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public Map<String, CartItem> getItems() {
            return items;
        }
    }

    class CartItem {
        private final String name;
        private final double price;
        private int quantity;

        public CartItem(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return price * quantity;
        }
    }






    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 709, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new barang().setVisible(true));
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

