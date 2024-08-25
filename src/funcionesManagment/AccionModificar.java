package funcionesManagment;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import EventoManagement.Evento;
import alarmas.AlarmaList;
import dbmanager.EventoManagerDAO;
import dbmanager.DBUtilidades;
import dbmanager.ListasEventosDAO;
import dbmanager.SelectManager;
import funcionesAuxiliares.Utilidades;
import funcionesAuxiliares.Ventanas;
import funcionesInterfaz.AccionControlUI;
import funcionesInterfaz.FuncionesComboBox;
import funcionesInterfaz.FuncionesManejoFront;
import funcionesInterfaz.FuncionesTableView;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccionModificar {

	private static AccionFuncionesComunes accionFuncionesComunes = new AccionFuncionesComunes();

	private static AccionReferencias referenciaVentana = getReferenciaVentana();

	private static AccionReferencias referenciaVentanaPrincipal = getReferenciaVentanaPrincipal();

	private static AccionControlUI accionRellenoDatos = new AccionControlUI();

	/**
	 * Instancia de la clase FuncionesComboBox para el manejo de ComboBox.
	 */
	private static FuncionesComboBox funcionesCombo = new FuncionesComboBox();

	/**
	 * Instancia de la clase Ventanas para la navegación.
	 */
	private static Ventanas nav = new Ventanas();

	public static void venderEvento() throws SQLException {

		String idEvento = getReferenciaVentana().getIdEventoTratarTextField().getText();
		getReferenciaVentana().getIdEventoTratarTextField().setStyle("");
		Evento eventoActualizar = EventoManagerDAO.eventoDatos(idEvento);
		if (accionFuncionesComunes.comprobarExistenciaEvento(idEvento)) {
			if (nav.alertaAccionGeneral()) {
				EventoManagerDAO.actualizarEventoBBDD(eventoActualizar, "vender");
				ListasEventosDAO.reiniciarListaEventos();
				String mensaje = ". Has puesto a la venta el evento";
				AlarmaList.mostrarMensajePront(mensaje, false, getReferenciaVentana().getProntInfoTextArea());

				getReferenciaVentana();
				List<ComboBox<String>> comboboxes = AccionReferencias.getListaComboboxes();

				funcionesCombo.rellenarComboBox(comboboxes);
				getReferenciaVentana().getTablaBBDD().refresh();
				FuncionesTableView.nombreColumnas();
				FuncionesTableView.tablaBBDD(ListasEventosDAO.eventosImportados);

			} else {
				String mensaje = "Accion cancelada";
				AlarmaList.mostrarMensajePront(mensaje, false, getReferenciaVentana().getProntInfoTextArea());
			}

		}
	}

	public static void modificarEvento() throws IOException {

		String idEvento = getReferenciaVentana().getIdEventoTratarTextField().getText();
		getReferenciaVentana().getIdEventoTratarTextField().setStyle("");

		if (accionFuncionesComunes.comprobarExistenciaEvento(idEvento)) {
			String sentenciaSQL = DBUtilidades.construirSentenciaSQL(DBUtilidades.TipoBusqueda.COMPLETA);
			List<Evento> listaEventos;
			if (nav.alertaAccionGeneral()) {

				Evento eventoModificado = AccionControlUI.eventoModificado();
				accionFuncionesComunes.procesarEvento(eventoModificado, true);

				ListasEventosDAO.listasAutoCompletado();

				listaEventos = EventoManagerDAO.verLibreria(sentenciaSQL);
				getReferenciaVentana().getTablaBBDD().refresh();
				FuncionesTableView.nombreColumnas();
				FuncionesTableView.tablaBBDD(listaEventos);
			} else {
				listaEventos = EventoManagerDAO.verLibreria(sentenciaSQL);
				ListasEventosDAO.reiniciarListaEventos();
				ListasEventosDAO.listasAutoCompletado();
				FuncionesTableView.nombreColumnas(); // Llamada a funcion
				FuncionesTableView.actualizarBusquedaRaw();
				FuncionesTableView.tablaBBDD(listaEventos);
			}
		}
	}

	public static void actualizarEventoLista() {

		if (!accionRellenoDatos.camposEventoSonValidos()) {
			String mensaje = "Error. Debes de introducir los datos correctos";
			AlarmaList.mostrarMensajePront(mensaje, false, getReferenciaVentana().getProntInfoTextArea());
			return; // Agregar return para salir del método en este punto
		}

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

		Evento datos = AccionControlUI.camposEvento(valorControles, true);
		if (!ListasEventosDAO.eventosImportados.isEmpty()) {

			if (datos.getIdEvento() == null || datos.getIdEvento().isEmpty()) {
				datos = ListasEventosDAO.buscarEventoPorID(ListasEventosDAO.eventosImportados, datos.getIdEvento());
			}

			// Si hay elementos en la lista
			for (Evento c : ListasEventosDAO.eventosImportados) {
				if (c.getIdEvento().equals(datos.getIdEvento())) {
					// Si se encuentra un cómic con el mismo ID, reemplazarlo con los nuevos datos
					ListasEventosDAO.eventosImportados.set(ListasEventosDAO.eventosImportados.indexOf(c), datos);
					break; // Salir del bucle una vez que se actualice el cómic
				}
			}
		} else {
			String id = "A" + 0 + "" + (ListasEventosDAO.eventosImportados.size() + 1);
			datos.setIdEvento(id);
			ListasEventosDAO.eventosImportados.add(datos);
			getReferenciaVentana().getBotonGuardarListaEventos().setVisible(true);
			getReferenciaVentana().getBotonEliminarImportadoListaEvento().setVisible(true);

			getReferenciaVentana().getBotonGuardarListaEventos().setDisable(false);
			getReferenciaVentana().getBotonEliminarImportadoListaEvento().setDisable(false);
		}

		AccionFuncionesComunes.cambiarEstadoBotones(false);
		getReferenciaVentana().getBotonCancelarSubida().setVisible(false); // Oculta el botón de cancelar

		Evento.limpiarCamposEvento(datos);
		AccionControlUI.limpiarAutorellenos(false);

		FuncionesTableView.nombreColumnas();
		FuncionesTableView.tablaBBDD(ListasEventosDAO.eventosImportados);
	}

	public void mostrarElementosModificar(List<Node> elementosAMostrarYHabilitar) {

		elementosAMostrarYHabilitar.addAll(Arrays.asList(referenciaVentana.getLabelAnio(), // Etiqueta para el año
				referenciaVentana.getLabelCodigo(), // Etiqueta para el código
				referenciaVentana.getLabelArtista(), // Etiqueta para el artista
				referenciaVentana.getLabelGuionista(), // Etiqueta para el guionista
				referenciaVentana.getLabelVariante(), // Etiqueta para la variante
				referenciaVentana.getLabelfechaG(), // Etiqueta para la fecha de gradeo
				referenciaVentana.getLabelEditor(), // Etiqueta para el editor
				referenciaVentana.getLabelKeyEvento(), // Etiqueta para los comentarios clave
				referenciaVentana.getLabelNombre(), // Etiqueta para el nombre
				referenciaVentana.getLabelIdMod(), // Etiqueta para el ID de modificación
				referenciaVentana.getLabelPortada(), // Etiqueta para la portada
				referenciaVentana.getLabelGradeo(), // Etiqueta para el gradeo
				referenciaVentana.getLabelReferencia() // Etiqueta para la referencia
		));

		elementosAMostrarYHabilitar.addAll(Arrays.asList(referenciaVentana.getNumeroEventoCombobox(),
				getReferenciaVentana().getRootVBox(), getReferenciaVentana().getBotonSubidaPortada(),
				getReferenciaVentana().getBotonbbdd(), getReferenciaVentana().getTablaBBDD(),
				getReferenciaVentana().getBotonParametroEvento(), getReferenciaVentana().getBotonEliminar()));

		elementosAMostrarYHabilitar.addAll(Arrays.asList(referenciaVentana.getTituloEventoTextField(),
				referenciaVentana.getNombreEditorTextField(), referenciaVentana.getBusquedaGeneralTextField(),
				referenciaVentana.getNumeroEventoTextField(), referenciaVentana.getCodigoEventoTratarTextField(),
				referenciaVentana.getDireccionImagenTextField(), referenciaVentana.getIdEventoTratarTextField(),
				referenciaVentana.getUrlReferenciaTextField(), referenciaVentana.getCodigoEventoTextField(),
				referenciaVentana.getArtistaEventoTextField(), referenciaVentana.getGuionistaEventoTextField(),
				referenciaVentana.getVarianteTextField(), referenciaVentana.getKeyEventoData(),
				referenciaVentana.getNombreEditorTextField(), referenciaVentana.getDataPickFechaP()));

		elementosAMostrarYHabilitar.addAll(Arrays.asList(referenciaVentana.getBotonSubidaPortada(),
				getReferenciaVentana().getBotonModificarEvento()));

		getReferenciaVentana().getRootVBox().toFront();
	}

	public static void actualizarDatabase(String tipoUpdate, Stage ventanaOpciones) {

		if (!Utilidades.isInternetAvailable()) {
			return;
		}

		List<String> inputPortadas = DBUtilidades.obtenerValoresColumna("direccionImagenEvento");

		boolean estaBaseLlena = ListasEventosDAO.comprobarLista();

		if (!estaBaseLlena) {
			String cadenaCancelado = "La base de datos esta vacia";
			AlarmaList.iniciarAnimacionAvanzado(getReferenciaVentana().getProntInfoEspecial(), cadenaCancelado);
			return;
		}

		String sentenciaSQL = DBUtilidades.construirSentenciaSQL(DBUtilidades.TipoBusqueda.COMPLETA);
		List<Evento> listaEventosDatabase = SelectManager.verLibreria(sentenciaSQL, true);

		Collections.sort(listaEventosDatabase, (evento1, evento2) -> {
			int id1 = Integer.parseInt(evento1.getIdEvento());
			int id2 = Integer.parseInt(evento2.getIdEvento());
			return Integer.compare(id1, id2);
		});

		List<Stage> stageVentanas = FuncionesManejoFront.getStageVentanas();

		for (Stage stage : stageVentanas) {
			if (stage != ventanaOpciones && !stage.getTitle().equalsIgnoreCase("Menu principal")) {
				stage.close(); // Close the stage if it's not the current state
			}
		}

		if (getReferenciaVentana().getTablaBBDD() != null) {
			getReferenciaVentana().getTablaBBDD().refresh();
			FuncionesTableView.nombreColumnas();
			FuncionesTableView.tablaBBDD(ListasEventosDAO.eventosImportados);
		}

	}

	public static void eliminarEvento() {

		String idEvento = getReferenciaVentana().getIdEventoTratarTextField().getText();
		getReferenciaVentana().getIdEventoTratarTextField().setStyle("");
		if (accionFuncionesComunes.comprobarExistenciaEvento(idEvento)) {
			if (nav.alertaAccionGeneral()) {

				EventoManagerDAO.borrarEvento(idEvento);
				ListasEventosDAO.reiniciarListaEventos();
				ListasEventosDAO.listasAutoCompletado();

				String sentenciaSQL = DBUtilidades.construirSentenciaSQL(DBUtilidades.TipoBusqueda.COMPLETA);
				List<Evento> listaEventos = EventoManagerDAO.verLibreria(sentenciaSQL);
				getReferenciaVentana().getTablaBBDD().refresh();
				FuncionesTableView.nombreColumnas();
				FuncionesTableView.tablaBBDD(listaEventos);

				List<ComboBox<String>> comboboxes = getReferenciaVentana().getListaComboboxes();

				funcionesCombo.rellenarComboBox(comboboxes);
				getReferenciaVentana().getProntInfoTextArea().clear();
				getReferenciaVentana().getProntInfoTextArea().setOpacity(0);
				AccionControlUI.limpiarAutorellenos(false);
			} else {
				String mensaje = "Accion cancelada";
				AlarmaList.mostrarMensajePront(mensaje, false, getReferenciaVentana().getProntInfoTextArea());
			}
		}
	}

	public static void eliminarEventoLista() {
		String idEvento = getReferenciaVentana().getIdEventoTratarTextField().getText();

		if (nav.alertaEliminar() && idEvento != null) {
			// Obtener la evento a eliminar
			Evento eventoEliminar = ListasEventosDAO.eventosImportados.stream().filter(c -> c.getIdEvento().equals(idEvento))
					.findFirst().orElse(null);

			if (eventoEliminar != null) {
				// Obtener la dirección de la imagen y verificar si existe
				String direccionImagen = eventoEliminar.getDireccionImagenEvento();
				if (direccionImagen != null && !direccionImagen.isEmpty()) {
					File archivoImagen = new File(direccionImagen);
					if (archivoImagen.exists()) {
						// Borrar el archivo de la imagen
						if (archivoImagen.delete()) {
							System.out.println("Archivo de imagen eliminado: " + direccionImagen);
						} else {
							System.err.println("No se pudo eliminar el archivo de imagen: " + direccionImagen);
							// Puedes lanzar una excepción aquí si lo prefieres
						}
					}
				}

				// Eliminar la evento de la lista
				ListasEventosDAO.eventosImportados.remove(eventoEliminar);
				AccionControlUI.limpiarAutorellenos(false);
				FuncionesTableView.nombreColumnas();
				FuncionesTableView.tablaBBDD(ListasEventosDAO.eventosImportados);
				getReferenciaVentana().getTablaBBDD().refresh();

				// Verificar si la lista está vacía y cambiar el estado de los botones
				if (ListasEventosDAO.eventosImportados.isEmpty()) {
					AccionFuncionesComunes.cambiarEstadoBotones(false);
				}
			}
		}
	}

	public static AccionReferencias getReferenciaVentana() {
		return referenciaVentana;
	}

	public static AccionReferencias getReferenciaVentanaPrincipal() {
		return referenciaVentanaPrincipal;
	}

	public static void setReferenciaVentana(AccionReferencias referenciaVentana) {
		AccionModificar.referenciaVentana = referenciaVentana;
	}

	public static void setReferenciaVentanaPrincipal(AccionReferencias referenciaVentana) {
		AccionModificar.referenciaVentanaPrincipal = referenciaVentana;
	}

}
