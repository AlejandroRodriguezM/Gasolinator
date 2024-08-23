package Dbmanager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConectManager {

	 // Ruta del directorio del usuario actual en el sistema
    private static final String USER_HOME = System.getProperty("user.home");
    // Ruta de la carpeta y del archivo de la base de datos SQLite
    private static final String DATABASE_FOLDER = USER_HOME + File.separator + "AppData" + File.separator + "Roaming" 
                                                + File.separator + "EventoDB";
    private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_FOLDER + File.separator + "eventoDB.db";

    // Objeto de conexión
    private static Connection connection;

    // Constructor
    public ConectManager() {
        try {
            checkAndCreateDatabase();
            // Establece la conexión a la base de datos
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Conexión a la base de datos SQLite establecida.");
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
                System.out.println("Conexión a la base de datos SQLite cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static void executeUpdate(String sql) {
    	
    	try {
			connection = DriverManager.getConnection(DATABASE_URL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
            System.out.println("Consulta ejecutada: " + sql);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    // Método para comprobar si la carpeta y la base de datos existen, y si no, crearlas
    private void checkAndCreateDatabase() {
        // Comprobar si la carpeta existe
        File dbFolder = new File(DATABASE_FOLDER);
        if (!dbFolder.exists()) {
            if (dbFolder.mkdirs()) {
                System.out.println("La carpeta EventoDB no existía, se ha creado.");
            } else {
                throw new RuntimeException("No se pudo crear la carpeta EventoDB.");
            }
        }

        // Comprobar si el archivo de la base de datos existe
        File dbFile = new File(DATABASE_FOLDER + File.separator + "eventoDB.db");
        if (!dbFile.exists()) {
            try {
                if (dbFile.createNewFile()) {
                    System.out.println("El archivo de base de datos eventoDB.db no existía, se ha creado.");
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al crear la base de datos: " + e.getMessage(), e);
            }
        }
    }
}
