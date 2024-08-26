
package Controladores;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import EventoManagement.Evento;
import EventoManagement.EventoCache;
import alarmas.AlarmaList;
import dbmanager.ConectManager;
import dbmanager.DBUtilidades;
import dbmanager.ListasEventosDAO;
import dbmanager.ListasEventosDAO;
import funcionesAuxiliares.Utilidades;
import funcionesAuxiliares.Ventanas;
import funcionesInterfaz.AccionControlUI;
import funcionesInterfaz.FuncionesComboBox;
import funcionesInterfaz.FuncionesManejoFront;
import funcionesInterfaz.FuncionesTableView;
import funcionesManagment.AccionAniadir;
import funcionesManagment.AccionEliminar;
import funcionesManagment.AccionFuncionesComunes;
import funcionesManagment.AccionModificar;
import funcionesManagment.AccionReferencias;
import funcionesManagment.AccionSeleccionar;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Clase controladora para la ventana de acciones, que gestiona la interfaz de
 * usuario y las operaciones relacionadas con los cómics.
 */
public class VentanaAccionController implements Initializable {
	@FXML
	private Label alarmaConexionInternet;

	@FXML
	private Label alarmaConexionSql;

	@FXML
	private Button botonBusquedaAvanzada;

	@FXML
	private Button botonBusquedaCodigo;

	@FXML
	private Button botonCancelarSubida;

	@FXML
	private Button botonClonarEvento;

	@FXML
	private Button botonEliminar;

	@FXML
	private Button botonEliminarImportadoEvento;

	@FXML
	private Button botonEliminarImportadoListaEvento;

	@FXML
	private Button botonGuardarCambioEvento;

	@FXML
	private Button botonGuardarEvento;

	@FXML
	private Button botonGuardarListaEventos;

	@FXML
	private Button botonLimpiar;

	@FXML
	private Button botonModificarEvento;

	@FXML
	private Button botonParametroEvento;

	@FXML
	private Button botonSubidaPortada;

	@FXML
	private Button botonbbdd;

	@FXML
	private TextField busquedaCodigo;

	@FXML
	private ImageView cargaImagen;

	@FXML
	private TableColumn<Evento, String> columnaArtista;

	@FXML
	private TableColumn<Evento, String> columnaPrecio;

	@FXML
	private TableColumn<Evento, String> columnaGuionista;

	@FXML
	private TableColumn<Evento, String> columnaVariante;

	@FXML
	private TableColumn<Evento, String> columnaNombre;

	@FXML
	private TableColumn<Evento, String> columnaNumero;

	@FXML
	private ComboBox<String> comboBoxTienda;

	@FXML
	private DatePicker dataPickFechaP;

	@FXML
	private TableColumn<Evento, String> id;

	@FXML
	private Label labelArtista;

	@FXML
	private Label labelCodigo;

	@FXML
	private Label labelEditor;

	@FXML
	private Label labelFechaG;

	@FXML
	private Label labelGuionista;

	@FXML
	private Label labelId;

	@FXML
	private Label labelKey;

	@FXML
	private Label labelNombre;

	@FXML
	private Label labelPortada;

	@FXML
	private Label labelReferencia;

	@FXML
	private Label labelVariante;

	@FXML
	private Label labelPrecio;

	@FXML
	private MenuItem menuImportarFichero;

	@FXML
	private MenuBar menuNavegacion;

	@FXML
	private Menu navegacionEstadistica;

	@FXML
	private MenuItem navegacionMostrarEstadistica;

	@FXML
	private Menu navegacionOpciones;

	@FXML
	private ProgressIndicator progresoCarga;

	@FXML
	private TextArea prontInfo;

	@FXML
	private VBox rootVBox;

	@FXML
	private TableView<Evento> tablaBBDD;

	@FXML
	private TextArea textAreaKeyEvento;

	@FXML
	private TextField textFieldArtistaEvento;

	@FXML
	private TextField textFieldCodigoEvento;

	@FXML
	private TextField textFieldDireccionEvento;

	@FXML
	private TextField textFieldEditorEvento;

	@FXML
	private TextField textFieldGuionistaEvento;

	@FXML
	private TextField textFieldIdEvento;

	@FXML
	private TextField textFieldNombreEvento;

	@FXML
	private TextField textFieldNumeroEvento;

	@FXML
	private TextField textFieldUrlEvento;

	@FXML
	private TextField textFieldVarianteEvento;

	@FXML
	private TextField textFieldPrecioEvento;

	@FXML
	private TextField textFieldFirmaEvento;

	@FXML
	private VBox vboxImage;

	/**
	 * Referencia a la ventana (stage).
	 */
	private Stage stage;

	public Evento eventoCache;

	/**
	 * Instancia de la clase Ventanas para la navegación.
	 */
	private static Ventanas nav = new Ventanas();

	public static final AlarmaList alarmaList = new AlarmaList();

	public static final AccionReferencias referenciaVentana = new AccionReferencias();

	public static AccionReferencias referenciaVentanaPrincipal = new AccionReferencias();

	private static final Logger logger = Logger.getLogger(Utilidades.class.getName());

	public AccionReferencias guardarReferencia() {

		referenciaVentana.setTituloEventoTextField(textFieldNombreEvento);
		referenciaVentana.setCodigoEventoTextField(textFieldCodigoEvento);
		referenciaVentana.setDataPickFechaP(dataPickFechaP);
		referenciaVentana.setIdEventoTratarTextField(textFieldIdEvento);
		referenciaVentana.setBusquedaCodigoTextField(busquedaCodigo);
		referenciaVentana.setPrecioEventoTextField(textFieldPrecioEvento);

		referenciaVentana.setBotonClonarEvento(botonClonarEvento);
		referenciaVentana.setBotonCancelarSubida(botonCancelarSubida);
		referenciaVentana.setBotonBusquedaCodigo(botonBusquedaCodigo);
		referenciaVentana.setBotonBusquedaAvanzada(botonBusquedaAvanzada);
		referenciaVentana.setBotonEliminar(botonEliminar);
		referenciaVentana.setBotonLimpiar(botonLimpiar);
		referenciaVentana.setBotonModificarEvento(botonModificarEvento);
		referenciaVentana.setBotonParametroEvento(botonParametroEvento);
		referenciaVentana.setBotonbbdd(botonbbdd);
		referenciaVentana.setBotonGuardarCambioEvento(botonGuardarCambioEvento);

		referenciaVentana.setBotonGuardarEvento(botonGuardarEvento);
		referenciaVentana.setBotonEliminarImportadoEvento(botonEliminarImportadoEvento);

		referenciaVentana.setBotonEliminarImportadoListaEvento(botonEliminarImportadoListaEvento);
		referenciaVentana.setBotonGuardarListaEventos(botonGuardarListaEventos);

		referenciaVentana.setBotonSubidaPortada(botonSubidaPortada);
		referenciaVentana.setBusquedaCodigoTextField(busquedaCodigo);

		referenciaVentana.setProgresoCarga(progresoCarga);
		referenciaVentana.setLabelfechaG(labelFechaG);

		referenciaVentana.setLabelCodigo(labelCodigo);
		referenciaVentana.setLabelReferencia(labelReferencia);

		referenciaVentana.setAlarmaConexionInternet(alarmaConexionInternet);
		referenciaVentana.setAlarmaConexionSql(alarmaConexionSql);

		referenciaVentana.setTablaBBDD(tablaBBDD);
		referenciaVentana.setCargaImagen(cargaImagen);
		referenciaVentana.setProntInfoTextArea(prontInfo);
		referenciaVentana.setRootVBox(rootVBox);
		referenciaVentana.setMenuNavegacion(menuNavegacion);
		referenciaVentana.setNavegacionCerrar(navegacionOpciones);
		referenciaVentana.setStageVentana(estadoStage());
		AccionReferencias.setListaTextFields(FXCollections
				.observableArrayList(Arrays.asList(textFieldNombreEvento, textFieldNumeroEvento, textFieldEditorEvento,
						textFieldArtistaEvento, textFieldVarianteEvento, textFieldGuionistaEvento,
						textFieldEditorEvento, textFieldIdEvento, textFieldDireccionEvento, textFieldUrlEvento)));

		referenciaVentana.setControlAccion(Arrays.asList(textFieldNombreEvento, textFieldNumeroEvento, dataPickFechaP,
				textFieldArtistaEvento, textFieldVarianteEvento, textFieldGuionistaEvento, textFieldUrlEvento,
				textFieldDireccionEvento, textAreaKeyEvento, textFieldEditorEvento, textFieldCodigoEvento,
				textFieldPrecioEvento, textFieldFirmaEvento, textFieldIdEvento));

		AccionReferencias.setListaColumnasTabla(Arrays.asList(columnaNombre, columnaNumero, columnaArtista,
				columnaGuionista, columnaVariante, columnaPrecio));

		return referenciaVentana;
	}

	public void enviarReferencias() {
		AccionControlUI.setReferenciaVentana(guardarReferencia());
		AccionFuncionesComunes.setReferenciaVentana(guardarReferencia());
		AccionFuncionesComunes.setReferenciaVentanaPrincipal(referenciaVentanaPrincipal);
		FuncionesTableView.setReferenciaVentana(guardarReferencia());
		FuncionesManejoFront.setReferenciaVentana(guardarReferencia());

		AccionSeleccionar.setReferenciaVentana(guardarReferencia());
		AccionAniadir.setReferenciaVentana(guardarReferencia());
		AccionEliminar.setReferenciaVentana(guardarReferencia());
		AccionModificar.setReferenciaVentana(guardarReferencia());
		Utilidades.setReferenciaVentana(guardarReferencia());
		Ventanas.setReferenciaVentana(guardarReferencia());
		DBUtilidades.setReferenciaVentana(guardarReferencia());
	}

	/**
	 * Inicializa la interfaz de usuario y configura el comportamiento de los
	 * elementos al cargar la vista.
	 *
	 * @param location  La ubicación relativa del archivo FXML.
	 * @param resources Los recursos que pueden ser utilizados por el controlador.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		alarmaList.setAlarmaConexionSql(alarmaConexionSql);
		alarmaList.setAlarmaConexionInternet(alarmaConexionInternet);
		alarmaList.iniciarThreadChecker();

		Platform.runLater(() -> {

			enviarReferencias();

			AccionControlUI.controlarEventosInterfazAccion();

			AccionControlUI.autocompletarListas();

			rellenarCombosEstaticos();

			AccionControlUI.mostrarOpcion(AccionFuncionesComunes.getTipoAccion());

			FuncionesManejoFront.getStageVentanas().add(estadoStage());

			estadoStage().setOnCloseRequest(event -> stop());
			AccionSeleccionar.actualizarRefrenciaClick(guardarReferencia());
		});

		ListasEventosDAO.eventosImportados.clear();

		AccionControlUI.establecerTooltips();

		formatearTextField();

	}

	/**
	 * Rellena los combos estáticos en la interfaz. Esta función llena los
	 * ComboBoxes con opciones estáticas predefinidas.
	 */
	public void rellenarCombosEstaticos() {
		List<ComboBox<String>> listaComboboxes = new ArrayList<>();
		listaComboboxes.add(comboBoxTienda);
		FuncionesComboBox.rellenarComboBoxEstaticos(listaComboboxes);
	}

	public void formatearTextField() {
		FuncionesManejoFront.eliminarEspacioInicialYFinal(textFieldNombreEvento);
		FuncionesManejoFront.eliminarSimbolosEspeciales(textFieldNombreEvento);
		FuncionesManejoFront.restringirSimbolos(textFieldEditorEvento);
//		FuncionesManejoFront.restringirSimbolos(textFieldGradeoEvento);

		FuncionesManejoFront.reemplazarEspaciosMultiples(textFieldNombreEvento);
		FuncionesManejoFront.reemplazarEspaciosMultiples(textFieldArtistaEvento);
		FuncionesManejoFront.reemplazarEspaciosMultiples(textFieldArtistaEvento);
		FuncionesManejoFront.reemplazarEspaciosMultiples(textFieldVarianteEvento);
		FuncionesManejoFront.reemplazarEspaciosMultiples(textFieldGuionistaEvento);
		FuncionesManejoFront.reemplazarEspaciosMultiples(textFieldCodigoEvento);
		FuncionesManejoFront.reemplazarEspaciosMultiples(textFieldNumeroEvento);
		FuncionesManejoFront.restringirSimboloClave(textAreaKeyEvento);
		FuncionesManejoFront.restringirSimboloClave(textFieldEditorEvento);
		FuncionesManejoFront.permitirUnSimbolo(textFieldNombreEvento);
		FuncionesManejoFront.permitirUnSimbolo(busquedaCodigo);
		textFieldIdEvento.setTextFormatter(FuncionesComboBox.validadorNenteros());

		if (AccionFuncionesComunes.TIPO_ACCION.equalsIgnoreCase("aniadir")) {
			textFieldIdEvento.setTextFormatter(FuncionesComboBox.desactivarValidadorNenteros());
		}
	}

	/**
	 * Metodo que mostrara los eventos o evento buscados por parametro
	 *
	 * @param event
	 * @throws SQLException
	 */
	@FXML
	void mostrarPorParametro(ActionEvent event) {
		enviarReferencias();

		List<String> controls = new ArrayList<>();

		// Iterar sobre los TextField y ComboBox en referenciaVentana
		for (Control control : AccionReferencias.getListaTextFields()) {
			if (control instanceof TextField) {
				controls.add(((TextField) control).getText());
			} else if (control instanceof ComboBox<?>) {
				Object selectedItem = ((ComboBox<?>) control).getSelectionModel().getSelectedItem();
				controls.add(selectedItem != null ? selectedItem.toString() : "");
			}
		}

		// Añadir valores de los ComboBoxes de getListaComboboxes() a controls
		for (ComboBox<?> comboBox : AccionReferencias.getListaComboboxes()) {
			Object selectedItem = comboBox.getSelectionModel().getSelectedItem();
			controls.add(selectedItem != null ? selectedItem.toString() : "");
		}

		// Crear y procesar la Evento con los controles recogidos
		Evento evento = AccionControlUI.camposEvento(controls, true);
		AccionSeleccionar.verBasedeDatos(false, true, evento);
	}

	/**
	 * Metodo que muestra toda la base de datos.
	 *
	 * @param event
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	void verTodabbdd(ActionEvent event) {
		enviarReferencias();
		AccionControlUI.limpiarAutorellenos(false);
		AccionSeleccionar.verBasedeDatos(true, true, null);
	}

	/**
	 * Método que maneja el evento de guardar los datos de un cómic.
	 * 
	 * @param event El evento de acción que desencadena la llamada al método.
	 */
	@FXML
	void guardarDatos(ActionEvent event) {
		enviarReferencias();
		rellenarCombosEstaticos();
		nav.cerrarMenuOpciones();
		AccionModificar.actualizarEventoLista();
		EventoCache.setEventoCache(null);
		ocultarBotonesEventos();
	}

	/**
	 * Método que maneja el evento de guardar la lista de cómics importados.
	 * 
	 * @param event El evento de acción que desencadena la llamada al método.
	 * @throws IOException        Si ocurre un error de entrada/salida.
	 * @throws SQLException       Si ocurre un error de base de datos.
	 * @throws URISyntaxException
	 */
	@FXML
	void guardarEventoImportados(ActionEvent event) throws IOException, SQLException {
		enviarReferencias();
		nav.cerrarMenuOpciones();
		AccionAniadir.guardarContenidoLista(false, EventoCache.getEventoCache());
		rellenarCombosEstaticos();
		ocultarBotonesEventos();
	}

	/**
	 * Método que maneja el evento de guardar la lista de cómics importados.
	 * 
	 * @param event El evento de acción que desencadena la llamada al método.
	 * @throws IOException        Si ocurre un error de entrada/salida.
	 * @throws SQLException       Si ocurre un error de base de datos.
	 * @throws URISyntaxException
	 */
	@FXML
	void guardarListaImportados(ActionEvent event) throws IOException, SQLException {
		enviarReferencias();
		nav.cerrarMenuOpciones();
		AccionAniadir.guardarContenidoLista(true, null);
		rellenarCombosEstaticos();
		ocultarBotonesEventos();
	}

	/**
	 * Llamada a funcion que modifica los datos de 1 evento en la base de datos.
	 *
	 * @param event
	 * @throws Exception
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@FXML
	void modificarDatos(ActionEvent event) throws Exception {
		enviarReferencias();
		nav.cerrarMenuOpciones();
		AccionModificar.modificarEvento();
		rellenarCombosEstaticos();
	}

	@FXML
	void clonarEventoSeleccionada(ActionEvent event) {

		int num = Ventanas.verVentanaNumero();
		Evento evento = guardarReferencia().getTablaBBDD().getSelectionModel().getSelectedItem();
		EventoCache.setEventoCache(evento);

		for (int i = 0; i < num; i++) {
			Evento eventoModificada = AccionFuncionesComunes.copiarEventoClon(evento);
			AccionFuncionesComunes.procesarEventoPorCodigo(eventoModificada, true);
		}
	}

	@FXML
	void eliminarEventoSeleccionado(ActionEvent event) {
		enviarReferencias();
		nav.cerrarMenuOpciones();
		AccionModificar.eliminarEventoLista();
		rellenarCombosEstaticos();
		ocultarBotonesEventos();
	}

	@FXML
	void eliminarListaEventos(ActionEvent event) {
		enviarReferencias();
		nav.cerrarMenuOpciones();

		if (!ListasEventosDAO.eventosImportados.isEmpty() && nav.alertaBorradoLista()) {
			// Ocultar botones relacionados con eventos
			ocultarBotonesEventos();

			// Limpiar la lista de eventos y la tabla de la interfaz
			ListasEventosDAO.eventosImportados.clear();
			guardarReferencia().getTablaBBDD().getItems().clear();
			limpiarVentana();
		}

		// Rellenar combos estáticos después de la operación
		rellenarCombosEstaticos();
	}

	// Función para ocultar botones relacionados con eventos
	private void ocultarBotonesEventos() {
		guardarReferencia().getBotonClonarEvento().setVisible(false);
		guardarReferencia().getBotonGuardarEvento().setVisible(false);
		guardarReferencia().getBotonEliminarImportadoEvento().setVisible(false);
	}

	/**
	 * Funcion que permite mostrar la imagen de portada cuando clickeas en una
	 * tabla.
	 *
	 * @param event
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	void clickRaton(MouseEvent event) {
		enviarReferencias();
		if (!tablaBBDD.isDisabled()) {

			Evento evento = guardarReferencia().getTablaBBDD().getSelectionModel().getSelectedItem();
			EventoCache.setEventoCache(evento);
			AccionSeleccionar.seleccionarEventos(false);
		}
	}

	/**
	 * Funcion que permite mostrar la imagen de portada cuando usas las teclas de
	 * direccion en una tabla.
	 *
	 * @param event
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	void teclasDireccion(KeyEvent event) {
		enviarReferencias();
		if ((event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) && !tablaBBDD.isDisabled()) {
			Evento evento = guardarReferencia().getTablaBBDD().getSelectionModel().getSelectedItem();
			EventoCache.setEventoCache(evento);
			AccionSeleccionar.seleccionarEventos(false);
		}

	}

	public File createTempFile(List<String> data) throws IOException {

		String tempDirectory = System.getProperty("java.io.tmpdir");

		// Create a temporary file in the system temporary directory
		Path tempFilePath = Files.createTempFile(Paths.get(tempDirectory), "tempFile", ".txt");
		logger.log(Level.INFO, "Temporary file created at: " + tempFilePath.toString());

		// Write data to the temporary file
		Files.write(tempFilePath, data, StandardOpenOption.WRITE);

		// Convert the Path to a File and return it
		return tempFilePath.toFile();
	}

	public void deleteFile(Path filePath) throws IOException {
		Files.delete(filePath);
	}

	/**
	 * Limpia los datos de la pantalla al hacer clic en el botón "Limpiar".
	 */
	@FXML
	void limpiarDatos(ActionEvent event) {
		limpiarVentana();
	}

	public void limpiarVentana() {
		enviarReferencias();
		AccionFuncionesComunes.limpiarDatosPantallaAccion();
		rellenarCombosEstaticos();
		EventoCache.setEventoCache(null);
	}

	/**
	 * Funcion que elimina un evento de la base de datos.
	 *
	 * @param event
	 * @throws IOException
	 * @throws SQLException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@FXML
	void eliminarDatos(ActionEvent event) {
		enviarReferencias();
		nav.cerrarMenuOpciones();
		AccionModificar.eliminarEvento();
		rellenarCombosEstaticos();

	}

	/**
	 * Establece la instancia de la ventana (Stage) asociada a este controlador.
	 *
	 * @param stage La instancia de la ventana (Stage) que se asocia con este
	 *              controlador.
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Scene miStageVentana() {
		Node rootNode = botonLimpiar;
		while (rootNode.getParent() != null) {
			rootNode = rootNode.getParent();
		}

		if (rootNode instanceof Parent) {
			Scene scene = ((Parent) rootNode).getScene();
			ConectManager.activeScenes.add(scene);
			return scene;
		} else {
			// Manejar el caso en el que no se pueda encontrar un nodo raíz adecuado
			return null;
		}
	}

	public Stage estadoStage() {

		return (Stage) botonLimpiar.getScene().getWindow();
	}

	/**
	 * Cierra la ventana asociada a este controlador, si está disponible. Si no se
	 * ha establecido una instancia de ventana (Stage), este método no realiza
	 * ninguna acción.
	 */
	public void closeWindow() {

		stage = estadoStage();
		EventoCache.setEventoCache(null);
		if (stage != null) {

			if (FuncionesManejoFront.getStageVentanas().contains(estadoStage())) {
				FuncionesManejoFront.getStageVentanas().remove(estadoStage());
			}
			nav.cerrarCargaEventos();
			stage.close();
		}
	}

	public void stop() {

		if (FuncionesManejoFront.getStageVentanas().contains(estadoStage())) {
			FuncionesManejoFront.getStageVentanas().remove(estadoStage());
		}

		Utilidades.cerrarCargaEventos();
	}

	public static void setReferenciaVentana(AccionReferencias referenciaVentana) {
		VentanaAccionController.referenciaVentanaPrincipal = referenciaVentana;
	}
}
