import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Turkish Airlines");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel homePanel = new BackgroundPanel("arkaplan2.jpg");
        homePanel.setLayout(new BorderLayout());
        JLabel homeLabel = new JLabel("Merhaba, nereyi keşfetmek istersiniz?");
        homeLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        homeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        homeLabel.setForeground(Color.WHITE);
        homePanel.add(homeLabel, BorderLayout.CENTER);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.add(homePanel, "home");

        PlanlaVeUc planlaVeUcPanel = new PlanlaVeUc(cardLayout, cardPanel);
        cardPanel.add(planlaVeUcPanel, "ticket");

        yardim yardimPanel = new yardim(cardLayout, cardPanel);
        cardPanel.add(yardimPanel, "help");

        UyeOl uyeOlPanel = new UyeOl(cardLayout, cardPanel);
        cardPanel.add(uyeOlPanel, "register");

        GirisYap girisYapPanel = new GirisYap(cardLayout, cardPanel);
        cardPanel.add(girisYapPanel, "login");

        JPanel navbarPanel = new JPanel();
        navbarPanel.setBackground(new Color(25, 25, 112));
        navbarPanel.setLayout(new BorderLayout());

        ImageIcon logoIcon = new ImageIcon("logo2.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogo);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, "home");
            }
        });

        // Create a panel to hold the logo and the label
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(25, 25, 112));
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        logoPanel.add(logoLabel);

        // Add "Turkish Airlines" label
        JLabel airlineLabel = new JLabel("Turkish Airlines");
        airlineLabel.setFont(new Font("Arial", Font.BOLD, 24));
        airlineLabel.setForeground(Color.WHITE);
        logoPanel.add(airlineLabel);

        navbarPanel.add(logoPanel, BorderLayout.WEST);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton planlaVeUcButton = new JButton("PLANLA&UÇ");
        planlaVeUcButton.setBackground(Color.WHITE);
        planlaVeUcButton.setForeground(Color.BLACK);
        planlaVeUcButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ticket");
            }
        });
        JButton yardimButton = new JButton("Yardım");
        yardimButton.setBackground(Color.WHITE);
        yardimButton.setForeground(Color.BLACK);
        yardimButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "help");
            }
        });
        JButton uyeOlButton = new JButton("Üye Ol");
        uyeOlButton.setBackground(Color.WHITE);
        uyeOlButton.setForeground(Color.BLACK);
        uyeOlButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "register");
            }
        });
        JButton girisYapButton = new JButton("Giriş Yap");
        girisYapButton.setBackground(Color.WHITE);
        girisYapButton.setForeground(Color.BLACK);
        girisYapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "login");
            }
        });
        menuPanel.add(planlaVeUcButton);
        menuPanel.add(yardimButton);
        menuPanel.add(uyeOlButton);
        menuPanel.add(girisYapButton);
        menuPanel.setBackground(new Color(25, 25, 112));
        navbarPanel.add(menuPanel, BorderLayout.EAST);

        frame.add(navbarPanel, BorderLayout.NORTH);
        frame.add(cardPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        try {
            backgroundImage = new ImageIcon(fileName).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
