package Dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DbConnection {

    // Objeto de conexión
    private static Connection connection;

    // Cargar variables de entorno desde el archivo .env
    private static final Dotenv dotenv = Dotenv.load();
    private static final String DATABASE_URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    // Constructor
    public DbConnection() {
        try {
            // Establece la conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            System.out.println("Conexión a la base de datos MySQL establecida.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC no encontrado: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }

    // Método para obtener la conexión
    public static Connection getConnection() {
        if (connection == null) {
            throw new RuntimeException("La conexión a la base de datos no ha sido inicializada.");
        }
        return connection;
    }

    // Método para cerrar la conexión
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión a la base de datos MySQL cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static void executeUpdate(String sql) {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.executeUpdate();
                System.out.println("Consulta ejecutada: " + sql);
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }
}
