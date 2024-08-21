package Dbmanager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbDeleteManager {

	private static final String SQL_DELETE_VIAJE = "DELETE FROM viajes WHERE identificadorConcierto = ?;";
	private static final String SQL_DELETE_TABLA_VIAJE = "DELETE FROM viajes;";

	public static void deleteViaje(String identificadorConcierto) {

		try (PreparedStatement pstmt = DbConnection.getConnection().prepareStatement(SQL_DELETE_VIAJE)) {

			// Set the identifier parameter
			pstmt.setString(1, identificadorConcierto);

			// Execute the DELETE query
			int rowsAffected = pstmt.executeUpdate();

			// Optionally, you can check if any rows were affected
			if (rowsAffected > 0) {
				System.out.println("Successfully deleted the viaje with identificador: " + identificadorConcierto);
			} else {
				System.out.println("No viaje found with identificador: " + identificadorConcierto);
			}
		} catch (SQLException e) {
			// Handle SQL exceptions
			System.err.println("SQL error occurred while deleting viaje: " + e.getMessage());
		}
	}

	public static void deleteTablaViaje() {
		DbConnection.executeUpdate(SQL_DELETE_TABLA_VIAJE);
	}

}
