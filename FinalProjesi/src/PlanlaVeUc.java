import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class PlanlaVeUc extends JPanel {
	public static void main(String[] args) {
		
	}
    private CardLayout cardLayout;
    private JPanel cardPanel;
	

    public PlanlaVeUc(CardLayout cardLayout, JPanel cardPanel) {
        super();
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Uçak Bileti Alma");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        
    
    

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Nerden
        JLabel fromLabel = new JLabel("Nerden:");
        formPanel.add(fromLabel, gbc);

        gbc.gridx = 1;
        JTextField fromTextField = new JTextField(15);
        formPanel.add(fromTextField, gbc);

        // Nereye
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel toLabel = new JLabel("Nereye:");
        formPanel.add(toLabel, gbc);

        gbc.gridx = 1;
        JTextField toTextField = new JTextField(15);
        formPanel.add(toTextField, gbc);

        // Tarih
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel dateLabel = new JLabel("Tarih:");
        formPanel.add(dateLabel, gbc);

        gbc.gridx = 1;
        JTextField dateTextField = new JTextField(15);
        formPanel.add(dateTextField, gbc);

        // Yolcu Sayısı
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passengerLabel = new JLabel("Yolcu Sayısı:");
        formPanel.add(passengerLabel, gbc);

        gbc.gridx = 1;
        JTextField passengerTextField = new JTextField(15);
        formPanel.add(passengerTextField, gbc);

        // Uçuş Tipi
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel tripTypeLabel = new JLabel("Uçuş Tipi:");
        formPanel.add(tripTypeLabel, gbc);

        gbc.gridx = 1;
        JPanel tripTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton oneWayRadioButton = new JRadioButton("Tek Yön");
        JRadioButton roundTripRadioButton = new JRadioButton("Gidiş-Dönüş");
        ButtonGroup tripTypeButtonGroup = new ButtonGroup();
        tripTypeButtonGroup.add(oneWayRadioButton);
        tripTypeButtonGroup.add(roundTripRadioButton);
        tripTypePanel.add(oneWayRadioButton);
        tripTypePanel.add(roundTripRadioButton);
        formPanel.add(tripTypePanel, gbc);
    

        // Search button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton searchButton = new JButton("Uçuş Ara");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = fromTextField.getText();
                String to = toTextField.getText();
                String date = dateTextField.getText();
                String passengerCount = passengerTextField.getText();
                String tripType = oneWayRadioButton.isSelected() ? "Tek Yön" : "Gidiş-Dönüş";
                ArrayList<String> flights = searchFlights(from, to, date);
                // Uçuşları işle
                if (flights != null && !flights.isEmpty()) {
                    // Uçuşları listelemek için bir metin alanı oluştur
                    JTextArea flightTextArea = new JTextArea(10, 30);
                    flightTextArea.setEditable(false); // Kullanıcı tarafından düzenlenemez
                    // Uçuşları metin alanına ekle
                    flightTextArea.append("Uygun uçuşlar:\n");
                    for (String flight : flights) {
                        flightTextArea.append(flight + "\n");
                    }

                    // Uçuşları listelemek için bir panel oluştur
                    JPanel flightsPanel = new JPanel(new GridLayout(0, 1));
                    flightsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                    for (String flight : flights) {
                        JCheckBox flightCheckBox = new JCheckBox(flight);
                        flightsPanel.add(flightCheckBox);
                    }

                    JPanel dialogPanel = new JPanel();
                    dialogPanel.setLayout(new BorderLayout());
                    dialogPanel.add(new JScrollPane(flightTextArea), BorderLayout.NORTH);
                    dialogPanel.add(flightsPanel, BorderLayout.CENTER);
                

                    JPanel buyTicketPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JButton buyTicketButton = new JButton("Bilet Satın Al");
                    buyTicketButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int totalPrice = 0;
                            ArrayList<String> selectedFlights = new ArrayList<>();
                            for (Component component : flightsPanel.getComponents()) {
                                if (component instanceof JCheckBox) {
                                    JCheckBox checkBox = (JCheckBox) component;
                                    if (checkBox.isSelected()) {
                                        selectedFlights.add(checkBox.getText());
                                    }
                                }
                            }
                            for (String flight : selectedFlights) {
                                String[] parts = flight.split(" - ");
                                String flightNumber = parts[0];
                                // Veritabanından fiyatı al
                                int price = getPrice(flightNumber);
                                // Gidiş-dönüş ise fiyatı ikiyle çarp
                                if (roundTripRadioButton.isSelected()) {
                                    price *= 2;
                                }
                                // Yolcu sayısını fiyatla çarp ve toplam fiyata ekle
                                totalPrice += price * Integer.parseInt(passengerCount);
                            }
                            // Toplam fiyatı göster
                            JOptionPane.showMessageDialog(null, "Toplam Fiyat: " + totalPrice + " TL", "Toplam Fiyat", JOptionPane.INFORMATION_MESSAGE);

                            // Ödeme bilgileri kısmını göster
                            JPanel paymentPanel = new JPanel(new GridLayout(0, 2));
                            paymentPanel.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));
                            JLabel nameLabel = new JLabel("İsim Soyisim:");
                            JTextField nameTextField = new JTextField(20);
                            JLabel cardNumberLabel = new JLabel("Kart Numarası:");
                            JTextField cardNumberTextField = new JTextField(20);
                            JLabel expiryDateLabel = new JLabel("Son Kullanma Tarihi:");
                            JTextField expiryDateTextField = new JTextField(20);
                            JLabel cvvLabel = new JLabel("CVV:");
                            JTextField cvvTextField = new JTextField(5);
                            paymentPanel.add(nameLabel);
                            paymentPanel.add(nameTextField);
                            paymentPanel.add(cardNumberLabel);
                            paymentPanel.add(cardNumberTextField);
                            paymentPanel.add(expiryDateLabel);
                            paymentPanel.add(expiryDateTextField);
                            paymentPanel.add(cvvLabel);
                            paymentPanel.add(cvvTextField);
                            
                            int result = JOptionPane.showConfirmDialog(null, paymentPanel, "Ödeme Bilgileri", JOptionPane.OK_CANCEL_OPTION);
                            if (result == JOptionPane.OK_OPTION) {
                                // Ödeme işlemini gerçekleştir
                                // Onayla düğmesi ile satın alma işlemini tamamla
                                JOptionPane.showMessageDialog(null, "Ödeme Tamamlandı! Satın Alma İşlemi Tamamlandı.", "Onay", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    });
                    buyTicketPanel.add(buyTicketButton);
                    dialogPanel.add(buyTicketPanel, BorderLayout.SOUTH);

                    JOptionPane.showMessageDialog(null, dialogPanel, "Uygun Uçuşlar", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Hiç uçuş bulunamadı, kullanıcıya uygun bir mesaj göster
                    JOptionPane.showMessageDialog(null, "Uygun uçuş bulunamadı.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        formPanel.add(searchButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }
    
    private ArrayList<String> searchFlights(String from, String to, String date) {
        ArrayList<String> flights = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // Veritabanı bağlantısını al
            conn = DatabaseConnector.getConnection();

            // SQL sorgusu
            String sql = "SELECT * FROM flights WHERE departure_city = ? AND arrival_city = ? AND departure_time LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, from);
            stmt.setString(2, to);
            stmt.setString(3, date + "%"); // Tarih kısmı için LIKE kullanarak başlangıç saatini eşleştir

            // Sorguyu çalıştır ve sonuçları al
            rs = stmt.executeQuery();
            
            // Uçuşları listele
            while (rs.next()) {
                // Uçuş bilgilerini al ve listeye ekle
                String flightInfo = rs.getString("flight_number") + " - " + rs.getString("departure_time") + " - " + rs.getString("arrival_time");
                flights.add(flightInfo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Kaynakları kapat
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return flights;
    }
    
    private int getPrice(String flightNumber) {
        int price = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // Veritabanı bağlantısını al
            conn = DatabaseConnector.getConnection();

            // SQL sorgusu
            String sql = "SELECT price FROM flights WHERE flight_number = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, flightNumber);

            // Sorguyu çalıştır ve sonuçları al
            rs = stmt.executeQuery();

            // Fiyatı al
            if (rs.next()) {
                price = rs.getInt("price");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Kaynakları kapat
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return price;
    }
}