package dbmanager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import EventoManagement.Evento;

public class UpdateManager {

	// SQL UPDATE statement
		private static final String SQL_UPDATE_VIAJE = "UPDATE viajes SET nombreConcierto = ?, nomMusico = ?, "
				+ "esConductor = ?, kmIda = ?, kmVuelta = ?,costoPagar = ?, totalAdelantado = ?, "
				+ "fechaConcierto = ?, resultadoPagar = ?WHERE identificadorConcierto = ?;";

		// Método para actualizar un registro en la tabla viajes
		public static void updateViaje(Evento evento) {
			if (evento == null) {
				throw new IllegalArgumentException("El evento no puede ser nulo.");
			}

			try (PreparedStatement pstmt = ConectManager.getConnection().prepareStatement(SQL_UPDATE_VIAJE)) {
				// Set parameters based on the Evento object
				pstmt.setString(1, evento.getCodigoMusico());
				pstmt.setString(2, evento.getCodigoMusico());
				pstmt.setBoolean(3, evento.isEsConductor());
				pstmt.setDouble(4, evento.getKmIda());
				pstmt.setDouble(5, evento.getKmVuelta());
				pstmt.setDouble(6, evento.getCostoPagar());
				pstmt.setDouble(7, evento.getTotalAdelantado());

				// Convertir la fecha de String a java.sql.Date
				java.sql.Date fechaConcierto = java.sql.Date.valueOf(evento.getFechaConcierto());
				pstmt.setDate(8, fechaConcierto);

				pstmt.setString(9, evento.getResultadoPagar());
				pstmt.setString(10, evento.getIdentificadorConcierto());

				// Execute the UPDATE query
				int rowsAffected = pstmt.executeUpdate();

				// Optionally, check if any rows were affected
				if (rowsAffected > 0) {
					System.out.println(
							"Successfully updated the viaje with identificador: " + evento.getIdentificadorConcierto());
				} else {
					System.out.println("No viaje found with identificador: " + evento.getIdentificadorConcierto());
				}
			} catch (SQLException e) {
				// Handle SQL exceptions
				System.err.println("SQL error occurred while updating viaje: " + e.getMessage());
			}
		}
}
