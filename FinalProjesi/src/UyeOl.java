import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UyeOl extends JPanel {
    private Connection connection;
    private JTextField adField, soyadField, emailField, cepTelefonuField;
    private JPasswordField sifreField, sifreTekrarField;

    public UyeOl(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Veritabanı bağlantısını al
        try {
            connection = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanına bağlanırken bir hata oluştu!");
        }

        // Başlık
        JLabel titleLabel = new JLabel("Üye Ol");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);

        // Üyelik Giriş Bilgileri
        formPanel.add(new JLabel("Üyelik Giriş Bilgileri"));
        formPanel.add(Box.createVerticalStrut(10)); // Boşluk ekle
        formPanel.add(new JLabel("Ad:"));
        adField = new JTextField(20);
        formPanel.add(adField);

        formPanel.add(new JLabel("Soyad:"));
        soyadField = new JTextField(20);
        formPanel.add(soyadField);

        formPanel.add(new JLabel("E-Posta Adresi:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);

        formPanel.add(new JLabel("Cep Telefonu:"));
        cepTelefonuField = new JTextField(20);
        formPanel.add(cepTelefonuField);

        // Güvenlik Bilgilerim
        formPanel.add(Box.createVerticalStrut(20)); // Boşluk ekle
        formPanel.add(new JLabel("Güvenlik Bilgilerim"));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(new JLabel("Şifrenizi Oluşturun:"));
        sifreField = new JPasswordField(20);
        formPanel.add(sifreField);

        formPanel.add(new JLabel("Şifrenizi Tekrar Girin:"));
        sifreTekrarField = new JPasswordField(20);
        formPanel.add(sifreTekrarField);

        // Üye Ol butonu
        JButton uyeOlButton = new JButton("Üye Ol");
        uyeOlButton.setBackground(new Color(25, 25, 112));
        uyeOlButton.setForeground(Color.WHITE);
        uyeOlButton.setFont(new Font("Arial", Font.BOLD, 16));
        uyeOlButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uyeOlButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Şifre uyumluluğunu kontrol et
                char[] password = sifreField.getPassword();
                char[] confirmPassword = sifreTekrarField.getPassword();
                if (!new String(password).equals(new String(confirmPassword))) {
                    JOptionPane.showMessageDialog(UyeOl.this, "Şifreler eşleşmiyor. Lütfen tekrar deneyin.");
                    return;
                }

                // Kullanıcı bilgilerini veritabanına ekle
                String ad = adField.getText();
                String soyad = soyadField.getText();
                String email = emailField.getText();
                String telefon = cepTelefonuField.getText();
                String sifre = new String(password);
                addUserToDatabase(ad, soyad, email, telefon, sifre);
                JOptionPane.showMessageDialog(UyeOl.this, "Hesabınız oluşturuldu!");
            }
        });
        formPanel.add(Box.createVerticalStrut(20)); // Boşluk ekle
        formPanel.add(uyeOlButton);

        formContainer.add(formPanel);
        add(formContainer, BorderLayout.CENTER);
    }

    private void addUserToDatabase(String ad, String soyad, String email, String telefon, String sifre) {
        try {
            // SQL sorgusu oluştur
            String query = "INSERT INTO users (username, email, phone_number, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ad + " " + soyad); // Kullanıcı adı soyad şeklin
            statement.setString(2, email);
            statement.setString(3, telefon);
            statement.setString(4, sifre);

            // Sorguyu çalıştır
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kullanıcı eklenirken bir hata oluştu!");
        }
    }
}