package funcionesManagment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import EventoManagement.Evento;
import alarmas.AlarmaList;
import dbmanager.ConectManager;
import dbmanager.ListasEventosDAO;
import funcionesAuxiliares.Utilidades;
import funcionesAuxiliares.Ventanas;
import funcionesInterfaz.AccionControlUI;
import funcionesInterfaz.FuncionesComboBox;
import funcionesInterfaz.FuncionesTableView;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class AccionAniadir {

	private static AccionFuncionesComunes accionFuncionesComunes = new AccionFuncionesComunes();

	private static AccionReferencias referenciaVentana = getReferenciaVentana();

	/**
	 * Instancia de la clase Ventanas para la navegación.
	 */
	private static Ventanas nav = new Ventanas();

	/**
	 * Instancia de la clase FuncionesComboBox para el manejo de ComboBox.
	 */
	private static FuncionesComboBox funcionesCombo = new FuncionesComboBox();

	/**
	 * Permite introducir un evento en la base de datos de forma manual
	 * 
	 * @throws Exception
	 */
	public void subidaEvento() throws Exception {

		List<String> controls = new ArrayList<>();

		for (Control control : AccionReferencias.getListaTextFields()) {
			if (control instanceof TextField) {
				controls.add(((TextField) control).getText()); // Add the Control object itself
			} else if (control instanceof ComboBox<?>) {
				Object selectedItem = ((ComboBox<?>) control).getSelectionModel().getSelectedItem();
				if (selectedItem != null) {
					controls.add(selectedItem.toString());
				} else {
					controls.add(""); // Add the Control object itself
				}
			}
		}

		Evento evento = AccionControlUI.camposEvento(controls, true);
//		accionRellenoDatos.actualizarCamposUnicos(evento);

		referenciaVentana.getProntInfoTextArea().setOpacity(1);

		accionFuncionesComunes.procesarEvento(evento, false);
	}

	public static void guardarContenidoLista(boolean esLista, Evento evento) {

		if (!ListasEventosDAO.eventosImportados.isEmpty() && nav.alertaInsertar()) {
			Collections.sort(ListasEventosDAO.eventosImportados, Comparator.comparing(Evento::getTituloEvento));
			String mensajePront = "";
			if (esLista) {
				for (Evento c : ListasEventosDAO.eventosImportados) {
					if (AccionControlUI.comprobarListaValidacion(c)) {
						EventoManagerDAO.insertarDatos(c, true);
					}
				}
				ListasEventosDAO.eventosImportados.clear();
				mensajePront = "Has introducido las eventos correctamente\n\n";
			} else {
				EventoManagerDAO.insertarDatos(evento, true);

				ListasEventosDAO.eventosImportados.removeIf(c -> c.getIdEvento().equals(evento.getIdEvento()));

				mensajePront = "Has introducido la evento correctamente\n\n";
			}

			ListasEventosDAO.listasAutoCompletado();
			getReferenciaVentana();
			List<ComboBox<String>> comboboxes = AccionReferencias.getListaComboboxes();
			funcionesCombo.rellenarComboBox(comboboxes);

			referenciaVentana.getTablaBBDD().getItems().clear();
			AccionControlUI.validarCamposClave(true);
			FuncionesTableView.tablaBBDD(ListasEventosDAO.eventosImportados);
			AccionControlUI.limpiarAutorellenos(false);

			AlarmaList.mostrarMensajePront(mensajePront, true, referenciaVentana.getProntInfoTextArea());
		}

	}

	public void mostrarElementosAniadir(List<Node> elementosAMostrarYHabilitar) {

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

		elementosAMostrarYHabilitar.addAll(Arrays.asList(referenciaVentana.getTituloEventoTextField(),
				referenciaVentana.getNombreEditorTextField(), referenciaVentana.getBusquedaGeneralTextField(),
				referenciaVentana.getNumeroEventoTextField(), referenciaVentana.getCodigoEventoTratarTextField(),
				referenciaVentana.getDireccionImagenTextField(), referenciaVentana.getIdEventoTratarTextField(),
				referenciaVentana.getUrlReferenciaTextField(), referenciaVentana.getCodigoEventoTextField(),
				referenciaVentana.getArtistaEventoTextField(), referenciaVentana.getGuionistaEventoTextField(),
				referenciaVentana.getVarianteTextField(), referenciaVentana.getKeyEventoData(),
				referenciaVentana.getNombreEditorTextField(), referenciaVentana.getDataPickFechaP()));

		elementosAMostrarYHabilitar.addAll(Arrays.asList(referenciaVentana.getBotonSubidaPortada(),
				referenciaVentana.getBotonBusquedaAvanzada(), referenciaVentana.getBotonGuardarCambioEvento(),
				referenciaVentana.getBotonEliminarImportadoListaEvento(),
				referenciaVentana.getBotonGuardarListaEventos()));
	}

	public static AccionReferencias getReferenciaVentana() {
		return referenciaVentana;
	}

	public static void setReferenciaVentana(AccionReferencias referenciaVentana) {
		AccionAniadir.referenciaVentana = referenciaVentana;
	}

}
