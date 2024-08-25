package funcionesInterfaz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controladores.VentanaAccionController;
import EventoManagement.Evento;
import alarmas.AlarmaList;
import dbmanager.ListasEventosDAO;
import funcionesAuxiliares.Utilidades;
import funcionesManagment.AccionAniadir;
import funcionesManagment.AccionEliminar;
import funcionesManagment.AccionFuncionesComunes;
import funcionesManagment.AccionModificar;
import funcionesManagment.AccionReferencias;
import funcionesManagment.AccionSeleccionar;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;

public class AccionControlUI {

	private static AccionReferencias referenciaVentana = getReferenciaVentana();

	private static VentanaAccionController accionController = new VentanaAccionController();

	private static AccionAniadir accionAniadir = new AccionAniadir();

	private static AccionEliminar accionEliminar = new AccionEliminar();

	private static AccionModificar accionModificar = new AccionModificar();

	public static void autoRelleno() {

		referenciaVentana.getIdEventoTratarTextField().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!rellenarCampos(newValue)) {
					limpiarAutorellenos(false);
					borrarDatosGraficos();
				}
			} else {
				limpiarAutorellenos(false);
				borrarDatosGraficos();
			}
		});
	}

	public static boolean rellenarCampos(String idEvento) {
		Evento eventoTempTemp = Evento.obtenerEvento(idEvento);
		if (eventoTempTemp != null) {
			rellenarDatos(eventoTempTemp);
			return true;
		}
		return false;
	}

	public static void mostrarOpcion(String opcion) {
		ocultarCampos();

		List<Node> elementosAMostrarYHabilitar = new ArrayList<>();

		switch (opcion.toLowerCase()) {
		case "eliminar":
			accionEliminar.mostrarElementosEliminar(elementosAMostrarYHabilitar);
			break;
		case "aniadir":
			accionAniadir.mostrarElementosAniadir(elementosAMostrarYHabilitar);
			break;
		case "modificar":
			accionModificar.mostrarElementosModificar(elementosAMostrarYHabilitar);
			break;
		default:
			accionController.closeWindow();
			return;
		}

		mostrarElementos(elementosAMostrarYHabilitar);
	}

	public static List<Node> modificarInterfazAccion(String opcion) {

		List<Node> elementosAMostrarYHabilitar = new ArrayList<>();

		switch (opcion.toLowerCase()) {
		case "modificar":
			elementosAMostrarYHabilitar.add(referenciaVentana.getBotonModificarEvento());
			elementosAMostrarYHabilitar.add(referenciaVentana.getBotonEliminar());
			break;
		case "aniadir":
			elementosAMostrarYHabilitar.add(referenciaVentana.getBotonGuardarEvento());
			elementosAMostrarYHabilitar.add(referenciaVentana.getBotonEliminarImportadoEvento());
			elementosAMostrarYHabilitar.add(referenciaVentana.getBotonClonarEvento());
			break;
		default:
			break;
		}

		return elementosAMostrarYHabilitar;
	}

	private static void mostrarElementos(List<Node> elementosAMostrarYHabilitar) {
		for (Node elemento : elementosAMostrarYHabilitar) {

			if (elemento != null) {
				elemento.setVisible(true);
				elemento.setDisable(false);
			}
		}

		if (!AccionFuncionesComunes.TIPO_ACCION.equals("modificar")) {
			autoRelleno();
		}

		if (!AccionFuncionesComunes.TIPO_ACCION.equals("aniadir")) {

			referenciaVentana.getNavegacionCerrar().setDisable(true);
			referenciaVentana.getNavegacionCerrar().setVisible(false);
		} else {
			referenciaVentana.getIdEventoTratarTextField().setEditable(false);
			referenciaVentana.getIdEventoTratarTextField().setOpacity(0.7);
		}

		if (AccionFuncionesComunes.TIPO_ACCION.equals("aniadir")) {
			referenciaVentana.getBotonEliminarImportadoListaEvento().setVisible(false);
			referenciaVentana.getBotonGuardarListaEventos().setVisible(false);

			referenciaVentana.getBotonEliminarImportadoListaEvento().setDisable(true);
			referenciaVentana.getBotonGuardarListaEventos().setDisable(true);
		}
		if (AccionFuncionesComunes.TIPO_ACCION.equals("modificar")) {

			referenciaVentana.getBotonModificarEvento().setVisible(false);
			referenciaVentana.getBotonModificarEvento().setDisable(true);

			referenciaVentana.getBotonEliminar().setVisible(false);
			referenciaVentana.getBotonEliminar().setDisable(true);
		}

	}

	/**
	 * Oculta y deshabilita varios campos y elementos en la interfaz gráfica.
	 */
	public static void ocultarCampos() {

//		List<Node> elementosTextfield = Arrays.asList(referenciaVentana.getGradeoEventoTextField(),
//				referenciaVentana.getDireccionImagenTextField(), referenciaVentana.getUrlReferenciaTextField(),
//				referenciaVentana.getNombreEmpresaTextField(), referenciaVentana.getAnioEventoTextField(),
//				referenciaVentana.getCodigoEventoTextField());
//
//		List<Node> elementosLabel = Arrays.asList(referenciaVentana.getLabelGradeo(),
//				referenciaVentana.getLabelPortada(), referenciaVentana.getLabelReferencia(),
//				referenciaVentana.getLabelEmpresa(), referenciaVentana.getLabelCodigo(),
//				referenciaVentana.getLabelAnio());
//
//		List<Node> elementosBoton = Arrays.asList(referenciaVentana.getBotonSubidaPortada(),
//				referenciaVentana.getBotonEliminar(), referenciaVentana.getBotonModificarEvento(),
//				referenciaVentana.getBotonBusquedaCodigo(), referenciaVentana.getBotonbbdd());
//
//		Utilidades.cambiarVisibilidad(elementosTextfield, true);
//		Utilidades.cambiarVisibilidad(elementosLabel, true);
//		Utilidades.cambiarVisibilidad(elementosBoton, true);
	}

	/**
	 * Establece los atributos del cómic basándose en el objeto Evento proporcionado.
	 * 
	 * @param eventoTempTemp El objeto Evento que contiene los datos a establecer.
	 */
	public void setAtributosDesdeTabla(Evento eventoTemp) {

		referenciaVentana.getIdEventoTratarTextField().setText(eventoTemp.getIdEvento());
		referenciaVentana.getTituloEventoTextField().setText(eventoTemp.getTituloEvento());
		referenciaVentana.getCodigoEventoTextField().setText(eventoTemp.getCodigoEvento());
		referenciaVentana.getNumeroEventoTextField().setText(eventoTemp.getNumeroEvento());
		referenciaVentana.getPrecioEventoTextField().setText(eventoTemp.getPrecioEvento());
		Utilidades.setDatePickerValue(referenciaVentana.getDataPickFechaP(),
				referenciaVentana.getDataPickFechaP().toString());
		referenciaVentana.getNombreEditorTextField().setText(eventoTemp.getEditorEvento());
		referenciaVentana.getFirmaEventoTextField().setText(eventoTemp.getFirmaEvento());
		referenciaVentana.getVarianteTextField().setText(eventoTemp.getVarianteEvento());
		referenciaVentana.getArtistaEventoTextField().setText(eventoTemp.getArtistaEvento());
		referenciaVentana.getGuionistaEventoTextField().setText(eventoTemp.getGuionistaEvento());
		referenciaVentana.getVarianteTextField().setText(eventoTemp.getVarianteEvento());
		referenciaVentana.getKeyEventoData().setText(eventoTemp.getKeyComentarios());
		referenciaVentana.getDireccionImagenTextField().setText(eventoTemp.getDireccionImagenEvento());
		referenciaVentana.getUrlReferenciaTextField().setText(eventoTemp.getUrlReferenciaEvento());
	}

	private static void rellenarDatos(Evento eventoTemp) {

		referenciaVentana.getIdEventoTratarTextField().setText(eventoTemp.getIdEvento());
		referenciaVentana.getTituloEventoTextField().setText(eventoTemp.getTituloEvento());
		referenciaVentana.getCodigoEventoTextField().setText(eventoTemp.getCodigoEvento());
		referenciaVentana.getNumeroEventoTextField().setText(eventoTemp.getNumeroEvento());
		Utilidades.setDatePickerValue(referenciaVentana.getDataPickFechaP(),
				referenciaVentana.getDataPickFechaP().toString());
		referenciaVentana.getNombreEditorTextField().setText(eventoTemp.getEditorEvento());
		referenciaVentana.getVarianteTextField().setText(eventoTemp.getVarianteEvento());
		referenciaVentana.getArtistaEventoTextField().setText(eventoTemp.getArtistaEvento());
		referenciaVentana.getGuionistaEventoTextField().setText(eventoTemp.getGuionistaEvento());
		referenciaVentana.getVarianteTextField().setText(eventoTemp.getVarianteEvento());
		referenciaVentana.getKeyEventoData().setText(eventoTemp.getKeyComentarios());
		referenciaVentana.getDireccionImagenTextField().setText(eventoTemp.getDireccionImagenEvento());
		referenciaVentana.getUrlReferenciaTextField().setText(eventoTemp.getUrlReferenciaEvento());

		referenciaVentana.getProntInfoTextArea().clear();
		referenciaVentana.getProntInfoTextArea().setOpacity(1);
	}

	public static void validarCamposClave(boolean esBorrado) {
		List<TextField> camposUi = Arrays.asList(referenciaVentana.getTituloEventoTextField(),
				referenciaVentana.getNombreEditorTextField(), referenciaVentana.getNombreEditorTextField(),
				referenciaVentana.getArtistaEventoTextField(), referenciaVentana.getVarianteTextField(),
				referenciaVentana.getGuionistaEventoTextField());

		for (TextField campoUi : camposUi) {

			if (campoUi != null) {
				String datoEvento = campoUi.getText();

				if (esBorrado) {
					if (datoEvento == null || datoEvento.isEmpty() || datoEvento.equalsIgnoreCase("vacio")) {
						campoUi.setStyle("");
					}
				} else {
					// Verificar si el campo está vacío, es nulo o tiene el valor "Vacio"
					if (datoEvento == null || datoEvento.isEmpty() || datoEvento.equalsIgnoreCase("vacio")) {
						campoUi.setStyle("-fx-background-color: red;");
					} else {
						campoUi.setStyle("");
					}
				}
			}
		}
	}

	public boolean camposEventoSonValidos() {
		List<TextField> camposUi = Arrays.asList(referenciaVentana.getTituloEventoTextField(),
				referenciaVentana.getNombreEditorTextField(), referenciaVentana.getNombreEditorTextField(),
				referenciaVentana.getArtistaEventoTextField(), referenciaVentana.getVarianteTextField(),
				referenciaVentana.getGuionistaEventoTextField());

		for (Control campoUi : camposUi) {
			String datoEvento = ((TextField) campoUi).getText();

			// Verificar si el campo está vacío, es nulo o tiene el valor "Vacio"
			if (datoEvento == null || datoEvento.isEmpty() || datoEvento.equalsIgnoreCase("vacio")) {
				campoUi.setStyle("-fx-background-color: #FF0000;");
				return false; // Devolver false si al menos un campo no es válido
			} else {
				campoUi.setStyle("");
			}
		}

		return true; // Devolver true si todos los campos son válidos
	}

	public static boolean comprobarListaValidacion(Evento c) {
		// Validar campos requeridos y "vacio"
		if (c.getTituloEvento() == null || c.getTituloEvento().isEmpty() || c.getNumeroEvento() == null
				|| c.getNumeroEvento().isEmpty() || c.getEditorEvento() == null || c.getEditorEvento().isEmpty()
				|| c.getUrlReferenciaEvento() == null || c.getUrlReferenciaEvento().isEmpty()
				|| c.getFechaGradeo() == null || c.getFechaGradeo().isEmpty() || c.getDireccionImagenEvento() == null
				|| c.getDireccionImagenEvento().isEmpty() || c.getCodigoEvento() == null || c.getCodigoEvento().isEmpty()
				|| c.getKeyComentarios() == null || c.getKeyComentarios().isEmpty() || c.getArtistaEvento() == null
				|| c.getArtistaEvento().isEmpty() || c.getGuionistaEvento() == null || c.getGuionistaEvento().isEmpty()
				|| c.getVarianteEvento() == null || c.getVarianteEvento().isEmpty()) {

			String mensajePront = "Revisa la lista, algunos campos están mal rellenados.";
			AlarmaList.mostrarMensajePront(mensajePront, false, referenciaVentana.getProntInfoTextArea());
			return false;
		}

		return true;
	}

	/**
	 * Borra los datos del cómic
	 */
	public static void limpiarAutorellenos(boolean esPrincipal) {

		if (esPrincipal) {
			referenciaVentana.getProntInfoTextArea().clear();
			referenciaVentana.getProntInfoTextArea().setText(null);
			referenciaVentana.getProntInfoTextArea().setOpacity(0);
			referenciaVentana.getTablaBBDD().getItems().clear();
			referenciaVentana.getTablaBBDD().setOpacity(0.6);
			referenciaVentana.getTablaBBDD().refresh();
			return;
		}

		// Limpiar valores en TextField
		referenciaVentana.getIdEventoTratarTextField().setText("");
		referenciaVentana.getTituloEventoTextField().setText("");
		referenciaVentana.getCodigoEventoTextField().setText("");
		referenciaVentana.getNumeroEventoTextField().setText("");
		referenciaVentana.getNombreEditorTextField().setText("");
		referenciaVentana.getVarianteTextField().setText("");
		referenciaVentana.getArtistaEventoTextField().setText("");
		referenciaVentana.getGuionistaEventoTextField().setText("");
		referenciaVentana.getKeyEventoData().setText("");
		referenciaVentana.getDireccionImagenTextField().setText("");
		referenciaVentana.getUrlReferenciaTextField().setText("");

		// Limpiar valor en DatePicker
		referenciaVentana.getDataPickFechaP().setValue(null);

		if ("aniadir".equals(AccionFuncionesComunes.TIPO_ACCION)) {
			referenciaVentana.getIdEventoTratarTextField().setDisable(false);
			referenciaVentana.getIdEventoTratarTextField().setText("");
			referenciaVentana.getIdEventoTratarTextField().setDisable(true);
		} else {
			referenciaVentana.getTablaBBDD().getItems().clear();
			referenciaVentana.getTablaBBDD().refresh();
		}

		if ("modificar".equals(AccionFuncionesComunes.TIPO_ACCION)) {
			referenciaVentana.getBotonModificarEvento().setVisible(false);
			referenciaVentana.getBotonEliminar().setVisible(false);
		}

		referenciaVentana.getProntInfoTextArea().setText(null);
		referenciaVentana.getProntInfoTextArea().setOpacity(0);
		referenciaVentana.getProntInfoTextArea().setStyle("");
		validarCamposClave(true);
	}

	public static void borrarDatosGraficos() {
		referenciaVentana.getProntInfoTextArea().setText(null);
		referenciaVentana.getProntInfoTextArea().setOpacity(0);
		referenciaVentana.getProntInfoTextArea().setStyle("");
	}

	/**
	 * Asigna tooltips a varios elementos en la interfaz gráfica. Estos tooltips
	 * proporcionan información adicional cuando el usuario pasa el ratón sobre los
	 * elementos.
	 */
	public static void establecerTooltips() {
		Platform.runLater(() -> {
			Map<Node, String> tooltipsMap = new HashMap<>();

			tooltipsMap.put(referenciaVentana.getTituloEventoCombobox(), "Nombre de los cómics / libros / mangas");
			tooltipsMap.put(referenciaVentana.getNumeroEventoCombobox(), "Número del cómic / libro / manga");
			tooltipsMap.put(referenciaVentana.getNombreTiendaCombobox(),
					"Nombre de la tienda del cómic / libro / manga");
			tooltipsMap.put(referenciaVentana.getNombreEditorCombobox(), "Nombre del editor del cómic / libro / manga");
			tooltipsMap.put(referenciaVentana.getNombreArtistaCombobox(),
					"Nombre del artista del cómic / libro / manga");
			tooltipsMap.put(referenciaVentana.getNombreGuionistaCombobox(),
					"Nombre del guionista del cómic / libro / manga");
			tooltipsMap.put(referenciaVentana.getNombreVarianteCombobox(), "Variante del cómic / libro / manga");

			tooltipsMap.put(referenciaVentana.getBotonLimpiar(), "Limpia la pantalla y reinicia todos los valores");
			tooltipsMap.put(referenciaVentana.getBotonbbdd(), "Botón para acceder a la base de datos");
			tooltipsMap.put(referenciaVentana.getBotonSubidaPortada(), "Botón para subir una portada");
			tooltipsMap.put(referenciaVentana.getBotonEliminar(), "Botón para eliminar un cómic");
			tooltipsMap.put(referenciaVentana.getBotonParametroEvento(),
					"Botón para buscar un cómic mediante una lista de parámetros");
			tooltipsMap.put(referenciaVentana.getBotonModificarEvento(), "Botón para modificar un cómic");

			tooltipsMap.put(referenciaVentana.getBotonIntroducir(),
					"Realizar una acción de introducción del cómic / libro / manga");
			tooltipsMap.put(referenciaVentana.getBotonModificar(),
					"Realizar una acción de modificación del cómic / libro / manga");
			tooltipsMap.put(referenciaVentana.getBotonEliminar(),
					"Realizar una acción de eliminación del cómic / libro / manga");
			tooltipsMap.put(referenciaVentana.getBotonMostrarParametro(),
					"Buscar por parámetros según los datos rellenados");

			FuncionesTooltips.assignTooltips(tooltipsMap);
		});
	}

	public static void autocompletarListas() {
		FuncionesManejoFront.asignarAutocompletado(referenciaVentana.getTituloEventoTextField(),
				ListasEventosDAO.listaNombre);
		FuncionesManejoFront.asignarAutocompletado(referenciaVentana.getNombreEditorTextField(),
				ListasEventosDAO.listaEditor);
		FuncionesManejoFront.asignarAutocompletado(referenciaVentana.getArtistaEventoTextField(),
				ListasEventosDAO.listaArtista);
		FuncionesManejoFront.asignarAutocompletado(referenciaVentana.getGuionistaEventoTextField(),
				ListasEventosDAO.listaGuionista);
		FuncionesManejoFront.asignarAutocompletado(referenciaVentana.getVarianteTextField(),
				ListasEventosDAO.listaVariante);
		FuncionesManejoFront.asignarAutocompletado(referenciaVentana.getNumeroEventoTextField(),
				ListasEventosDAO.listaNumeroEvento);
	}

	public static void controlarEventosInterfaz() {

		referenciaVentana.getProntInfoTextArea().textProperty().addListener((observable, oldValue, newValue) -> {
			FuncionesTableView.ajustarAnchoVBox();
		});

		// Desactivar el enfoque en el VBox para evitar que reciba eventos de teclado
		referenciaVentana.getRootVBox().setFocusTraversable(false);

		// Agregar un filtro de eventos para capturar el enfoque en el TableView y
		// desactivar el enfoque en el VBox
		referenciaVentana.getTablaBBDD().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			referenciaVentana.getRootVBox().setFocusTraversable(false);
			referenciaVentana.getTablaBBDD().requestFocus();
		});

	}

	public static void controlarEventosInterfazAccion() {
		controlarEventosInterfaz();

		// Establecemos un evento para detectar cambios en el segundo TextField
		referenciaVentana.getIdEventoTratarTextField().textProperty().addListener((observable, oldValue, newValue) -> {
			// Verificar que newValue no sea null antes de usarlo
			AccionSeleccionar.mostrarEvento(newValue, false);

			if (newValue != null && !newValue.isEmpty()) {

				if ("modificar".equals(AccionFuncionesComunes.TIPO_ACCION)) {

					AccionSeleccionar.verBasedeDatos(true, true, null);
					Evento evento = EventoManagerDAO.eventoDatos(newValue);

					referenciaVentana.getBotonEliminar().setVisible(true);
					referenciaVentana.getBotonModificarEvento().setVisible(true);

					referenciaVentana.getBotonEliminar().setDisable(false);
					referenciaVentana.getBotonModificarEvento().setDisable(false);

					referenciaVentana.getTablaBBDD().getSelectionModel().select(evento);
					referenciaVentana.getTablaBBDD().scrollTo(evento); // Esto hará scroll hasta el elemento seleccionado
				}

			}
		});

		List<Node> elementos = Arrays.asList(referenciaVentana.getBotonGuardarEvento(),
				referenciaVentana.getBotonEliminarImportadoEvento(),
				referenciaVentana.getBotonEliminarImportadoListaEvento(), referenciaVentana.getBotonGuardarListaEventos(),
				referenciaVentana.getBotonEliminarImportadoEvento(), referenciaVentana.getBotonGuardarEvento());

		ListasEventosDAO.eventosImportados.addListener((ListChangeListener<Evento>) change -> {
			while (change.next()) {

				if (!change.wasAdded() && ListasEventosDAO.eventosImportados.isEmpty()) {
					Utilidades.cambiarVisibilidad(elementos, true);
				}
			}
		});
	}

	public static void controlarEventosInterfazPrincipal(AccionReferencias referenciaVentana) {
		controlarEventosInterfaz();

		// Establecer un Listener para el tamaño del AnchorPane
		referenciaVentana.getRootAnchorPane().widthProperty().addListener((observable, oldValue, newValue) -> {

			FuncionesTableView.ajustarAnchoVBox();
			FuncionesTableView.seleccionarRaw();
			FuncionesTableView.modificarColumnas(true);
		});
	}

	public static Evento camposEvento(List<String> camposEvento, boolean esAccion) {
		Evento eventoTemp = new Evento();

		String tituloEvento = camposEvento.get(0);
		String numeroEvento = camposEvento.get(1);
		String editorEvento = camposEvento.get(2);
		String firmaEvento = camposEvento.get(3);
		String guionistaEvento = camposEvento.get(4);
		String varianteEvento = camposEvento.get(5);
		String artistaEvento = camposEvento.get(6);

		String direccionImagenEvento = "";
		String codigoEvento = "";
		String urlReferenciaEvento = "";
		String idEventoTratar = "";
		String fechaGradeo = "";
		String keyComentarios = "";
		String precioEvento = "";

		// Si es una acción, algunos campos se asignan en un orden diferente
		if (esAccion) {
			fechaGradeo = camposEvento.get(2); // 2
			artistaEvento = camposEvento.get(3); // 3
			varianteEvento = camposEvento.get(4); // 4
			guionistaEvento = camposEvento.get(5); // 5
			urlReferenciaEvento = camposEvento.get(6); // 6
			direccionImagenEvento = camposEvento.get(7); // 7
			keyComentarios = camposEvento.get(8); // 8
			editorEvento = camposEvento.get(9); // 9
			codigoEvento = camposEvento.get(10); // 9
			precioEvento = camposEvento.get(11); // 11
			firmaEvento = camposEvento.get(12); // 12
			idEventoTratar = camposEvento.get(13);
		}

		// Establecer los valores en el objeto EventoGradeo
		eventoTemp.setTituloEvento(Utilidades.defaultIfNullOrEmpty(tituloEvento, ""));
		eventoTemp.setNumeroEvento(Utilidades.defaultIfNullOrEmpty(numeroEvento, ""));
		eventoTemp.setCodigoEvento(Utilidades.defaultIfNullOrEmpty(codigoEvento, ""));
		eventoTemp.setPrecioEvento(Utilidades.defaultIfNullOrEmpty(precioEvento, ""));
		eventoTemp.setFechaGradeo(Utilidades.defaultIfNullOrEmpty(fechaGradeo, ""));
		eventoTemp.setEditorEvento(Utilidades.defaultIfNullOrEmpty(editorEvento, ""));
		eventoTemp.setKeyComentarios(Utilidades.defaultIfNullOrEmpty(keyComentarios, ""));
		eventoTemp.setFirmaEvento(Utilidades.defaultIfNullOrEmpty(firmaEvento, ""));
		eventoTemp.setArtistaEvento(Utilidades.defaultIfNullOrEmpty(artistaEvento, ""));
		eventoTemp.setGuionistaEvento(Utilidades.defaultIfNullOrEmpty(guionistaEvento, ""));
		eventoTemp.setVarianteEvento(Utilidades.defaultIfNullOrEmpty(varianteEvento, ""));
		eventoTemp.setDireccionImagenEvento(Utilidades.defaultIfNullOrEmpty(direccionImagenEvento, ""));
		eventoTemp.setUrlReferenciaEvento(Utilidades.defaultIfNullOrEmpty(urlReferenciaEvento, ""));
		eventoTemp.setIdEvento(Utilidades.defaultIfNullOrEmpty(idEventoTratar, ""));

		return eventoTemp;
	}

	public static List<String> comprobarYDevolverLista(List<ComboBox<String>> comboBoxes,
			ObservableList<Control> observableList) {
		List<String> valores = new ArrayList<>();
		for (ComboBox<String> comboBox : comboBoxes) {
			valores.add(comboBox.getValue() != null ? comboBox.getValue() : "");
		}
		if (contieneNulo(comboBoxes)) {
			return Arrays.asList(observableList.stream()
					.map(control -> control instanceof TextInputControl ? ((TextInputControl) control).getText() : "")
					.toArray(String[]::new));
		} else {
			return valores;
		}
	}

	private static <T> boolean contieneNulo(List<T> lista) {

		if (lista == null) {
			return false;
		}

		for (T elemento : lista) {
			if (elemento == null) {
				return true;
			}
		}
		return false;
	}

	public static Evento eventoModificado() {
		// Obtener el ID del cómic a modificar
		String idEventoTemp = referenciaVentana.getIdEventoTratarTextField().getText();

		// Obtener los datos del cómic existente
		Evento eventoTemp = EventoManagerDAO.eventoDatos(idEventoTemp);

		List<Control> allControls = getReferenciaVentana().getControlAccion();
		List<String> valorControles = new ArrayList<>();
		for (Control control : allControls) {
			if (control instanceof TextField) {
				TextField textField = (TextField) control;
				String value = textField.getText();
				valorControles.add(value);
			} else if (control instanceof DatePicker) {
				DatePicker datePicker = (DatePicker) control;
				String value = datePicker.getValue() != null ? datePicker.getValue().toString() : "";
				valorControles.add(value);
			} else if (control instanceof TextArea) {
				TextArea textArea = (TextArea) control;
				String value = textArea.getText();
				valorControles.add(value);
			}
		}

		// Crear un nuevo objeto EventoGradeo con los datos actualizados
		Evento datos = camposEvento(valorControles, true);

		// Crear un objeto EventoGradeo modificado
		Evento eventoTempModificado = new Evento();

		// Copiar el ID del cómic
		eventoTempModificado.setIdEvento(eventoTemp.getIdEvento());

		// Establecer los valores del cómic con los datos actualizados o los valores
		// existentes como respaldo
		eventoTempModificado
				.setTituloEvento(Utilidades.defaultIfNullOrEmpty(datos.getTituloEvento(), eventoTemp.getTituloEvento()));
		eventoTempModificado
				.setCodigoEvento(Utilidades.defaultIfNullOrEmpty(datos.getCodigoEvento(), eventoTemp.getCodigoEvento()));
		eventoTempModificado
				.setNumeroEvento(Utilidades.defaultIfNullOrEmpty(datos.getNumeroEvento(), eventoTemp.getNumeroEvento()));
		eventoTempModificado
				.setPrecioEvento(Utilidades.defaultIfNullOrEmpty(datos.getPrecioEvento(), eventoTemp.getPrecioEvento()));
		eventoTempModificado
				.setFechaGradeo(Utilidades.defaultIfNullOrEmpty(datos.getFechaGradeo(), eventoTemp.getFechaGradeo()));
		eventoTempModificado
				.setEditorEvento(Utilidades.defaultIfNullOrEmpty(datos.getEditorEvento(), eventoTemp.getEditorEvento()));

		eventoTempModificado
				.setFirmaEvento(Utilidades.defaultIfNullOrEmpty(datos.getFirmaEvento(), eventoTemp.getFirmaEvento()));

		eventoTempModificado.setKeyComentarios(
				Utilidades.defaultIfNullOrEmpty(datos.getKeyComentarios(), eventoTemp.getKeyComentarios()));
		eventoTempModificado
				.setArtistaEvento(Utilidades.defaultIfNullOrEmpty(datos.getArtistaEvento(), eventoTemp.getArtistaEvento()));
		eventoTempModificado.setGuionistaEvento(
				Utilidades.defaultIfNullOrEmpty(datos.getGuionistaEvento(), eventoTemp.getGuionistaEvento()));
		eventoTempModificado.setVarianteEvento(
				Utilidades.defaultIfNullOrEmpty(datos.getVarianteEvento(), eventoTemp.getVarianteEvento()));
		eventoTempModificado.setDireccionImagenEvento(
				Utilidades.defaultIfNullOrEmpty(datos.getDireccionImagenEvento(), eventoTemp.getDireccionImagenEvento()));
		eventoTempModificado.setUrlReferenciaEvento(
				Utilidades.defaultIfNullOrEmpty(datos.getUrlReferenciaEvento(), eventoTemp.getUrlReferenciaEvento()));

		return eventoTempModificado;
	}

	public static AccionReferencias getReferenciaVentana() {
		return referenciaVentana;
	}

	public static void setReferenciaVentana(AccionReferencias referenciaVentana) {
		AccionControlUI.referenciaVentana = referenciaVentana;
	}

}