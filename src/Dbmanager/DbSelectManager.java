package Dbmanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbSelectManager {
	
	private static final String SQL_SELECT_VIAJE = "SELECT * FROM viajes WHERE identificadorConcierto = ? OR nomMusico = ? OR fechaConcierto = ?;";

	// Consultas SQL para seleccionar campos específicos
	private static final String SQL_SELECT_ALL_MUSICOS = "SELECT * FROM musicos;";

	private static final String SQL_SELECT_NOMBRE_MUSICOS = "SELECT nombreMusico FROM musicos;";

	private static final String SQL_SELECT_DIRECCION_FOTO_MUSICOS = "SELECT direccionFoto FROM musicos;";
	
	// Método para seleccionar todos los campos de la tabla 'musicos'
    public static void selectAllMusicos(DbConnection dbConnection) {
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(SQL_SELECT_ALL_MUSICOS);
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

    // Método para seleccionar solo los nombres de la tabla 'musicos'
    public static void selectNombreMusicos(DbConnection dbConnection) {
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(SQL_SELECT_NOMBRE_MUSICOS);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("nombreMusico: " + rs.getString("nombreMusico"));
            }
        } catch (SQLException e) {
            System.err.println("Error al realizar la consulta (SELECT nombreMusico): " + e.getMessage());
        }
    }

    // Método para seleccionar solo las direcciones de foto de la tabla 'musicos'
    public static void selectDireccionFotoMusicos(DbConnection dbConnection) {
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(SQL_SELECT_DIRECCION_FOTO_MUSICOS);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("direccionFoto: " + rs.getString("direccionFoto"));
            }
        } catch (SQLException e) {
            System.err.println("Error al realizar la consulta (SELECT direccionFoto): " + e.getMessage());
        }
    }
	
	// Método para seleccionar registros de la tabla viajes
	public static void selectViaje(DbConnection dbConnection, String identificadorConcierto, String nomMusico,
			String fechaConcierto) {
		try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(SQL_SELECT_VIAJE)) {
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

}
