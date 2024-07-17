import javax.swing.*;
import java.awt.*;

public class yardim extends JPanel {
    public yardim(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sıkça Sorulan Sorular");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JTextArea faqTextArea = new JTextArea();
        faqTextArea.setText(
            "*Biletimi satın aldıktan sonra iptal etmem mümkün mü?\n" +
            "Biletinizi iptal edebilirsiniz ancak her biletin iptal/iade durumu ücret koşullarına göre değişiklik gösterir. " +
            "Biletinizin ücret koşullarını \"uçuşlarım\" sayfasından görebilir, detaylı bilgi için ücret koşulları sayfamızı ziyaret edebilirsiniz.\n\n" +
            "*Asker aileleri için indiriminiz var mı?\n" +
            "Sadece KKTC'ndeki Türk Barış Kuvvetleri'nde görev yapmakta olan askerlerin eşleri ve 2-12 yaş arasındaki çocukları biletlerinin " +
            "ücret sınıfına bağlı olarak %15 oranında indirimli bilet alabilir. Ancak bu indirimi online kanallarımız (internet sitesi/mobil uygulama) " +
            "aracılığıyla satın alınan biletlerde sunamıyoruz. Detaylı bilgi için ücret koşulları sayfamızı ziyaret edebilir ya da bize ulaşın " +
            "sayfamızdaki çağrı merkezimizden veya satış ofislerimizden bilgi alabilirsiniz.\n\n" +
            "*Çocuğum için bilet alırken indirimli bir tarifeden yararlanabilir miyim?\n" +
            "Kendi koltuğunda yolculuk yapabilecek olan 2-12 yaş arası çocuklarınız için uygulanan indirim oranı güzergâha ve ücret sınıfına göre " +
            "değişiklik gösterebilir. İndirimli ücretlerden faydalanmak için seyahat öncesinde çocuğunuzun doğum tarihini gösteren bir belge sunmanız " +
            "gerektiğini hatırlatalım. Detaylı bilgi için ücret koşulları sayfamızı ziyaret edebilir ya da bize ulaşın sayfamızdaki çağrı merkezimizden " +
            "veya satış ofislerimizden bilgi alabilirsiniz.\n\n" +
            "*Bilet alırken bebeğim için yararlanabileceğim indirimli bir tarife var mı?\n" +
            "Bebek yolcularımız için uyguladığımız indirimli tarifeler hakkında bebek ve çocuk yolcular veya ücret koşulları sayfalarımızdan bilgi " +
            "alabilirsiniz.\n\n" +
            "*Hangi havalimanlarında Turkish Airlines Lounge'larından yararlanabilirim?\n" +
            "Yurt içi ve yurt dışında bulunan özel yolcu salonlarımızın listesi için Turkish Airlines Lounge sayfamızı ziyaret edebilirsiniz.\n\n" +
            "Çağrı Merkezimiz:\n0850 333 0 849"
        );
        faqTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        faqTextArea.setEditable(false);
        faqTextArea.setLineWrap(true);
        faqTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(faqTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
