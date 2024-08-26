package dbmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import EventoManagement.Evento;
import funcionesAuxiliares.Utilidades;
import funcionesManagment.AccionReferencias;

public class DBUtilidades {

	private static AccionReferencias referenciaVentana = getReferenciaVentana();

	public enum TipoBusqueda {
		COMPLETA, ELIMINAR
	}

	public static void setParameters(PreparedStatement ps, Evento datos, boolean includeID) throws SQLException {
		// Establecer parámetros para los campos de Evento
		ps.setString(1, datos.getIdentificadorConcierto());
		ps.setString(2, datos.getCodigoMusico());
		ps.setBoolean(3, datos.isEsConductor());
		ps.setDouble(4, datos.getKmIda());
		ps.setDouble(5, datos.getKmVuelta());
		ps.setDouble(6, datos.getCostoPagar());
		ps.setDouble(7, datos.getTotalAdelantado());
		ps.setString(8, datos.getFechaConcierto());
		ps.setString(9, datos.getResultadoPagar());

		// Establecer el ID si se requiere para actualizaciones
		if (includeID) {
			ps.setString(10, datos.getIdEvento());
		}
	}

	public static String construirSentenciaSQL(TipoBusqueda tipoBusqueda) {
		switch (tipoBusqueda) {
		case COMPLETA:
			return SelectManager.SQL_SELECT_ALL_EVENTOS;
		default:
			throw new IllegalArgumentException("Tipo de búsqueda no válido");
		}
	}

	public static String datosConcatenados(Evento evento) {
		String connector = " WHERE ";

		// Construir la consulta SQL base
		StringBuilder sql = new StringBuilder(SelectManager.SQL_SELECT_ALL_EVENTOS);

		// Agregar condiciones para cada campo del evento
		connector = agregarCondicion(sql, connector, "identificadorConcierto", evento.getIdentificadorConcierto());
		connector = agregarCondicion(sql, connector, "codigoMusico", evento.getCodigoMusico());
		connector = agregarCondicion(sql, connector, "esConductor", String.valueOf(evento.isEsConductor()));
		connector = agregarCondicion(sql, connector, "kmIda", String.valueOf(evento.getKmIda()));
		connector = agregarCondicion(sql, connector, "kmVuelta", String.valueOf(evento.getKmVuelta()));
		connector = agregarCondicion(sql, connector, "costoPagar", String.valueOf(evento.getCostoPagar()));
		connector = agregarCondicion(sql, connector, "totalAdelantado", String.valueOf(evento.getTotalAdelantado()));
		connector = agregarCondicion(sql, connector, "fechaConcierto", evento.getFechaConcierto());
		connector = agregarCondicion(sql, connector, "resultadoPagar", evento.getResultadoPagar());

		// Si no se ha agregado ninguna condición, devolver una cadena vacía
		if (connector.trim().equalsIgnoreCase("WHERE")) {
			return "";
		}

		// Devolver la consulta SQL con las condiciones agregadas
		return (connector.length() > 0) ? sql.toString() : "";
	}

	public static String agregarCondicion(StringBuilder sql, String connector, String columna, String valor) {
		if (valor != null && !valor.isEmpty()) {
			sql.append(connector).append(columna).append(" = '").append(valor).append("'");
			return " AND ";
		}
		return connector;
	}

	public static String agregarCondicionLike(StringBuilder sql, String connector, String columna, String valor) {
		if (valor != null && !valor.isEmpty()) {
			sql.append(connector).append(columna).append(" LIKE '%").append(valor).append("%'");
			return " AND ";
		}
		return connector;
	}

	public static String datosGenerales(String tipoBusqueda, String busquedaGeneral) {
		String connector = " WHERE ";
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM eventosBbdd");

		switch (tipoBusqueda.toLowerCase()) {
		case "identificadorConcierto":
			sql.append(connector).append("identificadorConcierto LIKE '%" + busquedaGeneral + "%';");
			break;
		case "codigoMusico":
			sql.append(connector).append("codigoMusico LIKE '%" + busquedaGeneral + "%';");
			break;
		default:
			break;
		}

		return sql.toString();
	}

	public static List<Evento> verBusquedaGeneral(String busquedaGeneral) {
		String sql1 = datosGenerales("identificadorConcierto", busquedaGeneral);
		String sql2 = datosGenerales("codigoMusico", busquedaGeneral);
		try (Connection conn = ConectManager.getConnection();
				PreparedStatement ps1 = conn.prepareStatement(sql1);
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ResultSet rs1 = ps1.executeQuery();
				ResultSet rs2 = ps2.executeQuery()) {

			ListasEventosDAO.listaEventos.clear();

			agregarSiHayDatos(rs1);
			agregarSiHayDatos(rs2);

			ListasEventosDAO.listaEventos = (List<Evento>) ListasEventosDAO
					.listaArregladaAutoComplete((List) ListasEventosDAO.listaEventos);

			return ListasEventosDAO.listaEventos;

		} catch (SQLException ex) {
			Utilidades.manejarExcepcion(ex);
		}

		return Collections.emptyList();
	}

	public static void agregarSiHayDatos(ResultSet rs) throws SQLException {
		if (rs.next()) {
			obtenerEventoDesdeResultSet(rs);
		}
	}

	public static List<Evento> filtroBBDD(Evento datos, String busquedaGeneral) {

		ListasEventosDAO.listaEventos.clear();

		String sql = datosConcatenados(datos);

		if (!sql.isEmpty()) {
			try (Connection conn = ConectManager.getConnection();
					PreparedStatement ps = conn.prepareStatement(sql);
					ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					ListasEventosDAO.listaEventos.add(obtenerEventoDesdeResultSet(rs));
				}

				return ListasEventosDAO.listaEventos;
			} catch (SQLException ex) {
				Utilidades.manejarExcepcion(ex);
			}
		} else {
			getReferenciaVentana().getProntInfoTextArea().setOpacity(1);
			getReferenciaVentana().getProntInfoTextArea().setStyle("-fx-text-fill: red;");
			getReferenciaVentana().getProntInfoTextArea()
					.setText("Error: No existe Evento con los datos: " + busquedaGeneral + datos.toString());
		}

		if (sql.isEmpty() && busquedaGeneral.isEmpty()) {
			getReferenciaVentana().getProntInfoTextArea().setOpacity(1);
			getReferenciaVentana().getProntInfoTextArea().setStyle("-fx-text-fill: red;");
			getReferenciaVentana().getProntInfoTextArea().setText("Todos los campos están vacíos");
		}

		return ListasEventosDAO.listaEventos;
	}

	public static List<String> obtenerValoresColumnaEvento(String columna) {
		String sentenciaSQL = "SELECT " + columna + " FROM viajes ORDER BY " + columna + ";";

		ListasEventosDAO.listaEventos.clear();
		return ListasEventosDAO.guardarDatosAutoCompletado(sentenciaSQL, columna);
	}
	
	public static List<String> obtenerValoresColumnaMusicos(String columna) {
		String sentenciaSQL = "SELECT " + columna + " FROM musicos ORDER BY " + columna + ";";

		ListasEventosDAO.listaEventos.clear();
		return ListasEventosDAO.guardarDatosAutoCompletado(sentenciaSQL, columna);
	}
	
	public static List<String> obtenerValoresColumnaConciertos(String columna) {
		String sentenciaSQL = "SELECT " + columna + " FROM conciertos ORDER BY " + columna + ";";

		ListasEventosDAO.listaEventos.clear();
		return ListasEventosDAO.guardarDatosAutoCompletado(sentenciaSQL, columna);
	}

	public static Evento obtenerEventoDesdeResultSet(ResultSet rs) {
		try {
			String idEvento = rs.getString("idEvento");
			String identificadorConcierto = rs.getString("identificadorConcierto");
			String codigoMusico = rs.getString("codigoMusico");
			boolean esConductor = rs.getBoolean("esConductor");
			double kmIda = rs.getDouble("kmIda");
			double kmVuelta = rs.getDouble("kmVuelta");
			double costoPagar = rs.getDouble("costoPagar");
			double totalAdelantado = rs.getDouble("totalAdelantado");
			String fechaConcierto = rs.getString("fechaConcierto");
			String resultadoPagar = rs.getString("resultadoPagar");

			return new Evento(idEvento, identificadorConcierto, codigoMusico, esConductor, kmIda, kmVuelta, costoPagar,
					totalAdelantado, fechaConcierto, resultadoPagar);
		} catch (SQLException e) {
			Utilidades.manejarExcepcion(e);
			return null;
		}
	}

	public static AccionReferencias getReferenciaVentana() {
		return referenciaVentana;
	}

	public static void setReferenciaVentana(AccionReferencias referenciaVentana) {
		DBUtilidades.referenciaVentana = referenciaVentana;
	}
}
