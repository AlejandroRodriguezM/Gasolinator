package Dbmanager;

public class DbManager {

	// Consultas SQL como constantes estáticas
	private static final String SQL_CREATE_TABLE_CONCIERTO = "CREATE TABLE viajes ("
			+ " idEvento INTEGER PRIMARY KEY AUTOINCREMENT," + " identificadorConcierto TEXT NOT NULL,"
			+ " codigoMusico TEXT NOT NULL," + " esConductor BOOLEAN NOT NULL," + " kmIda REAL NOT NULL,"
			+ " kmVuelta REAL NOT NULL," + " costoPagar REAL NOT NULL," + " totalAdelantado REAL NOT NULL,"
			+ " fechaConcierto DATE NOT NULL," + " resultadoPagar TEXT" + ");";

	private static final String SQL_CREATE_TABLE_USUARIOS = "CREATE TABLE IF NOT EXISTS musicos ( "
			+ "idMusico INTEGER PRIMARY KEY AUTOINCREMENT," + "identificadorMusico TEXT NOT NULL,"
			+ "nombreMusico TEXT UNIQUE NOT NULL," + "direccionFoto TEXT NOT NULL);";

	private static final String SQL_CREATE_TABLE_CONCIERTOS = "CREATE TABLE IF NOT EXISTS conciertos ( "
			+ "idConcierto INTEGER PRIMARY KEY AUTOINCREMENT," 
			+ "identificadorConcierto TEXT NOT NULL,"
			+ "nombreConcierto TEXT NOT NULL,"
			+ "fechaConcierto DATE NOT NULL," 
			+ "identificadorMusicos TEXT NOT NULL);";

	// Método para crear la tabla
	public static void createTable() {
		DbConnection.executeUpdate(SQL_CREATE_TABLE_USUARIOS);
		DbConnection.executeUpdate(SQL_CREATE_TABLE_CONCIERTO);
		DbConnection.executeUpdate(SQL_CREATE_TABLE_CONCIERTOS);
	}
}
