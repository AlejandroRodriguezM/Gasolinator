package funcionesManagment;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import Controladores.CargaEventosController;
import EventoManagement.Evento;
import alarmas.AlarmaList;
import dbmanager.ListasEventosDAO;
import funcionesAuxiliares.Utilidades;
import funcionesAuxiliares.Ventanas;
import funcionesInterfaz.AccionControlUI;
import funcionesInterfaz.FuncionesComboBox;
import funcionesInterfaz.FuncionesTableView;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public class AccionFuncionesComunes {

	private static File fichero;
	private static AtomicInteger contadorErrores;
	private static AtomicInteger eventosProcesados;
	private static AtomicInteger mensajeIdCounter;
	private static AtomicInteger numLineas;
	private static AtomicReference<CargaEventosController> cargaEventosControllerRef;
	private static StringBuilder codigoFaltante;
	private static HashSet<String> mensajesUnicos = new HashSet<>();

	/**
	 * Obtenemos el directorio de inicio del usuario
	 */
	private static final String USER_DIR = System.getProperty("user.home");

	/**
	 * Construimos la ruta al directorio "Documents"
	 */
	private static final String DOCUMENTS_PATH = USER_DIR + File.separator + "Documents";

	/**
	 * Instancia de la clase Ventanas para la navegación.
	 */
	private static Ventanas nav = new Ventanas();

	/**
	 * Instancia de la clase FuncionesComboBox para el manejo de ComboBox.
	 */
	private static FuncionesComboBox funcionesCombo = new FuncionesComboBox();

	public static String TIPO_ACCION = getTipoAccion();

	private static AccionReferencias referenciaVentana = getReferenciaVentana();

	private static AccionReferencias referenciaVentanaPrincipal = getReferenciaVentanaPrincipal();

	private static AccionControlUI accionRellenoDatos = new AccionControlUI();

	/**
	 * Procesa la información de un cómic, ya sea para realizar una modificación o
	 * una inserción en la base de datos.
	 *
	 * @param evento          El cómic con la información a procesar.
	 * @param esModificacion Indica si se está realizando una modificación (true) o
	 *                       una inserción (false).
	 * @throws Exception
	 */
	public void procesarEvento(Evento evento, boolean esModificacion) {
		getReferenciaVentana().getProntInfoTextArea().setOpacity(1);

		if (!accionRellenoDatos.camposEventoSonValidos()) {
			mostrarErrorDatosIncorrectos();
			return;
		}

		String mensaje = "";

		try {

			if (esModificacion) {
				EventoManagerDAO.actualizarEventoBBDD(evento, "modificar");
				mensaje = "Has modificado correctamente el cómic";
			} else {
				procesarNuevaEvento(evento);
				mensaje = "Has introducido correctamente el cómic";
			}

			AlarmaList.mostrarMensajePront(mensaje, esModificacion, getReferenciaVentana().getProntInfoTextArea());
			procesarBloqueComun(evento);

		} catch (IOException | SQLException e) {
			Utilidades.manejarExcepcion(e);
		}
	}

	private void mostrarErrorDatosIncorrectos() {
		String mensaje = "Error. Debes de introducir los datos correctos";
		AlarmaList.mostrarMensajePront(mensaje, false, getReferenciaVentana().getProntInfoTextArea());
		List<Evento> eventosFinal = ListasEventosDAO.eventosImportados;
		Platform.runLater(() -> FuncionesTableView.tablaBBDD(eventosFinal));
	}

	private void procesarNuevaEvento(Evento evento) {
		EventoManagerDAO.insertarDatos(evento, true);
		Evento newSelection = getReferenciaVentana().getTablaBBDD().getSelectionModel().getSelectedItem();
		List<Evento> eventosFinal;

		if (newSelection != null) {
			String idEventoGradeo = newSelection.getIdEvento();
			ListasEventosDAO.eventosImportados.removeIf(c -> c.getIdEvento().equals(idEventoGradeo));
			getReferenciaVentana().getTablaBBDD().getItems().clear();
			eventosFinal = ListasEventosDAO.eventosImportados;
		} else {
			eventosFinal = null; // Inicializar eventosFinal en caso de que no haya ningún cómic seleccionado
		}

		Platform.runLater(() -> FuncionesTableView.tablaBBDD(eventosFinal));
		getReferenciaVentana().getTablaBBDD().refresh();
	}

	/**
	 * Procesa el bloque común utilizado en la función procesarEventoGradeo para
	 * actualizar la interfaz gráfica y realizar operaciones relacionadas con la
	 * manipulación de imágenes y la actualización de listas y combos.
	 *
	 * @param evento El objeto EventoGradeo que se está procesando.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	private void procesarBloqueComun(Evento evento) {

		ListasEventosDAO.listasAutoCompletado();
		FuncionesTableView.nombreColumnas();
		FuncionesTableView.actualizarBusquedaRaw();
	}

	public static boolean procesarEventoPorCodigo(Evento eventoInfo, boolean esClonar) {

		boolean alMenosUnoProcesado = false;

		if (comprobarCodigo(eventoInfo)) {
			rellenarTablaImport(eventoInfo, esClonar);
			alMenosUnoProcesado = true;
		}

		return alMenosUnoProcesado;
	}

	public static void actualizarInformacionEventos(Evento eventoOriginal) {

		String numEventoGradeo = eventoOriginal.getNumeroEvento();
		String nombreCorregido = Utilidades.eliminarParentesis(eventoOriginal.getTituloEvento());
		String nombreLimpio = Utilidades.extraerNombreLimpio(nombreCorregido);
		nombreLimpio = DatabaseManagerDAO.corregirPatrones(nombreLimpio);
		String editor = eventoOriginal.getEditorEvento();

		eventoOriginal.setTituloEvento(nombreLimpio);
		eventoOriginal.setNumeroEvento(numEventoGradeo);
		eventoOriginal.setEditorEvento(editor);
		eventoOriginal.setDireccionImagenEvento(eventoOriginal.getDireccionImagenEvento());

	}

	public static void actualizarCompletoEventos(Evento eventoOriginal) {

		String numEventoGradeo = eventoOriginal.getNumeroEvento();
		String nombreCorregido = Utilidades.eliminarParentesis(eventoOriginal.getTituloEvento());
		String nombreLimpio = Utilidades.extraerNombreLimpio(nombreCorregido);
		nombreLimpio = DatabaseManagerDAO.corregirPatrones(nombreLimpio);
		String editor = (eventoOriginal.getEditorEvento());

		eventoOriginal.setTituloEvento(nombreLimpio);
		eventoOriginal.setNumeroEvento(numEventoGradeo);
		eventoOriginal.setEditorEvento(editor);

	}

	public static String urlFinalImagen(String direccionImagen) {

		String urlImagen = direccionImagen;

		return urlImagen.replace("\\", "/").replaceFirst("^http:", "https:");
	}

	public static String determinarTipoTienda(String url) {
		if (url.contains("previewsworld")) {
			return "previews world";
		} else if (url.contains("marvel.com/eventos/issue/")) {
			return "marvel";
		} else if (url.contains("leagueofeventogeeks.com/evento/")) {
			return "league of eventos";
		} else if (url.contains("panini.es")) {
			return "panini";
		}
		return "";
	}

	public static Evento copiarEventoClon(Evento eventoOriginal) {
		try {
			// Clonar la evento original
			Evento eventoClon = (Evento) eventoOriginal.clone();

			// Obtener la dirección actual de la imagen
			String direccionActual = eventoOriginal.getDireccionImagenEvento();

			// Verificar si la dirección actual es válida
			if (direccionActual != null && !direccionActual.isEmpty()) {
				File imagenActual = new File(direccionActual);

				// Verificar si el archivo existe antes de proceder
				if (imagenActual.exists()) {
					// Devolver la evento clonada con la nueva dirección de imagen
					return eventoClon;
				}
			}

			// Si la dirección actual no es válida o el archivo no existe, devolver la evento
			// original
			return eventoOriginal;
		} catch (CloneNotSupportedException | IOException e) {
			// Manejar la excepción de clonación o de operaciones de archivos
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Limpia y restablece todos los campos de datos en la sección de animaciones a
	 * sus valores predeterminados. Además, restablece la imagen de fondo y oculta
	 * cualquier mensaje de error o información.
	 */
	public static void limpiarDatosPantallaAccion() {
		// Restablecer los campos de datos

		if (!ListasEventosDAO.eventosImportados.isEmpty() && nav.alertaBorradoLista()) {
			getReferenciaVentana().getBotonGuardarEvento().setVisible(false);
			getReferenciaVentana().getBotonEliminarImportadoEvento().setVisible(false);
			ListasEventosDAO.eventosImportados.clear();
		}
		getReferenciaVentana().getTablaBBDD().getItems().clear();
		getReferenciaVentana().getTablaBBDD().refresh();
		getReferenciaVentana().getBotonClonarEvento().setVisible(false);
		getReferenciaVentana().getProntInfoTextArea().setOpacity(0);
		getReferenciaVentana().getTablaBBDD().refresh();
		getReferenciaVentana().getBotonEliminarImportadoListaEvento().setVisible(false);
		getReferenciaVentana().getBotonGuardarListaEventos().setVisible(false);

		getReferenciaVentana().getBotonEliminarImportadoListaEvento().setDisable(true);
		getReferenciaVentana().getBotonGuardarListaEventos().setDisable(true);
		limpiarDatosEvento();
	}

	public static void limpiarDatosEvento() {
		// Limpiar campos de texto
		getReferenciaVentana().getTituloEventoTextField().setText("");
		getReferenciaVentana().getCodigoEventoTextField().setText("");
		getReferenciaVentana().getNombreEditorTextField().setText("");
		getReferenciaVentana().getArtistaEventoTextField().setText("");
		getReferenciaVentana().getGuionistaEventoTextField().setText("");
		getReferenciaVentana().getVarianteTextField().setText("");
		getReferenciaVentana().getIdEventoTratarTextField().setText("");
		getReferenciaVentana().getDireccionImagenTextField().setText("");
		getReferenciaVentana().getUrlReferenciaTextField().setText("");
		getReferenciaVentana().getNumeroEventoTextField().setText("");
		getReferenciaVentana().getFirmaEventoTextField().setText("");
		getReferenciaVentana().getPrecioEventoTextField().setText("");

		// Limpiar el TextArea
		getReferenciaVentana().getKeyEventoData().setText(""); // Asegúrate de tener el método get para el TextArea

		// Limpiar el DatePicker
		getReferenciaVentana().getDataPickFechaP().setValue(null); // Asegúrate de tener el método get para el
																	// DatePicker
		if ("modificar".equals(TIPO_ACCION)) {
			AccionControlUI.mostrarOpcion(TIPO_ACCION);
		}

		if ("aniadir".equals(TIPO_ACCION) && getReferenciaVentana().getNombreTiendaCombobox().isVisible()) {
			AccionFuncionesComunes.cambiarVisibilidadAvanzada();
		}

		// Borrar cualquier mensaje de error presente
		borrarErrores();
		AccionControlUI.validarCamposClave(true);
		AccionControlUI.borrarDatosGraficos();
	}

	/**
	 * Comprueba la existencia de un cómic en la base de datos y realiza acciones
	 * dependiendo del resultado.
	 *
	 * @param ID El identificador del cómic a verificar.
	 * @return true si el cómic existe en la base de datos y se realizan las
	 *         acciones correspondientes, false de lo contrario.
	 * @throws SQLException Si ocurre un error al interactuar con la base de datos.
	 */
	public boolean comprobarExistenciaEvento(String idEvento) {

		// Si el cómic existe en la base de datos
		if (EventoManagerDAO.comprobarIdentificadorEvento(idEvento)) {
			FuncionesTableView.actualizarBusquedaRaw();
			return true;
		} else { // Si el cómic no existe en la base de datos
			String mensaje = "ERROR. ID desconocido.";
			AlarmaList.mostrarMensajePront(mensaje, false, getReferenciaVentana().getProntInfoTextArea());
			return false;
		}
	}

	/**
	 * Verifica si se ha encontrado información válida para el cómic.
	 *
	 * @param eventoInfo Un objeto EventoGradeo con la información del cómic.
	 * @return True si la información es válida y existe; de lo contrario, False.
	 */
	private static boolean comprobarCodigo(Evento eventoInfo) {
		return eventoInfo != null;
	}

	/**
	 * Rellena los campos de la interfaz gráfica con la información del cómic
	 * proporcionada.
	 *
	 * @param eventoInfo Un arreglo de strings con información del cómic.
	 * @throws IOException
	 */
	private static void rellenarTablaImport(Evento evento, boolean esClonar) {
		Platform.runLater(() -> {
			String idEvento = "A" + 0 + "" + (ListasEventosDAO.eventosImportados.size() + 1);
			String tituloEvento = Utilidades.defaultIfNullOrEmpty(evento.getTituloEvento(), "Vacio");
			String codigoEvento = Utilidades.defaultIfNullOrEmpty(evento.getCodigoEvento(), "0");
			String numeroEvento = Utilidades.defaultIfNullOrEmpty(evento.getNumeroEvento(), "0");
			String fechaGradeo = Utilidades.defaultIfNullOrEmpty(evento.getFechaGradeo(), "2000-01-01");
			String editorEvento = Utilidades.defaultIfNullOrEmpty(evento.getEditorEvento(), "Vacio");
			String keyComentarios = Utilidades.defaultIfNullOrEmpty(evento.getKeyComentarios(), "Vacio");
			String artistaEvento = Utilidades.defaultIfNullOrEmpty(evento.getArtistaEvento(), "Vacio");
			String guionistaEvento = Utilidades.defaultIfNullOrEmpty(evento.getGuionistaEvento(), "Vacio");
			String varianteEvento = Utilidades.defaultIfNullOrEmpty(evento.getVarianteEvento(), "Vacio");
			String direccionImagenEvento = Utilidades.defaultIfNullOrEmpty(evento.getDireccionImagenEvento(), "Vacio");
			String urlReferenciaEvento = Utilidades.defaultIfNullOrEmpty(evento.getUrlReferenciaEvento(), "Vacio");
			String valorEvento = Utilidades.defaultIfNullOrEmpty(evento.getPrecioEvento(), "Vacio");
			String firmaEvento = Utilidades.defaultIfNullOrEmpty(evento.getFirmaEvento(), "");
			// Construcción del objeto EventoGradeo usando el builder
			Evento eventoImport = new Evento.EventoGradeoBuilder(idEvento, tituloEvento).codigoEvento(codigoEvento)
					.precioEvento(valorEvento).numeroEvento(numeroEvento).fechaGradeo(fechaGradeo).editorEvento(editorEvento)
					.keyComentarios(keyComentarios).firmaEvento(firmaEvento).artistaEvento(artistaEvento)
					.guionistaEvento(guionistaEvento).varianteEvento(varianteEvento).direccionImagenEvento(imagen)
					.urlReferenciaEvento(urlReferenciaEvento).build();

			// Añadir el cómic a la lista e actualizar la tabla
			ListasEventosDAO.eventosImportados.add(eventoImport);
			FuncionesTableView.nombreColumnas();
			FuncionesTableView.tablaBBDD(ListasEventosDAO.eventosImportados);
		});
	}

	public static void controlCargaEventos(int numCargas) {
		codigoFaltante = new StringBuilder();
		codigoFaltante.setLength(0);
		contadorErrores = new AtomicInteger(0);
		eventosProcesados = new AtomicInteger(0);
		mensajeIdCounter = new AtomicInteger(0);
		numLineas = new AtomicInteger(0);
		numLineas.set(numCargas);
		cargaEventosControllerRef = new AtomicReference<>();
		mensajesUnicos = new HashSet<>();
		mensajesUnicos.clear();
	}

	public static void actualizarCombobox() {
		ListasEventosDAO.reiniciarListaEventos();
		ListasEventosDAO.listasAutoCompletado();

		getReferenciaVentana();
		List<ComboBox<String>> comboboxes = AccionReferencias.getListaComboboxes();

		if (comboboxes != null) {
			funcionesCombo.rellenarComboBox(comboboxes);
		}
	}

	/**
	 * Elimina cualquier resaltado de campos en rojo que indique errores.
	 */
	public static void borrarErrores() {

		if (getReferenciaVentana() != null) {
			setStyleIfNotNull(getReferenciaVentana().getTituloEventoTextField());
			setStyleIfNotNull(getReferenciaVentana().getNumeroEventoCombobox());
			setStyleIfNotNull(getReferenciaVentana().getNombreEditorTextField());
			setStyleIfNotNull(getReferenciaVentana().getArtistaEventoTextField());
			setStyleIfNotNull(getReferenciaVentana().getGuionistaEventoTextField());
			setStyleIfNotNull(getReferenciaVentana().getVarianteTextField());
		}
	}

	public static void setStyleIfNotNull(Node element) {
		if (element != null) {
			element.setStyle("");
		}
	}

	/**
	 * Modifica la visibilidad y el estado de los elementos de búsqueda en la
	 * interfaz de usuario.
	 *
	 * @param mostrar True para mostrar los elementos de búsqueda, False para
	 *                ocultarlos.
	 */
	public static void cambiarVisibilidadAvanzada() {

		List<Node> elementos = Arrays.asList(getReferenciaVentana().getBotonBusquedaCodigo(),
				getReferenciaVentana().getBusquedaCodigoTextField(), getReferenciaVentana().getNombreTiendaCombobox());

		if (getReferenciaVentana().getBotonBusquedaCodigo().isVisible()) {
			Utilidades.cambiarVisibilidad(elementos, true);
		} else {
			getReferenciaVentana().getBusquedaCodigoTextField().setText("");
			Utilidades.cambiarVisibilidad(elementos, false);

		}
	}

	public static void cambiarEstadoBotones(boolean esCancelado) {

		if (TIPO_ACCION != null) {
			List<Node> elementos = Arrays.asList(getReferenciaVentana().getBotonSubidaPortada());

			if (!TIPO_ACCION.equals("aniadir")) {
				elementos.add(getReferenciaVentana().getBotonBusquedaCodigo());
				elementos.add(getReferenciaVentana().getBusquedaCodigoTextField());
				elementos.add(getReferenciaVentana().getBotonCancelarSubida());
				elementos.add(getReferenciaVentana().getBotonBusquedaCodigo());
				elementos.add(getReferenciaVentana().getBotonSubidaPortada());
				elementos.add(getReferenciaVentana().getNombreTiendaCombobox());
			}

			getReferenciaVentana().getBotonCancelarSubida().setVisible(esCancelado);

			if (getReferenciaVentana().getBotonLimpiar() != null) {
				getReferenciaVentana().getBotonLimpiar().setDisable(esCancelado);
				getReferenciaVentana().getBotonBusquedaAvanzada().setDisable(esCancelado);
			}

			Utilidades.cambiarVisibilidad(elementos, esCancelado);
		}

	}

	public static String carpetaRaizPortadas(String nombreDatabase) {
		return DOCUMENTS_PATH + File.separator + "libreria_eventos" + File.separator + nombreDatabase + File.separator;
	}

	public static String carpetaPortadas(String nombreDatabase) {
		return DOCUMENTS_PATH + File.separator + "libreria_eventos" + File.separator + nombreDatabase + File.separator
				+ "portadas";
	}

	public static void setTipoAccion(String tipoAccion) {
		TIPO_ACCION = tipoAccion;
	}

	public static String getTipoAccion() {
		return TIPO_ACCION;
	}

	public static AccionReferencias getReferenciaVentana() {
		return referenciaVentana;
	}

	public static AccionReferencias getReferenciaVentanaPrincipal() {
		return referenciaVentanaPrincipal;
	}

	public static void setReferenciaVentana(AccionReferencias referenciaVentana) {
		AccionFuncionesComunes.referenciaVentana = referenciaVentana;
	}

	public static void setReferenciaVentanaPrincipal(AccionReferencias referenciaVentana) {
		AccionFuncionesComunes.referenciaVentanaPrincipal = referenciaVentana;
	}

}
