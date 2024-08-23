package Controladores;

import java.io.IOException;
import java.sql.SQLException;

import FuncionesUI.Ventanas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Esta clase es donde se ejecuta la ventana principal del programa.
 *
 * @author Alejandro Rodriguez
 */
public class VentanaPrincipalController extends Application {

	/**
	 * Instancia de la clase Ventanas para la navegación.
	 */
	private static Ventanas nav = new Ventanas();

	/**
	 * Carga la ventana principal y arranca el programa.
	 *
	 * @param primaryStage La ventana principal (Stage) que se utiliza para mostrar
	 *                     la interfaz de usuario.
	 * @throws IOException  Si ocurre un error al cargar la interfaz de usuario
	 *                      desde el archivo FXML.
	 * @throws SQLException Si ocurre un error relacionado con la base de datos.
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, SQLException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/ventanas/AccesoBBDD.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.setTitle("Gasolinator"); // Título de la aplicación.
			primaryStage.show();
			primaryStage.getIcons().add(new Image("/Icono/icon.png"));

		} catch (IOException e) {
			nav.alertaException(e.toString());
		}
	}

	/**
	 * Método principal para iniciar la aplicación.
	 *
	 * @param args Los argumentos de línea de comandos (no se utilizan en esta
	 *             aplicación).
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
