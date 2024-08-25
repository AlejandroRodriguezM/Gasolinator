package dbmanager;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ConciertoManagement.Concierto;
import EventoManagement.Evento;
import MusicoManagement.Musico;

public class InsertManager {

	private static final String SQL_INSERT_VIAJE = "INSERT INTO viajes (identificadorConcierto, codigoMusico, esConductor, kmIda, kmVuelta, "
			+ "costoPagar, totalAdelantado, fechaConcierto, resultadoPagar) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_INSERTAR_MUSICOS = "INSERT INTO musicos (identificadorMusico, nombreMusico, direccionFoto) VALUES (?, ?, ?)";

	private static final String SQL_INSERTAR_CONCIERTO = "INSERT INTO conciertos (identificadorConcierto,nombreConcierto, fechaConcierto, identificadorMusicos) VALUES (?,?, ?, ?)";

	private static final String[] IDENTIFICADORES_MUSICOS = { "0001", "0002", "0003", "0004", "0005", "0006", "0007",
			"0008", "0009", "0010", "0011" };
	private static final String[] NOMBRES_MUSICOS = { "Denice", "JaviMalagamba", "PacoElChino", "Paco", "Rafa", "Jesus",
			"Noelia", "Nacho", "FranSosa", "FranLuna", "Alejandro" };
	private static final String FOTO_PATH_BASE = "path/to/photo"; // Cambia esto a la base de la ruta de las fotos

	// Método para insertar un registro en la tabla viajes
	public static void insertViaje(Evento evento) {

		try (PreparedStatement pstmt = ConectManager.getConnection().prepareStatement(SQL_INSERT_VIAJE)) {
			// Asigna los parámetros del PreparedStatement basados en el objeto Evento.
			pstmt.setString(1, evento.getIdentificadorConcierto());
			pstmt.setString(2, evento.getCodigoMusico());
			pstmt.setBoolean(3, evento.isEsConductor());
			pstmt.setDouble(4, evento.getKmIda());
			pstmt.setDouble(5, evento.getKmVuelta());
			pstmt.setDouble(6, evento.getCostoPagar());
			pstmt.setDouble(7, evento.getTotalAdelantado());
			pstmt.setString(8, evento.getFechaConcierto()); // Asegúrate de que esté en formato 'YYYY-MM-DD'
			pstmt.setString(9, evento.getResultadoPagar());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		}
	}

	private static String devolverCodigoMusicos(Concierto concierto) {
		StringBuilder codArtista = new StringBuilder();
		List<Musico> musicos = concierto.getMusicos();
		for (int i = 0; i < musicos.size(); i++) {
			codArtista.append(musicos.get(i).getCodigoMusico());
			if (i < musicos.size() - 1) {
				codArtista.append(",");
			}
		}
		return codArtista.toString();
	}

	public static void insertViaje(Concierto concierto) {

		try (PreparedStatement pstmt = ConectManager.getConnection().prepareStatement(SQL_INSERTAR_CONCIERTO)) {
			// Asigna los parámetros del PreparedStatement basados en el objeto Evento.

			String codMusicos = devolverCodigoMusicos(concierto);

			pstmt.setString(1, concierto.getIdConcierto());
			pstmt.setString(2, concierto.getNombreConcierto());
			pstmt.setString(3, concierto.getFechaConcierto());
			pstmt.setString(4, codMusicos);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta: " + e.getMessage());
		}
	}

	public static void insertarCantantes() {

		try (PreparedStatement pstmt = ConectManager.getConnection().prepareStatement(SQL_INSERTAR_MUSICOS)) {
			for (int i = 0; i < IDENTIFICADORES_MUSICOS.length; i++) {
				pstmt.setString(1, IDENTIFICADORES_MUSICOS[i]);
				pstmt.setString(2, NOMBRES_MUSICOS[i]);
				pstmt.setString(3, FOTO_PATH_BASE + (i + 1) + ".jpg"); // Asume que las fotos están nombradas como
																		// photo1.jpg, photo2.jpg, etc.
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			System.out.println("Insertados los registros correctamente.");
		} catch (SQLException e) {
			System.out.println("Error al preparar o ejecutar el batch de inserciones: " + e.getMessage());
		}
	}

}
