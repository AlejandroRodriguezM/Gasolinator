/**
 * Contiene las clases que hacen funcionar las ventanas
 *  
*/
package Controladores;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.gluonhq.charm.glisten.control.ProgressIndicator;

import alarmas.AlarmaList;
import dbmanager.ConectManager;
import dbmanager.ListasEventosDAO;
import ficherosFunciones.FuncionesFicheros;
import funcionesAuxiliares.Utilidades;
import funcionesAuxiliares.Ventanas;
import funcionesInterfaz.FuncionesManejoFront;
import funcionesManagment.AccionReferencias;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Esta clase sirve para acceder a la base de datos y poder realizar diferentes
 * funciones.
 *
 * @author Alejandro Rodriguez
 */
public class AccesoBBDDController implements Initializable {

	/**
	 * Etiqueta para mostrar una alarma relacionada con la conexión.
	 */
	@FXML
	private Label alarmaConexion;

	/**
	 * Etiqueta para mostrar una alarma relacionada con la conexión a Internet.
	 */
	@FXML
	private Label alarmaConexionInternet;

	/**
	 * Etiqueta para mostrar una alarma relacionada con la conexión a la base de
	 * datos SQL.
	 */
	@FXML
	private Label alarmaConexionSql;

	/**
	 * Botón para acceder a la base de datos.
	 */
	@FXML
	private Button botonAccesobbdd;

	/**
	 * Botón para enviar datos.
	 */
	@FXML
	private Button botonEnviar;

	/**
	 * Etiqueta para mostrar el estado de la conexión.
	 */
	@FXML
	private Label prontEstadoConexion;

	/**
	 * Campo de texto para ingresar la contraseña del usuario.
	 */
	@FXML
	private TextField passUsuarioTextField;

	@FXML
	private ProgressIndicator progresoCarga;

	@FXML
	private MenuItem menuItemOpciones;

	@FXML
	private MenuItem menuItemSalir;

	@FXML
	private MenuItem menuItemSobreMi;

	/**
	 * Objeto para gestionar las ventanas de navegación.
	 */
	private static Ventanas nav = new Ventanas();

	private static AlarmaList alarmaList = new AlarmaList();

	private static boolean estaConectado = false;

	public static final AccionReferencias referenciaVentana = new AccionReferencias();

	public AccionReferencias guardarReferencia() {
		referenciaVentana.setBotonIntroducir(botonEnviar);
		referenciaVentana.setStageVentana(myStage());
		referenciaVentana.setLabelComprobar(prontEstadoConexion);
		referenciaVentana.setLabelVersion(alarmaConexion);

		return referenciaVentana;
	}

	/**
	 * Inicializa el controlador cuando se carga la vista.
	 *
	 * @param location  la ubicación del archivo FXML
	 * @param resources los recursos utilizados por la vista
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (!Utilidades.verificarVersionJava()) {
			Platform.exit();
		}

		ConectManager.estadoConexion = false;
		estaConectado = false;

		alarmaList.setAlarmaConexion(alarmaConexion);
		alarmaList.setAlarmaConexionInternet(alarmaConexionInternet);
		alarmaList.setAlarmaConexionSql(alarmaConexionSql);
		alarmaList.setAlarmaConexionPrincipal(prontEstadoConexion);

		List<Stage> stageVentanas = FuncionesManejoFront.getStageVentanas();

		for (Stage stage : stageVentanas) {
			stage.close(); // Close the stage if it's not the current state
		}

		Platform.runLater(() -> {
			myStage().setOnCloseRequest(event -> stop());

			ConectManager.asignarValoresPorDefecto();
			ListasEventosDAO.reiniciarListaEventos();
			ConectManager.close();

			alarmaList.iniciarThreadChecker();

			if (Utilidades.isInternetAvailable()) {
				Utilidades.cargarTasasDeCambioDesdeArchivo();
			}
			Utilidades.crearDBPRedeterminada();

			ConectManager.closeConnection();
		});
	}

	public static void estadoBotonConexion(AccionReferencias referenciaDatos) {

		if (!estaConectado) {
			referenciaDatos.getBotonIntroducir().setText("Desconectar bbdd");
			alarmaList.manejarConexionExitosa(referenciaDatos.getLabelComprobar());
			AlarmaList.iniciarAnimacionConectado(referenciaDatos.getLabelComprobar());
			estaConectado = true;
		} else {
			referenciaDatos.getBotonIntroducir().setText("Conectar bbdd");
			ConectManager.estadoConexion = false;
			AlarmaList.iniciarAnimacionAlarma(referenciaDatos.getLabelVersion());
			AlarmaList.iniciarAnimacionEspera(referenciaDatos.getLabelComprobar());
			estaConectado = false;
		}
	}

	/**
	 * Funcion que permite el acceso a la ventana de menuPrincipal
	 *
	 * @param event
	 */
	@FXML
	void entrarMenu(ActionEvent event) {

		if (estaConectado) { // Siempre que el metodo de la clase DBManager sea true, permitira acceder
			ConectManager.resetConnection();
			// al menu principal
			nav.cerrarOpcionesDB();
			nav.verMenuPrincipal();
			closeWindow();
		} else { // En caso contrario mostrara el siguiente mensaje.
			AlarmaList.detenerAnimacion();
			ConectManager.asignarValoresPorDefecto();
			prontEstadoConexion.setStyle("-fx-background-color: #DD370F");
			String mensaje = "ERROR. Conectate primero";
			AlarmaList.iniciarAnimacionConexionError(prontEstadoConexion, mensaje);
		}
		alarmaList.detenerThreadChecker();
	}

	/**
	 * Maneja el evento de enviar datos a la base de datos.
	 *
	 * @param event El evento de acción que desencadenó la función.
	 * @throws SQLException
	 */
	@FXML
	void enviarDatos(ActionEvent event) {

		if (Utilidades.comprobarDB() && ConectManager.loadDriver()) {

			if (ConectManager.getConnection() != null) {

				if (!estaConectado) {
					botonEnviar.setText("Desconectar bbdd");
					alarmaList.manejarConexionExitosa(prontEstadoConexion);
					AlarmaList.iniciarAnimacionConectado(prontEstadoConexion);
					estaConectado = true;
				} else {
					botonEnviar.setText("Conectar bbdd");
					ConectManager.estadoConexion = false;
					AlarmaList.iniciarAnimacionAlarma(alarmaConexion);
					AlarmaList.iniciarAnimacionEspera(prontEstadoConexion);
					estaConectado = false;
				}

			} else {
				AlarmaList.detenerAnimacion();
				String mensaje = "ERROR. No estás conectado a la base de datos.";
				AlarmaList.iniciarAnimacionConexionError(prontEstadoConexion, mensaje);
				estaConectado = false;
			}
		} else {
			AlarmaList.detenerAnimacion();
			String mensaje1 = "ERROR. Ve a opciones y guarda la base de datos o en su defecto, crea otra.";
			String mensaje2 = "ERROR. No hay guardada niguna DB, ve a opciones.";
			nav.alertaException(mensaje1);
			AlarmaList.iniciarAnimacionConexionError(prontEstadoConexion, mensaje2);
			estaConectado = false;
		}
		System.out.println(estaConectado);
	}

	/**
	 * Permite salir completamente del programa.
	 *
	 * @param event
	 */
	@FXML
	public void salirPrograma(ActionEvent event) {

		if (nav.salirPrograma(event)) { // Llamada a metodo que permite salir completamente del programa
			myStage().close();
			stop();
		}
	}

	public Scene miStageVentana() {

		return botonEnviar.getScene();

	}

	public Stage myStage() {
		return (Stage) miStageVentana().getWindow();

	}

	/**
	 * Permite el cambio de ventana a la ventana de SobreMiController
	 *
	 * @param event
	 */
	@FXML
	void verSobreMi(ActionEvent event) {
		nav.verSobreMi();
	}

	/**
	 * Cierra el programa a la fuerza correctamente.
	 */
	public void closeWindow() { // Metodo que permite cerrar completamente el programa en caso de cerrar a la //

		myStage().close();
	}

	public void stop() {
		Platform.exit();
	}
}
