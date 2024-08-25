package dbmanager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import funcionesAuxiliares.Ventanas;
import javafx.scene.Scene;

/**
 * Clase que gestiona la conexión a la base de datos y proporciona métodos para
 * cargar el controlador JDBC, verificar la conexión y obtener una conexión
 * activa.
 */
public class ConectManager {

	/**
	 * Ventanas de la aplicación.
	 */
	private static Ventanas nav = new Ventanas();

	// Ruta del directorio del usuario actual en el sistema
	private static final String USER_HOME = System.getProperty("user.home");
	// Ruta de la carpeta y del archivo de la base de datos SQLite
	private static final String DATABASE_FOLDER = USER_HOME + File.separator + "AppData" + File.separator + "Roaming"
			+ File.separator + "EventoDB";
	
	public static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_FOLDER + File.separator + "eventoDB.db";

	// Objeto de conexión
	private static Connection connection;

	public static List<Scene> activeScenes = new ArrayList<>();
	
	public static boolean estadoConexion = false;

	/**
	 * Nombre de la base de datos.
	 */
	public static String DB_NAME;

	/**
	 * URL de la base de datos.
	 */
	public static String DB_URL;

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
		try {
			return DriverManager.getConnection(DATABASE_URL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	// Método para cerrar la conexión
	public static void close() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Conexión a la base de datos SQLite cerrada.");
			} catch (SQLException e) {
				System.err.println("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}

	/**
	 * Carga el controlador JDBC para el proyecto.
	 * 
	 * @return true si se carga el controlador correctamente, false en caso
	 *         contrario.
	 */
	public static boolean loadDriver() {
		try {
			Class.forName("org.sqlite.JDBC");
			return true;
		} catch (ClassNotFoundException ex) {
			nav.alertaException(ex.toString());
		}
		return false;
	}

	/**
	 * Comprueba la conexion y muestra su estado por pantalla
	 *
	 * @return true si la conexión existe y es válida, false en caso contrario
	 */
	public static boolean isConnected() {
		try {
			if (connection != null && !connection.isClosed()) {
				return true;
			}
		} catch (SQLException ex) {
			nav.alertaException(ex.toString());
		}
		return false;
	}

	/**
	 * Asigna valores predeterminados si las variables están vacías.
	 */
	public static void asignarValoresPorDefecto() {
		DB_NAME = "";
	}

	/**
	 * Método estático que restablece la conexión a la base de datos.
	 */
	public static void resetConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
			connection = getConnection();
		} catch (SQLException ex) {
			nav.alertaException(
					"No ha sido posible restablecer la conexion a la base de datos. Parece que esta desconectada");
		}
	}

	/**
	 * Método estático que restablece la conexión a la base de datos.
	 */
	public static void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException ex) {
			nav.alertaException(
					"No ha sido posible restablecer la conexion a la base de datos. Parece que esta desconectada");
		}
	}

	public static void executeUpdate(String sql) {

		try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
			pstmt.executeUpdate();
			System.out.println("Consulta ejecutada: " + sql);
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		}
	}

	// Método para comprobar si la carpeta y la base de datos existen, y si no,
	// crearlas
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
