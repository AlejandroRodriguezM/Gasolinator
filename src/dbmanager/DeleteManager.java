package dbmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class DeleteManager {

	private static final String SQL_DELETE_VIAJE = "DELETE FROM viajes WHERE identificadorConcierto = ?;";
	private static final String SQL_DELETE_TABLA_VIAJE = "DELETE FROM viajes;";
	private static final String SENTENCIA_REBOOT_ID = "DELETE FROM sqlite_sequence WHERE name = 'eventosDB';";

	public static void deleteViaje(String identificadorConcierto) {

		try (PreparedStatement pstmt = ConectManager.getConnection().prepareStatement(SQL_DELETE_VIAJE)) {

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
	
	/**
	 * Borra y reinicia la base de datos de manera asíncrona.
	 *
	 * @return CompletableFuture que indica si la operación se realizó con éxito o
	 *         no.
	 */
	public static CompletableFuture<Boolean> deleteTablaViaje() {
		return CompletableFuture.supplyAsync(() -> {
			try {
				ListasEventosDAO.limpiarListasAutocompletado();
				CompletableFuture<Boolean> result = CompletableFuture.supplyAsync(() -> {
					try (Connection conn = ConectManager.getConnection();
							PreparedStatement deleteStatement = conn.prepareStatement(SQL_DELETE_TABLA_VIAJE);
							PreparedStatement resetAutoIncrementStatement = conn
									.prepareStatement(SENTENCIA_REBOOT_ID)) {

						deleteStatement.executeUpdate();
						resetAutoIncrementStatement.executeUpdate();
						return true;
					} catch (Exception e) {
						throw new RuntimeException(e); // Envuelve excepción en RuntimeException para ser manejada en el
														// nivel superior
					}
				});

				return result.join(); // Espera a que la operación asíncrona se complete y devuelve el resultado
			} catch (Exception e) {
				throw new RuntimeException(e); // Envuelve excepción en RuntimeException para ser manejada en el nivel
												// superior
			}
		}, Executors.newCachedThreadPool()); // Se utiliza un Executor diferente para ejecutar el CompletableFuture en
												// un hilo separado
	}
}
