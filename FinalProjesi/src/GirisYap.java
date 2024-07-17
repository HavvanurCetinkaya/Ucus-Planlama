import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GirisYap extends JPanel {
    public GirisYap(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Başlık
        JLabel titleLabel = new JLabel("Giriş Yap");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);

        // E-posta alanı
        formPanel.add(new JLabel("E-posta:"));
        JTextField emailField = new JTextField(20);
        formPanel.add(emailField);

        // Şifre alanı
        formPanel.add(Box.createVerticalStrut(10)); // Boşluk ekle
        formPanel.add(new JLabel("Şifre:"));
        JPasswordField passwordField = new JPasswordField(20);
        formPanel.add(passwordField);

        // Giriş Yap butonu
        formPanel.add(Box.createVerticalStrut(20)); // Boşluk ekle
        JButton girisYapButton = new JButton("Giriş Yap");
        girisYapButton.setBackground(new Color(25, 25, 112));
        girisYapButton.setForeground(Color.WHITE);
        girisYapButton.setFont(new Font("Arial", Font.BOLD, 16));
        girisYapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        girisYapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (checkLogin(email, password)) {
                    JOptionPane.showMessageDialog(null, "Giriş yapıldı!");
                    // Kullanıcı hesabına yönlendirme yapılabilir.
                    // cardLayout.show(cardPanel, "KullanıcıPaneli"); gibi bir şey yapılabilir.
                } else {
                    JOptionPane.showMessageDialog(null, "E-posta veya şifre hatalı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        formPanel.add(girisYapButton);

        formContainer.add(formPanel);
        add(formContainer, BorderLayout.CENTER);
    }

    private boolean checkLogin(String email, String password) {
        boolean isValidUser = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Veritabanı bağlantısını al
            conn = DatabaseConnector.getConnection();

            // SQL sorgusunu hazırlayın
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            // Sorguyu çalıştırın
            rs = pstmt.executeQuery();

            // Eğer sonuç dönerse, kullanıcı bilgileri doğru demektir
            if (rs.next()) {
                isValidUser = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Kaynakları kapatın
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return isValidUser;
    }
}
