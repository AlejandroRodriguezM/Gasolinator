package dbmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import EventoManagement.Evento;
import funcionesAuxiliares.Utilidades;

public class SelectManager {

	private static final String SQL_SELECT_VIAJE = "SELECT * FROM viajes WHERE identificadorConcierto = ? OR nomMusico = ? OR fechaConcierto = ?;";
	public static final String TAMANIO_DATABASE = "SELECT COUNT(*) FROM viajes;";
	// Consultas SQL para seleccionar campos específicos
	private static final String SQL_SELECT_ALL_MUSICOS = "SELECT * FROM musicos;";

	public static final String SQL_SELECT_ALL_EVENTOS = "SELECT * FROM viajes;";
	public static final String SQL_SELECT_ALL_EVENTO_INDIVIDUAL = "SELECT * FROM viajes WHERE idEvento = ?;";

	private static final String SQL_SELECT_NOMBRE_MUSICOS = "SELECT nombreMusico FROM musicos;";

	private static final String SQL_SELECT_DIRECCION_FOTO_MUSICOS = "SELECT direccionFoto FROM musicos;";

	/**
	 * Funcion que permite contar cuantas filas hay en la base de datos.
	 *
	 * @return
	 */
	public static int countRows() {

		try (Connection conn = ConectManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(TAMANIO_DATABASE)) {

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			Utilidades.manejarExcepcion(e);
		}
		return -1;
	}

	public static List<Evento> libreriaSeleccionado(String datoSeleccionado) {
		String sql = "SELECT * FROM viajes WHERE " + "identificadorConcierto LIKE '%" + datoSeleccionado + "%' OR "
				+ "codigoMusico LIKE '%" + datoSeleccionado + "%' OR " + "fechaConcierto LIKE '%" + datoSeleccionado
				+ "%' OR " + "ORDER BY fechaConcierto ASC, idEvento ASC";

		return verEventos(sql, false);
	}

	// Método para seleccionar todos los campos de la tabla 'musicos'
	public static void selectAllMusicos() {
		try (PreparedStatement pstmt = ConectManager.getConnection().prepareStatement(SQL_SELECT_ALL_MUSICOS);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				System.out.println("idMusico: " + rs.getInt("idMusico"));
				System.out.println("nombreMusico: " + rs.getString("nombreMusico"));
				System.out.println("direccionFoto: " + rs.getString("direccionFoto"));
				System.out.println("----------------------------");
			}
		} catch (SQLException e) {
			System.err.println("Error al realizar la consulta (SELECT *): " + e.getMessage());
		}
	}

	// Método para seleccionar todos los campos de la tabla 'musicos'
	public static List<Evento> selectAllEventos() {
		try (PreparedStatement pstmt = ConectManager.getConnection().prepareStatement(SQL_SELECT_ALL_EVENTOS);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				// RELLENAR COSAS AQUI
			}

			return new ArrayList<>();
		} catch (SQLException e) {
			System.err.println("Error al realizar la consulta (SELECT *): " + e.getMessage());
		}
		return new ArrayList<>();
	}

	// Método para seleccionar solo los nombres de la tabla 'musicos'
	public static void selectNombreMusicos() {
		try (PreparedStatement pstmt = ConectManager.getConnection().prepareStatement(SQL_SELECT_NOMBRE_MUSICOS);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				System.out.println("nombreMusico: " + rs.getString("nombreMusico"));
			}
		} catch (SQLException e) {
			System.err.println("Error al realizar la consulta (SELECT nombreMusico): " + e.getMessage());
		}
	}

	// Método para seleccionar solo las direcciones de foto de la tabla 'musicos'
	public static void selectDireccionFotoMusicos() {
		try (PreparedStatement pstmt = ConectManager.getConnection()
				.prepareStatement(SQL_SELECT_DIRECCION_FOTO_MUSICOS); ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				System.out.println("direccionFoto: " + rs.getString("direccionFoto"));
			}
		} catch (SQLException e) {
			System.err.println("Error al realizar la consulta (SELECT direccionFoto): " + e.getMessage());
		}
	}

	// Método para seleccionar registros de la tabla viajes
	public static void selectViaje(String identificadorConcierto, String nomMusico, String fechaConcierto) {
		try (PreparedStatement pstmt = ConectManager.getConnection().prepareStatement(SQL_SELECT_VIAJE)) {
			pstmt.setString(1, identificadorConcierto);
			pstmt.setString(2, nomMusico);
			pstmt.setString(3, fechaConcierto);

			var rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("idViaje: " + rs.getInt("idViaje"));
				System.out.println("identificadorConcierto: " + rs.getString("identificadorConcierto"));
				System.out.println("nombreConcierto: " + rs.getString("nombreConcierto"));
				System.out.println("nomMusico: " + rs.getString("nomMusico"));
				System.out.println("esConductor: " + rs.getBoolean("esConductor"));
				System.out.println("kmIda: " + rs.getDouble("kmIda"));
				System.out.println("kmVuelta: " + rs.getDouble("kmVuelta"));
				System.out.println("costoPagar: " + rs.getDouble("costoPagar"));
				System.out.println("totalAdelantado: " + rs.getDouble("totalAdelantado"));
				System.out.println("fechaConcierto: " + rs.getString("fechaConcierto"));
				System.out.println("resultadoPagar: " + rs.getString("resultadoPagar"));
				System.out.println("----------------------------");
			}
		} catch (SQLException e) {
			System.err.println("Error al realizar la consulta: " + e.getMessage());
		}
	}

	/**
	 * Método que muestra los cómics de la librería según la sentencia SQL
	 * proporcionada.
	 * 
	 * @param sentenciaSQL La sentencia SQL para obtener los cómics de la librería.
	 * @return Una lista de objetos Evento que representan los cómics de la
	 *         librería.
	 */
	public static List<Evento> verEventos(String sentenciaSQL, boolean esActualizacion) {
		ListasEventosDAO.listaEventos.clear(); // Limpiar la lista existente de cómics
		List<Evento> listaEventos = new ArrayList<>();

		try (Connection conn = ConectManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sentenciaSQL, ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				listaEventos.add(DBUtilidades.obtenerEventoDesdeResultSet(rs));
			}

		} catch (SQLException e) {
			Utilidades.manejarExcepcion(e);
		}

		return listaEventos;
	}

	/**
	 * Devuelve un objeto Comic cuya ID coincida con el parámetro de búsqueda.
	 *
	 * @param identificador el ID del cómic a buscar
	 * @return el objeto Comic encontrado, o null si no se encontró ningún cómic con
	 *         ese ID
	 * @throws SQLException si ocurre algún error al ejecutar la consulta SQL
	 */
	public static Evento eventoDatos(String identificador) {

		Evento comic = null;

		try (Connection conn = ConectManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(SQL_SELECT_ALL_EVENTO_INDIVIDUAL)) {
			statement.setString(1, identificador);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					comic = DBUtilidades.obtenerEventoDesdeResultSet(rs);
				}
			} catch (SQLException e) {
				Utilidades.manejarExcepcion(e);
			}
		} catch (SQLException e) {
			Utilidades.manejarExcepcion(e);
		}

		return comic;
	}
	
	/**
	 * Función que busca en el ArrayList el o los cómics que tengan coincidencia con
	 * los datos introducidos en el TextField.
	 *
	 * @param comic           el cómic con los parámetros de búsqueda
	 * @param busquedaGeneral el texto de búsqueda general
	 * @return una lista de cómics que coinciden con los criterios de búsqueda
	 * @throws SQLException si ocurre un error al acceder a la base de datos
	 */
	public static List<Evento> busquedaParametro(Evento comic, String busquedaGeneral) {

		List<Evento> listEvento = new ArrayList<>();
		if (countRows() > 0 && !busquedaGeneral.isEmpty()) {

				listEvento = libreriaSeleccionado(busquedaGeneral);

				if (listEvento.isEmpty()) {
					return new ArrayList<>();
				} else {
					return DBUtilidades.filtroBBDD(comic, busquedaGeneral);

				}
			}
		
		return listEvento;
	}

	public static boolean hayDatosEnLibreria(String sentenciaSQL) {
		if (sentenciaSQL.isEmpty()) {
			sentenciaSQL = SQL_SELECT_ALL_EVENTOS;
		}

		try (Connection conn = ConectManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sentenciaSQL, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery()) {

			// Si hay resultados, devolver true
			return rs.first();

		} catch (SQLException ex) {
			Utilidades.manejarExcepcion(ex);
		}
		return false;
	}
}
