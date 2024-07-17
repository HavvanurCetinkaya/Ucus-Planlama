import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    // Veritabanı bağlantısını oluşturmak için gerekli bilgiler
    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        // Veritabanı bağlantısını oluştur
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
