package dbmanager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import ficherosFunciones.FuncionesFicheros;
import funcionesAuxiliares.Utilidades;
import funcionesAuxiliares.Ventanas;

public class SQLiteManager {

	// Ruta del directorio del usuario actual en el sistema
	private static final String USER_HOME = System.getProperty("user.home");

	private static final String DATABASE_FOLDER = USER_HOME + File.separator + "AppData" + File.separator + "Roaming"
			+ File.separator + "EventoDB";

	// Consultas SQL como constantes estáticas
	private static final String SQL_CREATE_DATABASE= "CREATE TABLE viajes ("
			+ " idEvento INTEGER PRIMARY KEY AUTOINCREMENT," + " identificadorConcierto TEXT NOT NULL,"
			+ " codigoMusico TEXT NOT NULL," + " esConductor BOOLEAN NOT NULL," + " kmIda REAL NOT NULL,"
			+ " kmVuelta REAL NOT NULL," + " costoPagar REAL NOT NULL," + " totalAdelantado REAL NOT NULL,"
			+ " fechaConcierto DATE NOT NULL," + " resultadoPagar TEXT" + ");";
	
	// Consultas SQL como constantes estáticas
	private static final String SQL_CREATE_TABLE_VIAJES= "CREATE TABLE viajes ("
			+ " idEvento INTEGER PRIMARY KEY AUTOINCREMENT," + " identificadorConcierto TEXT NOT NULL,"
			+ " codigoMusico TEXT NOT NULL," + " esConductor BOOLEAN NOT NULL," + " kmIda REAL NOT NULL,"
			+ " kmVuelta REAL NOT NULL," + " costoPagar REAL NOT NULL," + " totalAdelantado REAL NOT NULL,"
			+ " fechaConcierto DATE NOT NULL," + " resultadoPagar TEXT" + ");";

	private static final String SQL_CREATE_TABLE_USUARIOS = "CREATE TABLE IF NOT EXISTS musicos ( "
			+ "idMusico INTEGER PRIMARY KEY AUTOINCREMENT," + "identificadorMusico TEXT NOT NULL,"
			+ "nombreMusico TEXT UNIQUE NOT NULL," + "direccionFoto TEXT NOT NULL);";

	private static final String SQL_CREATE_TABLE_CONCIERTOS = "CREATE TABLE IF NOT EXISTS conciertos ( "
			+ "idConcierto INTEGER PRIMARY KEY AUTOINCREMENT," + "identificadorConcierto TEXT NOT NULL,"
			+ "nombreConcierto TEXT NOT NULL," + "fechaConcierto DATE NOT NULL,"
			+ "identificadorMusicos TEXT NOT NULL);";

	// Método para crear la tabla
	public static void createTable() {
		
        File dbFolder = new File(DATABASE_FOLDER);
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
            System.out.println("Carpeta creada en: " + DATABASE_FOLDER);
        }
		
		createTable(SQL_CREATE_TABLE_USUARIOS);
		createTable(SQL_CREATE_TABLE_VIAJES);
		createTable(SQL_CREATE_TABLE_CONCIERTOS);
	}
	
	public static void createTable(String sql) {

		String url = ConectManager.DATABASE_URL;

		System.out.println(url);
		
		try (Connection connection = DriverManager.getConnection(url);
				Statement statement = connection.createStatement()) {

			statement.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Comprueba si existe una base de datos con el nombre especificado para la
	 * creación.
	 *
	 * @return true si la base de datos no existe, false si ya existe o si hay un
	 *         error en la conexión
	 */
	public static boolean checkDatabaseExists(String nombreDataBase) {
		File file = new File(DATABASE_FOLDER + nombreDataBase + ".db");
		return !file.exists();
	}

}
